package services.sui;

import java.util.Vector;

public class SUITableItem {
	private Vector<SUITableItem> children = new Vector<SUITableItem>();
	private String itemName;
	private int index;
	
	public SUITableItem() { }
	
	public SUITableItem(String itemName, int index) {
		this.itemName = itemName;
		this.setIndex(index);
	}
	
	public Vector<SUITableItem> getChildren() {
		return children;
	}
	public void setChildren(Vector<SUITableItem> children) {
		this.children = children;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
