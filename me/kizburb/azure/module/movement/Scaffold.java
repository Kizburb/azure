package azure.me.kizburb.azure.module.movement;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.Azure;
import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventPostMotionUpdate;
import azure.me.kizburb.azure.event.events.EventPreMotionUpdate;
import azure.me.kizburb.azure.event.events.EventSafeWalk;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import azure.me.kizburb.azure.utils.CombatUtils;
import azure.me.kizburb.azure.utils.PlayerUtils;
import azure.me.kizburb.azure.utils.TimeUtils;
import de.Hero.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Scaffold extends Module {

	public Scaffold() {
		super("Scaffold", Keyboard.KEY_NONE, Category.MOVEMENT);
	}

	private String mode = Azure.INSTANCE.settingsManager.getSettingByName("Scaffold Mode").getValString();
	private BlockData blockData;
	private TimeUtils time = new TimeUtils();
	private TimeUtils delay = new TimeUtils();
	private double oldDelay;

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Normal");
		options.add("AAC");
		options.add("CubeCraft");
		options.add("Gomme");
		options.add("Unlegit");
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Scaffold Mode", this, "Normal", options));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("NoSwing", this, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Silent", this, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("SC Delay", this, 250.0D, 40.0D, 1000.0D, false));
	}

	@EventTarget
	public void onPre(EventPreMotionUpdate event) {
		this.setDisplayName("Scaffold §7" + mode);
		if (this.mc.thePlayer == null)
			return;
		this.blockData = getBlockData(new BlockPos(this.mc.thePlayer).add(0.0D, -0.75D, 0.0D), 1);
		int block = getBlockItem();
		Item item = this.mc.thePlayer.inventory.getStackInSlot(block).getItem();
		if ((block != -1) && (item != null) && ((item instanceof ItemBlock))) {
			if (Azure.INSTANCE.settingsManager.getSettingByName("Silent").getValBoolean())
				this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(block));
			if ((mode.equalsIgnoreCase("AAC")) || (mode.equalsIgnoreCase("Gomme"))) {
				if (mode.equalsIgnoreCase("Gomme"))
					this.mc.thePlayer.setSprinting(false);
				if ((mode.equalsIgnoreCase("Gomme")) && (PlayerUtils.getSpeed() > 0.08D)) {
					PlayerUtils.setSpeed(0.08D);
				}
				if ((mode.equalsIgnoreCase("AAC")) && (this.mc.gameSettings.keyBindSprint.pressed)
						&& (PlayerUtils.MovementInput()))
					PlayerUtils.setSpeed(0.002D);
				if ((mode.equalsIgnoreCase("Gomme")) && (PlayerUtils.MovementInput())) {
					PlayerUtils.setSpeed(0.08D);
				}
			}
			if (mode.equalsIgnoreCase("AAC"))
				event.pitch = 81.0F;
			if ((mode.equalsIgnoreCase("CubeCraft")) && (PlayerUtils.MovementInput()))
				PlayerUtils.setSpeed(0.04D);
		}

		Random r = new Random();
		if ((this.blockData != null) && (block != -1) && (item != null) && ((item instanceof ItemBlock))) {
			Vec3 pos = getBlockSide(this.blockData.position, this.blockData.face);
			float[] rot = CombatUtils.getRotationsNeededBlock(pos.xCoord, pos.yCoord, pos.zCoord);
			float[] rots = CombatUtils.getDirectionToBlock(pos.xCoord, pos.yCoord, pos.zCoord, this.blockData.face);
			if (mode.equalsIgnoreCase("Unlegit")) {
				event.pitch = rots[1];
			} else
				event.pitch = rot[1];
			if (mode.equalsIgnoreCase("CubeCraft")) {
				if (this.mc.gameSettings.keyBindForward.pressed) {
					event.yaw = (this.mc.thePlayer.rotationYaw >= 180.0F ? this.mc.thePlayer.rotationYaw - 180.0F
							: this.mc.thePlayer.rotationYaw + 180.0F);
				} else if (this.mc.gameSettings.keyBindBack.pressed) {
					event.yaw = this.mc.thePlayer.rotationYaw;
				} else if (this.mc.gameSettings.keyBindLeft.pressed) {
					event.yaw = (this.mc.thePlayer.rotationYaw + 90.0F);
				} else if (this.mc.gameSettings.keyBindRight.pressed) {
					event.yaw = (this.mc.thePlayer.rotationYaw - 90.0F);
				}
				this.mc.thePlayer.rotationYawHead = event.yaw;
			} else if (mode.equalsIgnoreCase("Unlegit")) {
				event.yaw = rots[0];
			} else {
				event.yaw = rot[0];
			}
			this.mc.thePlayer.rotationYawHead = event.yaw;
		}
	}

	private double getDoubleRandom(double min, double max) {
		return ThreadLocalRandom.current().nextDouble(min, max);
	}

	@EventTarget
	public void onPost(EventPostMotionUpdate event) {
	}

	@EventTarget
	public void onSafe(EventSafeWalk event) {
		event.setSafe(true);

		if (this.mc.thePlayer == null)
			return;

		if (this.blockData != null) {
			int block = getBlockItem();
			Random rand = new Random();
			Item item = this.mc.thePlayer.inventory.getStackInSlot(block).getItem();
			if ((block != -1) && (item != null) && ((item instanceof ItemBlock))) {
				Vec3 hitVec = new Vec3(this.blockData.position).addVector(0.5D, 0.5D, 0.5D)
						.add(new Vec3(this.blockData.face.getDirectionVec()).scale(0.5D));

				if ((mode.equalsIgnoreCase("AAC")) || (mode.equalsIgnoreCase("Gomme"))) {
					if (!this.delay.isDelayComplete(mode.equalsIgnoreCase("Gomme") ? 0 + rand.nextInt(50)
							: ((int) Azure.INSTANCE.settingsManager.getSettingByName("SC Delay").getValDouble()
									+ rand.nextInt(50)))) {
					}
				} else if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld,
						this.mc.thePlayer.inventory.getStackInSlot(block), this.blockData.position, this.blockData.face,
						hitVec)) {
					this.delay.reset();
					this.blockData = null;
					this.time.reset();
					if ((Boolean) Azure.INSTANCE.settingsManager.getSettingByName("NoSwing").getValBoolean()) {
						this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
						return;
					}
					this.mc.thePlayer.swingItem();

					return;
				}
				if (mode.equalsIgnoreCase("CubeCraft")) {
					if (this.delay.isDelayComplete(
							((int) Azure.INSTANCE.settingsManager.getSettingByName("SC Delay").getValDouble()
									+ rand.nextInt(50)))) {
						if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld,
								this.mc.thePlayer.inventory.getStackInSlot(block), this.blockData.position,
								this.blockData.face, hitVec)) {
							this.delay.reset();
							this.blockData = null;
							this.time.reset();
							if ((Azure.INSTANCE.settingsManager.getSettingByName("NoSwing").getValBoolean())) {
								this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
								return;
							}
							this.mc.thePlayer.swingItem();
							return;
						}
					}
				}
				if ((this.delay.isDelayComplete(
						(long) Azure.INSTANCE.settingsManager.getSettingByName("SC Delay").getValDouble()))
						&& (mode.equalsIgnoreCase("Normal"))) {
					if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld,
							this.mc.thePlayer.inventory.getStackInSlot(block), this.blockData.position,
							this.blockData.face, hitVec)) {
						this.delay.reset();
						this.blockData = null;
						if (Azure.INSTANCE.settingsManager.getSettingByName("NoSwing").getValBoolean()) {
							this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
						} else
							this.mc.thePlayer.swingItem();
					}
					this.delay.reset();
				}
			}
		}
	}

	private boolean canPlace(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos,
			EnumFacing side, Vec3 vec3) {
		if ((heldStack.getItem() instanceof ItemBlock)) {
			return ((ItemBlock) heldStack.getItem()).canPlaceBlockOnSide(worldIn, hitPos, side, player, heldStack);
		}
		return false;
	}

	private void sendCurrentItem() {
		this.mc.thePlayer.sendQueue
				.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
	}

	private int getBlockItem() {
		int block = -1;
		for (int i = 8; i >= 0; i--) {
			if ((this.mc.thePlayer.inventory.getStackInSlot(i) != null)
					&& ((this.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock))
					&& ((this.mc.thePlayer.getHeldItem() == this.mc.thePlayer.inventory.getStackInSlot(i))
							|| (((Boolean) Azure.INSTANCE.settingsManager.getSettingByName("Silent")
									.getValBoolean())))) {
				block = i;
			}
		}
		return block;
	}

	public BlockData getBlockData(BlockPos pos, int i) {
		return this.mc.theWorld.getBlockState(pos.add(0, -i, 0)).getBlock() != Blocks.air
				? new BlockData(pos.add(0, -i, 0), EnumFacing.UP)
				: this.mc.theWorld.getBlockState(pos.add(-i, 0, 0)).getBlock() != Blocks.air
						? new BlockData(pos.add(-i, 0, 0), EnumFacing.EAST)
						: this.mc.theWorld.getBlockState(pos.add(i, 0, 0)).getBlock() != Blocks.air
								? new BlockData(pos.add(i, 0, 0), EnumFacing.WEST)
								: this.mc.theWorld.getBlockState(pos.add(0, 0, -i)).getBlock() != Blocks.air
										? new BlockData(pos.add(0, 0, -i), EnumFacing.SOUTH)
										: this.mc.theWorld.getBlockState(pos.add(0, 0, i)).getBlock() != Blocks.air
												? new BlockData(pos.add(0, 0, i), EnumFacing.NORTH)
												: null;
	}

	public Vec3 getBlockSide(BlockPos pos, EnumFacing face) {
		if (face == EnumFacing.NORTH) {
			return new Vec3(pos.getX(), pos.getY(), pos.getZ() - 0.5D);
		}
		if (face == EnumFacing.EAST) {
			return new Vec3(pos.getX() + 0.5D, pos.getY(), pos.getZ());
		}
		if (face == EnumFacing.SOUTH) {
			return new Vec3(pos.getX(), pos.getY(), pos.getZ() + 0.5D);
		}
		if (face == EnumFacing.WEST) {
			return new Vec3(pos.getX() - 0.5D, pos.getY(), pos.getZ());
		}
		return new Vec3(pos.getX(), pos.getY(), pos.getZ());
	}

	public class BlockData {
		public BlockPos position;
		public EnumFacing face;

		public BlockData(BlockPos position, EnumFacing face) {
			this.position = position;
			this.face = face;
		}
	}

	public void onEnable() {
		super.onEnable();
	}

	public void onDisable() {
		super.onDisable();
		sendCurrentItem();
		this.mc.gameSettings.keyBindSneak.pressed = false;
		this.mc.timer.timerSpeed = 1.0F;
	}
}
