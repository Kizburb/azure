package azure.me.kizburb.azure;

import org.lwjgl.opengl.Display;

public class Azure {

	public String name = "Azure", version = "1", creator = "Kizburb";

	public static Azure INSTANCE = new Azure();

	public void startClient() {
		System.out.println("[" + name + "] Starting client Build " + version + ", by " + creator);
		Display.setTitle(name + " | Build " + version);
	}
}
