package resources.objects;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.SimpleBufferAllocator;

import resources.common.StringUtilities;

public abstract class ListObject implements IListObject {
	
	protected final Object objectMutex = new Object();
	public SimpleBufferAllocator bufferPool = new SimpleBufferAllocator();
	
	protected String getAsciiString(ByteBuffer buffer) {
		return StringUtilities.getAsciiString(buffer);
	}
	
	protected String getUnicodeString(ByteBuffer buffer) {
		return StringUtilities.getUnicodeString(buffer);
	}
	
	protected byte[] getAsciiString(String string) {
		return StringUtilities.getAsciiString(string);
	}
	
	protected byte[] getUnicodeString(String string) {
		return StringUtilities.getUnicodeString(string);
	}

}
