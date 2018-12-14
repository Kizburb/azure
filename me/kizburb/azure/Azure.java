package azure.me.kizburb.azure;

import org.lwjgl.opengl.Display;

import azure.me.kizburb.azure.event.EventManager;

public class Azure {

	public String name = "Azure", version = "1", creator = "Kizburb";

	public static Azure INSTANCE = new Azure();

	public EventManager eventManager;
	
	public void startClient() {
		eventManager = new EventManager();
		
		System.out.println("[" + name + "] Starting client Build " + version + ", by " + creator);
		Display.setTitle(name + " | Build " + version);
		
		eventManager.register(this);
	}
	
	public void endClient() {
		eventManager.unregister(this);
	}
}
