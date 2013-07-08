package resources.objects;

public class CurrentServerGCWZonePercent {
	
	private byte unknown = 0;
	private String zone = "";
	private int percent = 50;
	
	public CurrentServerGCWZonePercent(byte unknown, String zone, int percent) {
		this.unknown = unknown;
		this.zone = zone;
		this.percent = percent;
	}
	
	public CurrentServerGCWZonePercent() {
		
	}
	
	public byte getUnknown() {
		return unknown;
	}
	
	public void setUnknown(byte unknown) {
		this.unknown = unknown;
	}
	
	public String getZone() {
		return zone;
	}
	
	public void setZone(String zone) {
		this.zone = zone;
	}
	
	public int getPercent() {
		return percent;
	}
	
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
}
