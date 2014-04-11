package resources.objects.resource;

public class ResourceConcentration {
	private float coordsX;
	private float coordsZ;
	private float concentration;

	public ResourceConcentration(){
		
	}
	
	public ResourceConcentration(float coordsX, float coordsZ, float concentration){
		this.coordsX = coordsX;
		this.coordsZ = coordsZ;
		this.concentration = concentration;
	}
	
	public float getCoordsX() {
		return coordsX;
	}

	public void setCoordsX(float coordsX) {
		this.coordsX = coordsX;
	}

	public float getCoordsZ() {
		return coordsZ;
	}

	public void setCoordsZ(float coordsZ) {
		this.coordsZ = coordsZ;
	}

	public float getConcentration() {
		return concentration;
	}

	public void setConcentration(float concentration) {
		this.concentration = concentration;
	}	
}
