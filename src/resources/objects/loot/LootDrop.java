package resources.objects.loot;

import java.util.ArrayList;
import java.util.List;

public class LootDrop {
	
	private List<String> elements = new ArrayList<String>();
	
	public LootDrop(){
		
	}
	
	public void addElement(String element){
		elements.add(element);
	}
	
	public List<String> getElements(){
		return elements;
	}
}
