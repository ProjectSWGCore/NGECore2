package resources.z.exp.mission;

import com.sleepycat.persist.model.Persistent;

import engine.resources.common.CRC;
import engine.resources.scene.Point3D;

@Persistent
public class MissionLocation {
	
	private float x, y, z;
	private long objectId;
	private String planet;
	
	public MissionLocation(Point3D position, long objectId, String planet) {
		this.x = position.x;
		this.y = position.y;
		this.z = position.z;
		this.objectId = objectId;
		this.planet = planet;
	}
	
	public MissionLocation() {
		
	}
	
	public Point3D getPosition() {
		return new Point3D(x, y, z);
	}
	
	public long getObjectId() {
		return objectId;
	}
	
	public String getPlanet() {
		return planet;
	}
	
	public int getPlanetCRC() {
		if (planet != null) {
			return CRC.StringtoCRC(planet);
		} else {
			return 0;
		}
	}
	
}
