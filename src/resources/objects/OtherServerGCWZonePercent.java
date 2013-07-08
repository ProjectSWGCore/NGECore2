package resources.objects;

public class OtherServerGCWZonePercent {
	
	private byte unknown = 0;
	private String server = "";
	private String zone = "";
	private int percent = 50;
	
	public OtherServerGCWZonePercent(byte unknown, String server, String zone, int percent) {
		this.unknown = unknown;
		this.server = server;
		this.zone = zone;
		this.percent = percent;
	}
	
	public OtherServerGCWZonePercent() {
		
	}
	
	public byte getUnknown() {
		return unknown;
	}
	
	public void setUnknown(byte unknown) {
		this.unknown = unknown;
	}
	
	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
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
