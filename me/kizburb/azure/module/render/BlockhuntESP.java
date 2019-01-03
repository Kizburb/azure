package azure.me.kizburb.azure.module.render;

import org.lwjgl.input.Keyboard;

import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.Event3D;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import azure.me.kizburb.azure.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

public class BlockhuntESP extends Module {
	
	public BlockhuntESP() {
		super("BlockhuntESP", Keyboard.KEY_NONE, Category.RENDER);
	}
	
	@EventTarget
	public void onRender(Event3D event) {
		if (!this.isToggled()) return;
		
		for (Object entity : mc.theWorld.loadedEntityList)
			if (entity instanceof EntityLiving && ((Entity) entity).isInvisible()) {
				double x = ((Entity) entity).posX;
				double y = ((Entity) entity).posY;
				double z = ((Entity) entity).posZ;
				float alpha;
				if (mc.thePlayer.getDistanceToEntity((Entity) entity) >= 0.5)
					alpha = 0.5F - MathHelper.abs(MathHelper.sin(Minecraft.getSystemTime() % 1000L / 1000.0F * (float) Math.PI * 1.0F) * 0.3F);
				else
					alpha = 0;
				RenderUtils.box(x - 0.5, y - 0.1, z - 0.5, x + 0.5, y + 0.9, z + 0.5, 0F, 1F, 0F, alpha);
			}
	}

}
