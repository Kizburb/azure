package azure.me.kizburb.azure;

import org.lwjgl.opengl.Display;

import azure.me.kizburb.azure.event.EventManager;
import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventKey;
import azure.me.kizburb.azure.module.ModuleManager;

public class Azure {

	public String name = "Azure", version = "1", creator = "Kizburb";

	public static Azure INSTANCE = new Azure();

	public EventManager eventManager;
	public ModuleManager moduleManager;
	
	public void startClient() {
		eventManager = new EventManager();
		moduleManager = new ModuleManager();
		
		System.out.println("[" + name + "] Starting client Build " + version + ", by " + creator);
		Display.setTitle(name + " | Build " + version);
		
		eventManager.register(this);
	}
	
	public void endClient() {
		eventManager.unregister(this);
	}
	
	@EventTarget
	public void onKey(EventKey event) {
		moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
	}
}
