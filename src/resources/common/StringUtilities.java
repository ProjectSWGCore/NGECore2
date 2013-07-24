package resources.common;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class StringUtilities {
	
	public static String getAsciiString(ByteBuffer buffer) {
		return getString(buffer, "US-ASCII");
	}
	
	public static String getUnicodeString(ByteBuffer buffer) {
		return getString(buffer, "UTF-16LE");
	}
	
	public static byte[] getAsciiString(String string) {
		return getString(string, "US-ASCII");
	}
	
	public static byte[] getUnicodeString(String string) {
		return getString(string, "UTF-16LE");
	}
	
	private static String getString(ByteBuffer buffer, String charFormat) {
		String result;
		int length;
		
		if (charFormat == "UTF-16LE") {
			length = buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
		} else {
			length = buffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
		}
		
		int bufferPosition = buffer.position();
		
		try {
			result = new String(buffer.array(), bufferPosition, length, charFormat);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		
		buffer.position(bufferPosition + length);
		
		return result;
	}
	
	private static byte[] getString(String string, String charFormat) {
		ByteBuffer result;
		int length = 2 + string.length();
		
		if (charFormat == "UTF-16LE") {
			result = ByteBuffer.allocate(length * 2).order(ByteOrder.LITTLE_ENDIAN);
			result.putInt(string.length());
		} else {
			result = ByteBuffer.allocate(length).order(ByteOrder.LITTLE_ENDIAN);
			result.putShort((short)string.length());
		}
		
		try {
			result.put(string.getBytes(charFormat));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new byte[] { };
		}
		
		return result.array();		
	}

}
