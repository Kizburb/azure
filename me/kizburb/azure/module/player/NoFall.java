package azure.me.kizburb.azure.module.player;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {
	
	public NoFall() {
		super("NoFall", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (mc.thePlayer.fallDistance > 3F)
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	}
}
