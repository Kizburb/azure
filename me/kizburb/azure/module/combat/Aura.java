package azure.me.kizburb.azure.module.combat;

import java.util.Comparator;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.Azure;
import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventMotionUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import azure.me.kizburb.azure.utils.TimerHelper;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Aura extends Module {
	
	private static Random RANDOM = new Random();
	
	public Aura() {
		super("Aura", Keyboard.KEY_NONE, Category.COMBAT);
	}
	
	@Override
	public void setup() {
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Aura Reach", this, 4.1F, 1.0F, 7.0F, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Change Delay", this, 100, 10, 1000, true));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("CPS", this, 8, 1, 15, true));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Random CPS", this, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Aura Range", this, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Team", this, false));
	}

	private EntityLivingBase target;
	
	double cpsInMS = 1000 / Azure.INSTANCE.settingsManager.getSettingByName("CPS").getValDouble() + (RANDOM.nextDouble() * 100);
	double unrandomizedCpsInMS = 1000 / Azure.INSTANCE.settingsManager.getSettingByName("CPS").getValDouble();
	private TimerHelper cpsTimer = new TimerHelper();
	private TimerHelper targetChangeTimer = new TimerHelper();
	
	@EventTarget
	public void onMotion(EventMotionUpdate event) {
		if (!this.isToggled())
			return;
		
		switch(event.getCurrentState()) {
		case PRE:
			
			Object[] objects = mc.theWorld.loadedEntityList.stream().filter(this::isValid).sorted(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer))).toArray();
			
			if (!isValid(target))
				target = null;
			
			if (target instanceof EntityPlayer && mc.thePlayer.isOnSameTeam(target) && Azure.INSTANCE.settingsManager.getSettingByName("Team").getValBoolean())
				return;
			
			if (objects.length > 0 && target == null) {
				target = (EntityLivingBase) objects[0];
				targetChangeTimer.setLastMS();
			}
			
			if (target == null)
				return;
			
			float[] facing = getNeededRotations(target);
			event.setYaw(facing[0]);
			event.setPitch(facing[1]);
			
			break;
		case POST:
			
			if (target == null)
				return;
			
			if (Azure.INSTANCE.settingsManager.getSettingByName("Random CPS").getValBoolean()) {
				if (!cpsTimer.hasTimeReached(cpsInMS))
					return;
			} else if (!Azure.INSTANCE.settingsManager.getSettingByName("Random CPS").getValBoolean()) {
				if (!cpsTimer.hasTimeReached(unrandomizedCpsInMS))
					return;
			}
			
			if (!targetChangeTimer.hasTimeReached((int) Azure.INSTANCE.settingsManager.getSettingByName("Change Delay").getValDouble()))
				return;
			
			mc.thePlayer.swingItem();
			mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
			
			cpsTimer.setLastMS();
			
			if (Azure.INSTANCE.settingsManager.getSettingByName("Aura Range").getValBoolean())
				target = null;
			
			break;
		}
	}
	
	public static int random(int minCPSValue, int maxCPSValue) {
		return RANDOM.nextInt(maxCPSValue - minCPSValue) + minCPSValue;
	}

	private boolean isValid(Entity entity) {
		return entity instanceof EntityLivingBase && entity != mc.thePlayer
				&& ((EntityLivingBase) entity).getHealth() > 0F
				&& entity.getDistanceToEntity(mc.thePlayer) <= (float) Azure.INSTANCE.settingsManager
						.getSettingByName("Aura Reach").getValDouble();
	}

	private boolean teamCheck(EntityLivingBase player) {
		if (player.isOnSameTeam(mc.thePlayer) && Azure.INSTANCE.settingsManager.getSettingByName("Team").getValBoolean())
			return false;
		return true;
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
}
