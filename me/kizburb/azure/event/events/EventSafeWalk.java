package azure.me.kizburb.azure.event.events;

import azure.me.kizburb.azure.event.Event;

public class EventSafeWalk extends Event {
	
	public boolean safe;
	
	public EventSafeWalk(boolean safe) {
		this.safe = safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}
}
