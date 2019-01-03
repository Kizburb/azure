package azure.me.kizburb.azure.event.events;

import azure.me.kizburb.azure.event.Event;

public class EventMotionUpdate extends Event {
	
	private final State currentState;
	private float yaw, pitch;
	public double x, y, z;
	private boolean onGround, wasSprinting;
	
	public EventMotionUpdate(State currentState, float yaw, float pitch, double x, double y, double z, boolean onGround, boolean wasSprinting) {
		this.currentState = currentState;
		this.yaw = yaw;
		this.pitch = pitch;
		this.x = x;
		this.y = y;
		this.z = z;
		this.onGround = onGround;
		this.wasSprinting = wasSprinting;
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
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean wasSprinting() {
        return wasSprinting;
    }

    public State getCurrentState() {
        return currentState;
    }
    
    public enum State {
    	PRE, POST
    }
}
