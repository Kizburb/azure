package azure.me.kizburb.azure.module.movement;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;

public class Fly extends Module {

	public Fly() {
		super("Fly", Keyboard.KEY_NONE, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.setDisplayName("Fly §7Normal");
		
		mc.thePlayer.capabilities.isFlying = true;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		mc.thePlayer.capabilities.isFlying = false;
	}
}
