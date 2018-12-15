package azure.me.kizburb.azure.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.Azure;
import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Fly extends Module {

	private String mode = Azure.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString();

	public Fly() {
		super("Fly", Keyboard.KEY_NONE, Category.MOVEMENT);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("Hypixel");
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Fly Mode", this, "Vanilla", options));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.setDisplayName("Fly §7" + mode);

		if (mode.equalsIgnoreCase("Hypixel")) {
			double y;
			double y1;
			mc.thePlayer.motionY = 0;
			if (mc.thePlayer.ticksExisted % 3 == 0) {
				y = mc.thePlayer.posY = 1.0E-10D;
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true));
			}
			y1 = mc.thePlayer.posY + 1.0E-10D;
			mc.thePlayer.setPosition(mc.thePlayer.posX, y1, mc.thePlayer.posZ);
		}
		
		if (mode.equalsIgnoreCase("Vanilla"))
			mc.thePlayer.capabilities.isFlying = true;
	}

	@Override
	public void onDisable() {
		super.onDisable();

		if (mode.equalsIgnoreCase("Vanilla"))
			mc.thePlayer.capabilities.isFlying = false;
	}
}
