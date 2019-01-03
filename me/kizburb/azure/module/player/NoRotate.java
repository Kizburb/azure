package azure.me.kizburb.azure.module.player;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventRecievePacket;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module {
	
	public NoRotate() {
		super("NoRotate", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	@EventTarget
	public void onPacketRecieve(EventRecievePacket event) {
		if(event.getPacket() instanceof S08PacketPlayerPosLook) {
			S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.getPacket();
			packet.yaw = this.mc.thePlayer.rotationYaw;
			packet.pitch = this.mc.thePlayer.rotationPitch;
		}
	}

}
