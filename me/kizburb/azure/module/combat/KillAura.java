package azure.me.kizburb.azure.module.combat;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventPostMotionUpdate;
import azure.me.kizburb.azure.event.events.EventPreMotionUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;

public class KillAura extends Module {

	private EntityLivingBase target;
	private long current, last;
	private int delay = 8;
	private float yaw, pitch;
	private boolean others;
	
	public KillAura() {
		super("KillAura", Keyboard.KEY_NONE, Category.COMBAT);
	}
	
	@EventTarget
	public void onPre(EventPreMotionUpdate event) {
		target = getClosest(mc.playerController.getBlockReachDistance());
		if(target == null)
			return;
		
		updateTime();
		yaw = mc.thePlayer.rotationYaw;
		pitch = mc.thePlayer.rotationPitch;
		
		if (current - last > 1000 / delay) {
			attack(target);
			resetTime();
		}
	}
	
	@EventTarget
	public void onPost(EventPostMotionUpdate event) {
		if (target == null)
			return;
		
		mc.thePlayer.rotationYaw = yaw;
		mc.thePlayer.rotationPitch = pitch;
	}
	
	private void attack(Entity entity) {
		mc.thePlayer.swingItem();
		mc.playerController.attackEntity(mc.thePlayer, entity);
	}
	
	private void updateTime() {
		current = (System.nanoTime() / 1000000L);
	}
	
	private void resetTime() {
		last = (System.nanoTime() / 1000000L);
	}
	
	private EntityLivingBase getClosest(double range) {
		double dist = range;
		EntityLivingBase target = null;
		
		for (Object object : mc.theWorld.loadedEntityList) {
			Entity entity = (Entity)object;
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase player = (EntityLivingBase)entity;
				if(canAttack(player)) {
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
		return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= mc.playerController.getBlockReachDistance() && player.ticksExisted > 30;
	}
}
