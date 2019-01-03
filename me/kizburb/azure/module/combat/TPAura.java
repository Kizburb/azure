package azure.me.kizburb.azure.module.combat;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import azure.me.kizburb.azure.event.EventTarget;
import azure.me.kizburb.azure.event.events.EventPreMotionUpdate;
import azure.me.kizburb.azure.module.Category;
import azure.me.kizburb.azure.module.Module;
import azure.me.kizburb.azure.utils.CombatUtils;
import azure.me.kizburb.azure.utils.PlayerUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;

public class TPAura extends Module {

	public TPAura() {
		super("TPAura", Keyboard.KEY_NONE, Category.COMBAT);
	}

	private EntityLivingBase entity;
	private boolean hit;

	@EventTarget
	public void onPreMotion(EventPreMotionUpdate event) {
		if (!this.isToggled())
			return;

		if (!this.hit) {
			this.entity = null;
			if ((this.mc.objectMouseOver != null) && (this.mc.objectMouseOver.entityHit != null)
					&& ((this.mc.objectMouseOver.entityHit instanceof EntityPlayer))) {
				this.entity = ((EntityPlayer) this.mc.objectMouseOver.entityHit);
			} else
				setTarget();
		}

		if ((this.entity != null) && (Mouse.isButtonDown(0))) {
			if (!this.hit) {
				this.hit = true;
				return;
			}
			if ((this.hit) && (this.mc.thePlayer.onGround)) {
				this.mc.thePlayer.jump();
			}
			if (this.mc.thePlayer.fallDistance > 0.0F) {
				event.yaw = CombatUtils.getRotations(this.entity)[0];
				event.pitch = CombatUtils.getRotations(this.entity)[1];
				if (this.mc.thePlayer.getDistanceToEntity(this.entity) > 3.5D) {
					Vec3 vec = this.mc.thePlayer.getVectorForRotation(0.0F, this.mc.thePlayer.rotationYaw);
					double x = this.mc.thePlayer.posX
							+ vec.xCoord * (this.mc.thePlayer.getDistanceToEntity(this.entity) - 1.0F);
					double z = this.mc.thePlayer.posZ
							+ vec.zCoord * (this.mc.thePlayer.getDistanceToEntity(this.entity) - 1.0F);
					double y = this.entity.getPosition().getY() + 0.25D;

					ArrayList<Vector3f> positions = PlayerUtils.vanillaTeleportPositions(x, y + 1.0D, z, 4.0D);
					for (int j = 0; j < 1; j++) {
						for (int i = 0; i < positions.size(); i++) {
							Vector3f pos = (Vector3f) positions.get(i);
							Vector3f oldPos = i == 0
									? new Vector3f((float) this.mc.thePlayer.posX, (float) this.mc.thePlayer.posY,
											(float) this.mc.thePlayer.posZ)
									: (Vector3f) positions.get(i - 1);
							this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
									pos.getX(), pos.getY(), pos.getZ(), false));
						}
					}
					this.mc.thePlayer.onCriticalHit(this.entity);
					this.mc.thePlayer.swingItem();
					this.mc.thePlayer.sendQueue
							.addToSendQueue(new C02PacketUseEntity(this.entity, C02PacketUseEntity.Action.ATTACK));
					this.hit = false;
				}
			}
		}
	}

	private void setTarget() {
		double closest = 2.147483647E9D;
		EntityLivingBase target = null;
		for (Object o : this.mc.theWorld.loadedEntityList) {
			if ((o instanceof EntityLivingBase)) {
				EntityLivingBase e = (EntityLivingBase) o;
				if (isValidTarget(e)) {
					double dist = this.mc.thePlayer.getDistanceToEntity(e);
					if (dist < closest) {
						closest = dist;
						target = e;
					}
				}
			}
		}
		this.entity = target;
	}

	private boolean isValidTarget(EntityLivingBase entity) {
		if (entity == this.mc.thePlayer)
			return false;
		if (entity.isInvisible())
			return false;
		if (!(entity instanceof EntityPlayer))
			return false;
		return true;
	}
}
