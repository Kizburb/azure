package azure.me.kizburb.azure.module.movement;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;

public class Sprint extends Module {
	
	public Sprint() {
		 super("Sprint", Keyboard.KEY_NONE, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0)
			mc.thePlayer.setSprinting(true);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		mc.thePlayer.setSprinting(false);
	}
	
	
}
