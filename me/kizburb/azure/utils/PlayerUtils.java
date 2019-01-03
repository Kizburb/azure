package azure.me.kizburb.azure.utils;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class PlayerUtils {

	public static Minecraft mc = Minecraft.getMinecraft();

	public static boolean isInLiquid() {
		for (int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); x < MathHelper
				.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; ++z) {
				BlockPos pos = new BlockPos(x, (int) Minecraft.getMinecraft().thePlayer.boundingBox.minY, z);
				Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
				if (block != null && !(block instanceof BlockAir))
					return block instanceof BlockLiquid;
			}
		}
		return false;
	}

	public static boolean isInsideBlock() {
		for (int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; x++) {
			for (int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); y < MathHelper
					.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxY) + 1; y++) {
				for (int z = MathHelper
						.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper
								.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; z++) {
					Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
					if (block != null && !(block instanceof BlockAir)) {
						AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld,
								new BlockPos(x, y, z),
								Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)));
						if (block instanceof BlockHopper)
							boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
						if (boundingBox != null
								&& Minecraft.getMinecraft().thePlayer.boundingBox.intersectsWith(boundingBox))
							return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean MovementInput() {
		return (mc.gameSettings.keyBindForward.pressed) || (mc.gameSettings.keyBindLeft.pressed)
				|| (mc.gameSettings.keyBindRight.pressed) || (mc.gameSettings.keyBindBack.pressed);
	}

	public static ArrayList<Vector3f> vanillaTeleportPositions(double tpX, double tpY, double tpZ, double speed) {
		ArrayList<Vector3f> positions = new ArrayList();
		Minecraft mc = Minecraft.getMinecraft();
		double posX = tpX - mc.thePlayer.posX;
		double posY = tpY - mc.thePlayer.posY;
		double posZ = tpZ - mc.thePlayer.posZ;
		float yaw = (float) (Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D - 90.0D);
		float pitch = (float) (-Math.atan2(posY, Math.sqrt(posX * posX + posZ * posZ)) * 180.0D / 3.141592653589793D);
		double tmpX = mc.thePlayer.posX;
		double tmpY = mc.thePlayer.posY;
		double tmpZ = mc.thePlayer.posZ;
		double steps = 1.0D;
		for (double d = speed; d < getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, tpX, tpY,
				tpZ); d += speed) {
			steps += 1.0D;
		}
		for (double d = speed; d < getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, tpX, tpY,
				tpZ); d += speed) {
			tmpX = mc.thePlayer.posX - Math.sin(getDirection(yaw)) * d;
			tmpZ = mc.thePlayer.posZ + Math.cos(getDirection(yaw)) * d;
			tmpY -= (mc.thePlayer.posY - tpY) / steps;
			positions.add(new Vector3f((float) tmpX, (float) tmpY, (float) tmpZ));
		}
		positions.add(new Vector3f((float) tpX, (float) tpY, (float) tpZ));

		return positions;
	}

	public static void toFwd(double speed) {
		float yaw = mc.thePlayer.rotationYaw * 0.017453292F;
		mc.thePlayer.motionX -= MathHelper.sin(yaw) * speed;
		mc.thePlayer.motionZ += MathHelper.cos(yaw) * speed;
	}

	public static void setSpeed(double speed) {
		mc.thePlayer.motionX = (-(Math.sin(getDirection()) * speed));
		mc.thePlayer.motionZ = (Math.cos(getDirection()) * speed);
	}

	public static double getSpeed() {
		return Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
	}

	public static Block getBlockUnderPlayer(EntityPlayer inPlayer) {
		return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - 1.0D, inPlayer.posZ));
	}

	public static Block getBlock(BlockPos pos) {
		return mc.theWorld.getBlockState(pos).getBlock();
	}

	public static Block getBlockAtPosC(EntityPlayer inPlayer, double x, double y, double z) {
		return getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
	}

	public static float getDirection() {
		float yaw = mc.thePlayer.rotationYaw;
		if (mc.thePlayer.moveForward < 0.0F) {
			yaw += 180.0F;
		}
		float forward = 1.0F;
		if (mc.thePlayer.moveForward < 0.0F) {
			forward = -0.5F;
		} else if (mc.thePlayer.moveForward > 0.0F) {
			forward = 0.5F;
		}
		if (mc.thePlayer.moveStrafing > 0.0F) {
			yaw -= 90.0F * forward;
		}
		if (mc.thePlayer.moveStrafing < 0.0F) {
			yaw += 90.0F * forward;
		}
		yaw *= 0.017453292F;
		return yaw;
	}

	public static float getDirection(float yaw) {
		if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0F) {
			yaw += 180.0F;
		}
		float forward = 1.0F;
		if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0F) {
			forward = -0.5F;
		} else if (Minecraft.getMinecraft().thePlayer.moveForward > 0.0F) {
			forward = 0.5F;
		}
		if (Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0F) {
			yaw -= 90.0F * forward;
		}
		if (Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0F) {
			yaw += 90.0F * forward;
		}
		yaw *= 0.017453292F;

		return yaw;
	}

	public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
		double d0 = x1 - x2;
		double d1 = y1 - y2;
		double d2 = z1 - z2;
		return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
	}
}
