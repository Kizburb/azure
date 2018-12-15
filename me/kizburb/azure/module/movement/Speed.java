package azure.me.kizburb.azure.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.Azure;
import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventPreMotionUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Speed extends Module {

	private String mode = Azure.INSTANCE.settingsManager.getSettingByName("Speed Mode").getValString();

	public Speed() {
		super("Speed", Keyboard.KEY_NONE, Category.MOVEMENT);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Y-Port");
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Speed Mode", this, "Y-Port", options));
	}

	@EventTarget
	public void onPre(EventPreMotionUpdate event) {

		if (mode.equalsIgnoreCase("Y-Port")) {
			if (isOnLiquid())
				return;

			if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
				if (mc.thePlayer.ticksExisted % 2 != 0)
					event.y += 4;
				mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? .45F : .2F);
				mc.timer.timerSpeed = 1.095F;
			}
		}
	}

	private boolean isOnLiquid() {
		boolean onLiquid = false;
		final int y = (int) (mc.thePlayer.boundingBox.minY - .01);
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++x) {
				Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLiquid))
						return false;
					onLiquid = true;
				}
			}
		}
		return onLiquid;
	}
}
