package services.object;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import engine.resources.objects.SWGObject;

public class ObjectList<K, V> implements Map<K, V>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Map<K, V> objectList = new ConcurrentHashMap<K, V>();
	private Map<K, String> history = new ConcurrentHashMap<K, String>();
	
	public ObjectList() {
		
	}
	
	public void clear() {
		System.err.println("ObjectList was wiped!");
		try {
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
		objectList.clear();
	}
	
	public boolean containsKey(Object k) {
		return objectList.containsKey(k);
	}
	
	public boolean containsValue(Object v) {
		return objectList.containsValue(v);
	}
	
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return objectList.entrySet();
	}
	
	public V get(Object key) {
		return objectList.get(key);
	}
	
	public boolean isEmpty() {
		return objectList.isEmpty();
	}
	
	public Set<K> keySet() {
		return objectList.keySet();
	}
	
	public V put(K key, V value) {
		if ((objectList.containsKey((Long) key) && !((SWGObject) objectList.get(key)).getTemplate().equals(((SWGObject) value).getTemplate())) ||
		(history.containsKey((Long) key) && !history.get(key).equals(((SWGObject) value).getTemplate()))) {
			System.err.println("Attempted to register duplicate Id. Old template: " + ((SWGObject) objectList.get(key)).getTemplate() + " New template: " + ((SWGObject) value).getTemplate());
			
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		objectList.put(key, value);
		
		return value;
	}
	
	public void putAll(Map<? extends K, ? extends V> objects) {
		objectList.putAll(objects);
	}
	
	@SuppressWarnings("unchecked")
	public V remove(Object object) {
		/*if (object instanceof SWGObject) System.err.println("Object was removed! " + ((SWGObject) objectList.get(object)).getTemplate());
		try {
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		if (objectList.containsKey(object)) {
			history.put((K) object, ((SWGObject) objectList.get(object)).getTemplate());
		}
		return objectList.remove(object);
	}
	
	public int size() {
		return objectList.size();
	}
	
	public Collection<V> values() {
		return objectList.values();
	}
	
}
