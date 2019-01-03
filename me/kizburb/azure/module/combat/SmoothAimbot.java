package azure.me.kizburb.azure.module.combat;

import java.util.Objects;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.Azure;
import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventMotionUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import azure.me.kizburb.azure.utils.EntityUtils;
import de.Hero.settings.Setting;
import net.minecraft.entity.player.EntityPlayer;

public class SmoothAimbot extends Module {

	public SmoothAimbot() {
		super("SmoothAim", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@Override
	public void setup() {
		Azure.INSTANCE.settingsManager.rSetting(new Setting("SA FOV", this, 80F, 0F, 360F, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("SA Speed", this, 8F, 0F, 15F, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("SA Range", this, 3.8D, 3.0D, 6.0D, false));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("SA Pitch", this, true));
	}

	@EventTarget
	public void onEventCancelled(EventMotionUpdate event) {
		if (!this.isToggled())
			return;

		final EntityPlayer target = this.getClosestPlayerToCursor(
				(float) Azure.INSTANCE.settingsManager.getSettingByName("SA FOV").getValDouble());
		if (Objects.nonNull(target)) {
			mc.thePlayer.rotationYaw = mc.thePlayer.rotationYaw + (EntityUtils.getYawChange(target)
					/ (float) Azure.INSTANCE.settingsManager.getSettingByName("SA Speed").getValDouble());
			if (Azure.INSTANCE.settingsManager.getSettingByName("SA Pitch").getValBoolean())
				mc.thePlayer.rotationPitch = mc.thePlayer.rotationPitch + (EntityUtils.getPitchChange(target)
						/ (float) Azure.INSTANCE.settingsManager.getSettingByName("SA Speed").getValDouble());
		}
	}

	private EntityPlayer getClosestPlayerToCursor(final float angle) {
		float distance = angle;
		EntityPlayer tempPlayer = null;
		for (final EntityPlayer player : mc.theWorld.playerEntities) {
			if (isValidEntity(player)) {
				final float yaw = EntityUtils.getYawChange(player);
				final float pitch = EntityUtils.getPitchChange(player);
				if (yaw > angle || pitch > angle) {
					continue;
				}
				final float currentDistance = (yaw + pitch) / 2F;
				if (currentDistance <= distance) {
					distance = currentDistance;
					tempPlayer = player;
				}
			}
		}
		return tempPlayer;
	}

	private boolean isValidEntity(final EntityPlayer player) {
		return Objects.nonNull(player) && player.isEntityAlive()
				&& player.getDistanceToEntity(mc.thePlayer) <= Azure.INSTANCE.settingsManager
						.getSettingByName("SA Range").getValDouble()
				&& player.ticksExisted > 20 && !player.isInvisibleToPlayer(mc.thePlayer);
	}
}
