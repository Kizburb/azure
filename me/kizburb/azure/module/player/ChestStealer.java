package azure.me.kizburb.azure.module.player;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.Azure;
import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventMotionUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import azure.me.kizburb.azure.utils.TimerHelper;
import de.Hero.settings.Setting;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.ItemStack;

public class ChestStealer extends Module {
	
	private final TimerHelper timer = new TimerHelper();
	
	public ChestStealer() {
		super("ChestStealer", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	@Override
	public void setup() {
		Azure.INSTANCE.settingsManager.rSetting(new Setting("Steal Delay", this, 125L, 0L, 1000L, true));
	}
	
	@EventTarget
	public void onEventCalled(EventMotionUpdate event) {
		if (event.getCurrentState() == EventMotionUpdate.State.PRE) {
			if (mc.currentScreen instanceof GuiChest) {
				GuiChest chest = (GuiChest) mc.currentScreen;
				if (isChestEmpty(chest) || isInventoryFull())
					mc.thePlayer.closeScreen();
				
				for (int index = 0; index < chest.getLowerChestInventory().getSizeInventory(); index++) {
					ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
					if (stack == null)
						continue;
					if (timer.hasReached((long) Azure.INSTANCE.settingsManager.getSettingByName("Steal Delay").getValDouble())) {
						mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
						timer.reset();
					}
				}
			}
		}
	}
	
	private boolean isChestEmpty(GuiChest chest) {
		for (int index = 0; index <= chest.getLowerChestInventory().getSizeInventory(); index++) {
			ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
			if (stack != null)
				return false;
		}
		return true;
	}
	
	public boolean isInventoryFull() {
		for (int index = 9; index <= 44; index++) {
			ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
			if (stack == null)
				return false;
		}
		return true;
	}

}
