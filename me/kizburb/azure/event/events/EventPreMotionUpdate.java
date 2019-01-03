package azure.me.kizburb.azure.event.events;

import azure.me.kizburb.azure.event.Event;

public class EventPreMotionUpdate extends Event {

	public float yaw;
	public float pitch;
	private boolean ground;
	public double x, y, z;
	
	public EventPreMotionUpdate(float yaw, float pitch, double x, double y, double z, boolean ground) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.x = x;
		this.y = y;
		this.z = z;
		this.ground = ground;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public boolean onGround() {
		return ground;
	}
	
	public void setGround(boolean ground) {
		this.ground = ground;
	}
}
