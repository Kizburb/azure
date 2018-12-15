package azure.me.kizburb.azure.module.combat;

import azure.me.kizburb.azure.Azure;
import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventSendPacket;
import azure.me.kizburb.azure.event.events.EventUpdate;
import azure.me.kizburb.azure.module.Category;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.module.Module;
import azure.me.kizburb.azure.utils.PlayerUtils;
import de.Hero.settings.Setting;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {

	public Criticals() {
		super("Criticals", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Packet");
		options.add("MiniJump");
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Crits Mode", this, "Packet", options));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		String mode = Azure.INSTANCE.settingsManager.getSettingByName("Crits Mode").getValString();
		this.setDisplayName("Criticals §7" + mode);
	}

	@EventTarget
	public void onSendPacket(EventSendPacket event) {
		String mode = Azure.INSTANCE.settingsManager.getSettingByName("Crits Mode").getValString();

		if (canCrit()) {
			if (event.getPacket() instanceof C02PacketUseEntity) {
				C02PacketUseEntity packet = (C02PacketUseEntity) event.getPacket();
				if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
					if (mode.equalsIgnoreCase("Packet")) {
						mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
								mc.thePlayer.posX, mc.thePlayer.posY + .1625, mc.thePlayer.posZ, false));
						mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
								mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
						mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
								mc.thePlayer.posX, mc.thePlayer.posY + 4.0E-6, mc.thePlayer.posZ, false));
						mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
								mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
						mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
								mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-6, mc.thePlayer.posZ, false));
						mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
								mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
						mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
					}
				}
			}
			if (mode.equalsIgnoreCase("MiniJump")) {
				mc.thePlayer.jump();
				mc.thePlayer.motionY -= .30000001192092879;
			}
		}
	}

	private boolean canCrit() {
		return !PlayerUtils.isInLiquid() && mc.thePlayer.onGround;
	}
}
