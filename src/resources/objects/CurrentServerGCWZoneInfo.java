package resources.objects;

public class CurrentServerGCWZoneInfo {
	
	private byte unknown1 = 0;
	private String zone = "";
	private int unknown2 = 0x0D05EA4E;
	private int percent = 50;
	
	public CurrentServerGCWZoneInfo(byte unknown1, String zone, int unknown2, int percent) {
		this.unknown1 = unknown1;
		this.zone = zone;
		this.unknown2 = unknown2;
		this.percent = percent;
	}
	
	public CurrentServerGCWZoneInfo() {
		
	}
	
	public byte getUnknown1() {
		return unknown1;
	}
	
	public void setUnknown1(byte unknown1) {
		this.unknown1 = unknown1;
	}
	
	public String getZone() {
		return zone;
	}
	
	public void setZone(String zone) {
		this.zone = zone;
	}
	
	public int getUnknown2() {
		return unknown2;
	}
	
	public void setUnknown2(int unknown2) {
		this.unknown2 = unknown2;
	}
	
	public int getPercent() {
		return percent;
	}
	
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
}
