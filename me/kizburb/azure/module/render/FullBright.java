package azure.me.kizburb.azure.module.render;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;

public class FullBright extends Module {
	
	private float oldBrightness;
	
	public FullBright() {
		super("FullBright", Keyboard.KEY_NONE, Category.RENDER);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		oldBrightness = mc.gameSettings.gammaSetting;
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (mc.gameSettings.gammaSetting < 100F) {
			mc.gameSettings.gammaSetting += 0.1;
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		mc.gameSettings.gammaSetting = oldBrightness;
	}
}
