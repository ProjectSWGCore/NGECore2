package services.object;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class DuplicateId {
	
	@PrimaryKey
	private String key;
	private long objectId;
	
	public DuplicateId(String key, long objectId) {
		this.key = key;
		this.objectId = objectId;
	}
	
	public DuplicateId() {
		
	}
	
	public long getObjectId() {
		return objectId;
	}
	
}
