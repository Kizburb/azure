package azure.me.kizburb.azure.event.events;

import azure.me.kizburb.azure.event.Event;

public class Event3D extends Event {
	
	private float partialTicks;
	
	public Event3D(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}
}
