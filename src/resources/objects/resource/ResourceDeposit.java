package resources.objects.resource;

import com.sleepycat.persist.model.Persistent;

/** 
 * @author Charon 
 */

@Persistent(version=0)
public class ResourceDeposit {
	private float spawnCoordsX;
	private float spawnCoordsZ;
	private float spawnRadius;
	private float spawnConcentration;
	
	public ResourceDeposit(){
		
	}
	
	public void setSpawnCoordinatesX(float spawnCoordsX){
		this.spawnCoordsX = spawnCoordsX;
	}
	
	public float getSpawnCoordinatesX(){
		return this.spawnCoordsX;
	}
	
	public void setSpawnCoordinatesZ(float spawnCoordsZ){
		this.spawnCoordsZ = spawnCoordsZ;
	}
	
	public float getSpawnCoordinatesZ(){
		return this.spawnCoordsZ;
	}
	
	public void setSpawnRadius(float spawnRadius){
		this.spawnRadius = spawnRadius;
	}
	
	public float getSpawnRadius(){
		return spawnRadius;
	}
	
	public void setSpawnConcentration(float spawnConcentration){
		this.spawnConcentration = spawnConcentration;
	}
	
	public float getSpawnConcentration(){
		return this.spawnConcentration;
	}
}
