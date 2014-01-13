package resources.common;

import resources.objects.Delta;

public class RGB extends Delta {
	
	private byte red, green, blue;
	
	public RGB(byte red, byte green, byte blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public RGB(int red, int green, int blue) {
		this.red = (byte) red;
		this.green = (byte) green;
		this.blue = (byte) blue;
	}
	
	public byte getRed() {
		return red;
	}
	
	public byte getGreen() {
		return green;
	}
	
	public byte getBlue() {
		return blue;
	}
	
	public byte[] getBytes() {
		return new byte[] { red, green, blue };
	}
	
}
