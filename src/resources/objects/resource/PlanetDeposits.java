package resources.objects.resource;

import java.util.List;

import com.sleepycat.persist.model.Persistent;

@Persistent(version=0)
public class PlanetDeposits {
	
	private int planetId;
	private List<ResourceDeposit> depositList;
	
	public PlanetDeposits(){
		
	}
	
	public void setPlanetId(int planetId){
		this.planetId = planetId;
	}
	
	public int getPlanetId(){
		return this.planetId;
	}
	
	public void addDeposits(List<ResourceDeposit> depositList){
		this.depositList = depositList;
	}	
	
	public List<ResourceDeposit> getDeposits(){
		return this.depositList;
	}	
}
