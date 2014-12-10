/**
 * 
 */
package concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zha
 *
 */
public class TestConcurrentHashMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		map.put("kk", "value1");
		map.put("aa1", "value2");
		map.put("aa2", "value2");
		map.put("aa3", "value2");
		map.put("aa4", "value2");
		map.put("aa5", "value2");
		map.put("aa6", "value2");
		map.put("aa7", "value2");
		map.put("ss", "value3");
		System.out.println(map.toString());

		map.remove("aa4");

	}

	/*
  public V remove(Object key) {
		int hash = hash(key);
		Segment<K, V> s = segmentForHash(hash);
		return s == null ? null : s.remove(key, hash, null);
	}

	final V remove(Object key, int hash, Object value) {
		if (!tryLock())
			scanAndLock(key, hash);
		V oldValue = null;
		try {
			HashEntry<K, V>[] tab = table;
			int index = (tab.length - 1) & hash;
			HashEntry<K, V> e = entryAt(tab, index);
			HashEntry<K, V> pred = null;
			while (e != null) {
				K k;
				HashEntry<K, V> next = e.next;
				if ((k = e.key) == key || (e.hash == hash && key.equals(k))) {
					V v = e.value;
					if (value == null || value == v || value.equals(v)) {
						if (pred == null)
							setEntryAt(tab, index, next);
						else
							pred.setNext(next);
						++modCount;
						--count;
						oldValue = v;
					}
					break;
				}
				pred = e;
				e = next;
			}
		} finally {
			unlock();
		}
		return oldValue;
	}
	
	*/

}
