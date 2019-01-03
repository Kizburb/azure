package azure.me.kizburb.azure.module.combat;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.Azure;
import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventMotionUpdate;
import azure.me.kizburb.azure.event.events.EventPostMotionUpdate;
import azure.me.kizburb.azure.event.events.EventPreMotionUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class KillAura extends Module {

	private EntityLivingBase target;
	private long current, last;
	private int delay = 8;
	private float yaw, pitch;
	private boolean others;

	public KillAura() {
		super("KillAura", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@Override
	public void setup() {
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Crit Size", this, 5, 0, 15, true));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Existed", this, 30, 0, 500, true));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Reach", this, 3, 0, 10, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Auto Block", this, true));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Invisibles", this, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Players", this, true));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Animals", this, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Monsters", this, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Villagers", this, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Teams", this, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("KeepSprint", this, true));
	}

	@EventTarget
	public void onPre(EventMotionUpdate event) {
		if (!this.isToggled())
			return;

		if (event.getCurrentState() == EventMotionUpdate.State.PRE) {
			target = getClosest(mc.playerController.getBlockReachDistance());

			if (target == null)
				return;

			updateTime();

			yaw = mc.thePlayer.rotationYaw;
			pitch = mc.thePlayer.rotationPitch;

			boolean block = target != null
					&& Azure.INSTANCE.settingsManager.getSettingByName("AutoBlock").getValBoolean()
					&& mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null
					&& mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;

			if ((block && mc.thePlayer.getDistanceToEntity(target) <= mc.playerController.getBlockReachDistance())
					&& isValidEntity())
				mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());

			if ((current - last > 1000 / delay && isValidEntity() && canAttack(target))) {
				float[] facing = getNeededRotations(target);
				event.setYaw(facing[0]);
				event.setPitch(facing[1]);
				attack(target);
				resetTime();
			}
		}
	}

	@EventTarget
	public void onPost(EventMotionUpdate event) {
		if (!this.isToggled())
			return;

		if (event.getCurrentState() == EventMotionUpdate.State.POST) {
			if (target == null)
				return;
			mc.thePlayer.rotationYaw = yaw;
			mc.thePlayer.rotationPitch = pitch;
		}
	}

	private void attack(Entity entity) {
		for (int i = 0; i < Azure.INSTANCE.settingsManager.getSettingByName("Crit Size").getValDouble(); i++)
			mc.thePlayer.onCriticalHit(entity);

		mc.thePlayer.swingItem();
		mc.playerController.attackEntity(mc.thePlayer, entity);
	}

	private void updateTime() {
		current = (System.nanoTime() / 1000000L);
	}

	private void resetTime() {
		last = (System.nanoTime() / 1000000L);
	}

	public boolean isValidEntity() {
		return !(target.isDead) && target != null;
	}

	private EntityLivingBase getClosest(double range) {
		double dist = range;
		EntityLivingBase target = null;

		for (Object object : mc.theWorld.loadedEntityList) {
			Entity entity = (Entity) object;
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase player = (EntityLivingBase) entity;
				if (canAttack(player)) {
					if (!(player instanceof EntityMob) && (!(player instanceof EntityAnimal) || others)) {
						double currentDist = mc.thePlayer.getDistanceToEntity(player);
						if (currentDist <= dist) {
							dist = currentDist;
							target = player;
						}
					}
				}
			}
		}
		return target;
	}

	private boolean canAttack(EntityLivingBase player) {
		if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob
				|| player instanceof EntityVillager) {
			if (player instanceof EntityPlayer
					&& !Azure.INSTANCE.settingsManager.getSettingByName("Players").getValBoolean())
				return false;
			if (player instanceof EntityAnimal
					&& !Azure.INSTANCE.settingsManager.getSettingByName("Animals").getValBoolean())
				return false;
			if (player instanceof EntityMob
					&& !Azure.INSTANCE.settingsManager.getSettingByName("Monsters").getValBoolean())
				return false;
			if (player instanceof EntityVillager
					&& !Azure.INSTANCE.settingsManager.getSettingByName("Villagers").getValBoolean())
				return false;
		}
		if (player.isOnSameTeam(mc.thePlayer)
				&& Azure.INSTANCE.settingsManager.getSettingByName("Teams").getValBoolean())
			return false;
		if (player.isInvisible() && !Azure.INSTANCE.settingsManager.getSettingByName("Invisibles").getValBoolean())
			return false;
		if (!isInFOV(player, Azure.INSTANCE.settingsManager.getSettingByName("FOV").getValDouble()))
			return false;

		return player != mc.thePlayer && player.isEntityAlive()
				&& mc.thePlayer.getDistanceToEntity(player) <= mc.playerController.getBlockReachDistance()
				&& player.ticksExisted > Azure.INSTANCE.settingsManager.getSettingByName("Existed").getValDouble();
	}

	private boolean isInFOV(EntityLivingBase entity, double angle) {
		angle *= .5D;
		double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw,
				getRotations(entity.posX, entity.posY, entity.posZ)[0]);
		return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
	}

	private float getAngleDifference(float dir, float yaw) {
		float f = Math.abs(yaw - dir) % 360F;
		float dist = f > 180F ? 360F - f : f;
		return dist;
	}

	public static float[] getNeededRotations(final EntityLivingBase entity) {
		final Vec3 eyesPos = new Vec3(Minecraft.getMinecraft().thePlayer.posX,
				Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(),
				Minecraft.getMinecraft().thePlayer.posZ);
		final AxisAlignedBB bb = entity.getEntityBoundingBox();
		final Vec3 vec = new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5, bb.minY + (bb.maxY - bb.minY) * 0.5,
				bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
		final double diffX = vec.xCoord - eyesPos.xCoord;
		final double diffY = vec.yCoord - eyesPos.yCoord;
		final double diffZ = vec.zCoord - eyesPos.zCoord;
		final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
		final float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
		final float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
		return new float[] { MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
	}

	private float[] getRotations(double x, double y, double z) {
		double diffX = x + .5D - mc.thePlayer.posX;
		double diffY = (y + .5D) / 2D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		double diffZ = z + .5D - mc.thePlayer.posZ;

		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180D / Math.PI);

		return new float[] { yaw, pitch };
	}
}
