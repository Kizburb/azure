package azure.me.kizburb.azure.event.events;

import azure.me.kizburb.azure.event.Event;

public class EventKey extends Event {

	private int key;
	
	public EventKey(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}
}
