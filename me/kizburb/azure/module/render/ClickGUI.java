package azure.me.kizburb.azure.module.render;

import azure.me.kizburb.azure.Azure;
import azure.me.kizburb.azure.module.Category;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.module.Module;
import de.Hero.settings.Setting;

public class ClickGUI extends Module {

	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
	}
	
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("New");
		options.add("JellyLike");
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Design", this, "New", options));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Sound", this, true));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("GuiRed", this, 255, 0, 255, true));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("GuiGreen", this, 26, 0, 255, true));
		Azure.INSTANCE.settingsManager.rSetting(new Setting("GuiBlue", this, 42, 0, 255, true));
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		mc.displayGuiScreen(Azure.INSTANCE.clickGui);
		toggle();
	}
}
