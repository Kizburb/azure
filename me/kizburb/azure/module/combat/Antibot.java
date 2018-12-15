package azure.me.kizburb.azure.module.combat;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.Azure;
import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventRecievePacket;
import azure.me.kizburb.azure.event.events.EventUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

public class Antibot extends Module {

	public Antibot() {
		super("Antibot", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Advanced");
		options.add("Watchdog");
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Antibot Mode", this, "Advanced", options));
	}

	@EventTarget
	public void onRecievePacket(EventRecievePacket event) {
		String mode = Azure.INSTANCE.settingsManager.getSettingByName("Antibot Mode").getValString();

		if (mode.equalsIgnoreCase("Advanced") && event.getPacket() instanceof S0CPacketSpawnPlayer) {
			S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) event.getPacket();
			double posX = packet.getX() / 32D;
			double posY = packet.getY() / 32D;
			double posZ = packet.getZ() / 32D;

			double diffX = mc.thePlayer.posX - posX;
			double diffY = mc.thePlayer.posY - posY;
			double diffZ = mc.thePlayer.posZ - posZ;

			double dist = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

			if (dist <= 17D && posX != mc.thePlayer.posX && posY != mc.thePlayer.posY && posZ != mc.thePlayer.posZ) {
				event.setCancelled(true);
			}
		}
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		String mode = Azure.INSTANCE.settingsManager.getSettingByName("Antibot Mode").getValString();
		this.setDisplayName("Antibot §7" + mode);

		if (mode.equalsIgnoreCase("Watchdog"))
			for (Object entity : mc.theWorld.loadedEntityList)
				if (((Entity) entity).isInvisible() && entity != mc.thePlayer)
					mc.theWorld.removeEntity((Entity) entity);
	}
}
