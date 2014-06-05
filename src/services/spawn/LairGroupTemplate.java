package services.spawn;

import java.util.Vector;

public class LairGroupTemplate extends SpawnGroup{
	
	private Vector<LairSpawnTemplate> lairSpawnTemplates;
	private String name;
	
	public LairGroupTemplate(String name, Vector<LairSpawnTemplate> lairSpawnTemplates) {
		this.name = name; 
		this.lairSpawnTemplates = lairSpawnTemplates;
	}

	public Vector<LairSpawnTemplate> getLairSpawnTemplates() {
		return lairSpawnTemplates;
	}

	public void setLairSpawnTemplates(Vector<LairSpawnTemplate> lairSpawnTemplates) {
		this.lairSpawnTemplates = lairSpawnTemplates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
