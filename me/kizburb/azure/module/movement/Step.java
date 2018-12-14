package azure.me.kizburb.azure.module.movement;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;

public class Step extends Module {
	
	public Step() {
		super("Step", Keyboard.KEY_NONE, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.thePlayer.stepHeight = 1.5F;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		mc.thePlayer.stepHeight = 0.5F;
	}
}
