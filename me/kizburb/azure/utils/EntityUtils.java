package azure.me.kizburb.azure.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class EntityUtils {

	public synchronized static void faceEntityClient(EntityLivingBase entity) {
		float[] rotations = getRotationsNeeded(entity);
		if (rotations != null) {
			Minecraft.getMinecraft().thePlayer.rotationYaw = limitAngleChange(
					Minecraft.getMinecraft().thePlayer.prevRotationYaw, rotations[0], 55);// NoCheat+
																							// bypass!!!
			Minecraft.getMinecraft().thePlayer.rotationPitch = rotations[1];
		}
	}

	public static float getPitchChange(final EntityLivingBase entity) {
		final double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		final double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		final double deltaY = entity.posY - 2.2D + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
		final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
		final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
		return -MathHelper
				.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float) pitchToEntity);
	}

	public static float getYawChange(final EntityLivingBase entity) {
		final double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		final double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double yawToEntity;

		if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
			yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
		} else {
			if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
				yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
			} else {
				yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
			}
		}
		return MathHelper.wrapAngleTo180_float(-(Minecraft.getMinecraft().thePlayer.rotationYaw - (float) yawToEntity));
	}

	public synchronized static void faceEntityPacket(EntityLivingBase entity) {
		float[] rotations = getRotationsNeeded(entity);
		if (rotations != null) {
			float yaw = rotations[0];
			float pitch = rotations[1];
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(
					new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, Minecraft.getMinecraft().thePlayer.onGround));
		}
	}

	public static EntityLivingBase getClosestEntity() {
		EntityLivingBase closestEntity = null;
		for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
			EntityLivingBase en = (EntityLivingBase) o;
			if (!(o instanceof EntityPlayerSP) && !en.isDead && en.getHealth() > 0
					&& Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en)) {
				if (closestEntity == null || Minecraft.getMinecraft().thePlayer.getDistanceToEntity(
						en) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEntity)) {
					closestEntity = en;
				}
			}
		}
		return closestEntity;
	}

	public static float[] getRotationsNeeded(Entity entity) {
		if (entity == null)
			return null;
		double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double diffY;
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = entityLivingBase.posY + (double) entityLivingBase.getEyeHeight() * 0.9
					- (Minecraft.getMinecraft().thePlayer.posY
							+ (double) Minecraft.getMinecraft().thePlayer.getEyeHeight());
		} else
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D
					- (Minecraft.getMinecraft().thePlayer.posY
							+ (double) Minecraft.getMinecraft().thePlayer.getEyeHeight());
		double dist = (double) MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0D / Math.PI));
		return new float[] {
				Minecraft.getMinecraft().thePlayer.rotationYaw
						+ MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw),
				Minecraft.getMinecraft().thePlayer.rotationPitch
						+ MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) };

	}

	private final static float limitAngleChange(final float current, final float intended, final float maxChange) {
		float change = intended - current;

		if (change > maxChange) {
			change = maxChange;
		} else if (change < -maxChange) {
			change = -maxChange;
		}

		return current + change;
	}

	public static int getDistanceFromMouse(EntityLivingBase entity) {
		float[] neededRotations = getRotationsNeeded(entity);
		if (neededRotations != null) {
			float neededYaw = Minecraft.getMinecraft().thePlayer.rotationYaw - neededRotations[0],
					neededPitch = Minecraft.getMinecraft().thePlayer.rotationPitch - neededRotations[1];
			float distanceFromMouse = MathHelper.sqrt_float(neededYaw * neededYaw + neededPitch * neededPitch);
			return (int) distanceFromMouse;
		}
		return -1;
	}
}
