package azure.me.kizburb.azure.event.events;

import azure.me.kizburb.azure.event.Event;
import net.minecraft.network.Packet;

public class EventRecievePacket extends Event {
	
	private Packet packet;

	public EventRecievePacket(Packet packet) {
		this.packet = packet;
	}
	
	public Packet getPacket() {
		return packet;
	}
	
	public void setPacket(Packet packet) {
		this.packet = packet;
	}
}
