package azure.me.kizburb.azure.module;

import java.util.ArrayList;

import azure.me.kizburb.azure.module.movement.*;
import azure.me.kizburb.azure.module.player.*;
import azure.me.kizburb.azure.module.render.*;

public class ModuleManager {

	private ArrayList<Module> modules = new ArrayList<Module>();
	
	public ModuleManager() {
		// Combat
		
		// Movement
		modules.add(new Sprint());
		modules.add(new Fly());
		modules.add(new Step());
		
		// Player
		modules.add(new NoFall());
		
		// Render
		modules.add(new FullBright());
		
		// None
	}
	
	public ArrayList<Module> getModules() {
		return modules;
	}
	
	public Module getModuleByName(String name) {
		return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
}