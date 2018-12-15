package azure.me.kizburb.azure.module;

import java.util.ArrayList;

import azure.me.kizburb.azure.module.combat.*;
import azure.me.kizburb.azure.module.movement.*;
import azure.me.kizburb.azure.module.player.*;
import azure.me.kizburb.azure.module.render.*;

public class ModuleManager {

	private ArrayList<Module> modules = new ArrayList<Module>();
	
	public ModuleManager() {
		// Combat
		modules.add(new KillAura());
		modules.add(new Antibot());
		modules.add(new AutoArmor());
		modules.add(new Criticals());
		
		// Movement
		modules.add(new Sprint());
		modules.add(new Fly());
		modules.add(new Step());
		modules.add(new LongJump());
		modules.add(new Speed());
		modules.add(new Phase());
		
		// Player
		modules.add(new NoFall());
		
		// Render
		modules.add(new FullBright());
		modules.add(new ClickGUI());
		
		// None
	}
	
	public ArrayList<Module> getModules() {
		return modules;
	}
	
	public Module getModuleByName(String name) {
		return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
}
