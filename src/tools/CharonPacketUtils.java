package tools;

import java.io.UnsupportedEncodingException;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Charon 
 */

public class CharonPacketUtils {
	
    private final static char[] HEX = {
	    '0', '1', '2', '3', '4', '5', '6', '7',
	    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	  };
	
	public static String fromLong(long value) {
	    char[] hexs;
	    int i;
	    int c;

	    hexs = new char[16];
	    for (i = 0; i < 16; i++) {
	      c = (int)(value & 0xf);
	      hexs[16-i-1] = HEX[c];
	      value = value >> 4;
	    }
	    return new String(hexs);
	  }
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static String byteToHex(byte bytes) {
	    char[] hexChars = new char[2];
	    int v;

        v = bytes & 0xFF;
        hexChars[2] = hexArray[v >>> 4];
        hexChars[2 + 1] = hexArray[v & 0x0F];

	    return new String(hexChars);
	}
	
	public static void printAnalysis(IoBuffer pack){
		
		if (!NGECore.PACKET_DEBUG)
			return;
		
		byte[] packArray = pack.array();
		int lineCount = packArray.length/16;
		int actualIndex = 0;
		String buffString = "";
		String asciiString = "";
		for (int lineIndex = 0; lineIndex <= lineCount; lineIndex++)
        {
			byte[] ASCIIArray = new byte[16];
			buffString = "";
			asciiString = "";
            // remaining bytes per line
            int remaining = packArray.length - actualIndex;
            int byteColumnSize = 0;
            if (remaining < 16) byteColumnSize = remaining;
            else byteColumnSize = 16;
            for (int byteColumnIndex = 0; byteColumnIndex < byteColumnSize; byteColumnIndex++)
            {
                if (byteColumnIndex == 8)
                    buffString += "   ";
                else buffString += " ";
                	 
                buffString += ((Integer.toString( ( packArray[actualIndex] & 0xff ) + 0x100, 16).substring( 1 )).toUpperCase() + "");
                if (packArray[actualIndex] < 31 || packArray[actualIndex] == 13)
                    ASCIIArray[byteColumnIndex] = 32;
                else
                    ASCIIArray[byteColumnIndex] = packArray[actualIndex];

                actualIndex++;
            }
            try {
				asciiString +=  new String(ASCIIArray, "US-ASCII");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println(buffString + "   " + asciiString);
            

        }
		System.out.println("------------------------------------------------------------------------");
	}
}
