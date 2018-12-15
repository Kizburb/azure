package azure.me.kizburb.azure.event.events;

import azure.me.kizburb.azure.event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event {
	
	private Packet packet;

    public EventSendPacket(Packet packet) {
        packet = null;
        setPacket(packet);
    }

    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }

}
