package azure.me.kizburb.azure.utils;

public class TimerHelper {

	private long lastMS = 0L;

	public int convertToMS(int d) {
		return 1000 / d;
	}

	public long getCurrentMS() {
		return System.nanoTime() / 1000000L;
	}

	public boolean hasReached(long milliseconds) {
		return getCurrentMS() - lastMS >= milliseconds;
	}
	
	public boolean hasReached(double milliseconds) {
        return getCurrentMS() - lastMS >= milliseconds;
    }

	public boolean hasTimeReached(long delay) {
		return System.currentTimeMillis() - lastMS >= delay;
	}
	
	public boolean hasTimeReached(double delay) {
		return System.currentTimeMillis() - lastMS >= delay;
	}

	public long getDelay() {
		return System.currentTimeMillis() - lastMS;
	}

	public void reset() {
		lastMS = getCurrentMS();
	}

	public void setLastMS() {
		lastMS = System.currentTimeMillis();
	}

	public void setLastMS(long lastMS) {
		this.lastMS = lastMS;
	}
}
