package lms.model;

import java.util.Map;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class LibraryCollection {
	// private:
	private String collectionCode;
	private String name;
	private Map<Integer, Holding> holdings = new HashMap<Integer, Holding>();

	public LibraryCollection(String code, String name) {
		this.collectionCode = code;
		this.name = name;
	}
	
	/* Return a holding given a certain ID */
	public Holding getHolding(int code) {
		// check holding with this key exists
		if (this.holdings.containsKey(code)) {
			return this.holdings.get(code);
		} else {
			return null;
		}
	}
	
	
	/* Add a new holding to the Map, so long as the code doesn't already exist */
	public boolean addHolding(Holding h) {
		if (this.holdings.containsKey(h.code)) {
			return false;
		} else {
			// add the new holding
			this.holdings.put(h.code, h);
			
			return this.holdings.containsKey(h.code);
		}
	}
	
	
	/* Remove a holding from the map, if it exists */
	public boolean removeHolding(int code) {
		if (!this.holdings.containsKey(code)) {
			return false;
		} else {
			// remove the holding
			this.holdings.remove(code);
			
			return !this.holdings.containsKey(code);
		}
	}
	
	
	/* Convenience method to get holdings as Array */
	public Holding[] getAllHoldings() {
		// return null immediately if there are no holdings
		if (this.holdings.isEmpty()) {
			return null;
		}
		
		// fetch the collection - use generic.
		Collection<Holding> c = this.holdings.values();
				
		Holding[] holdings_ = (c.toArray(new Holding[c.size()]));
		
		// Will sort by Holding.code - see Holding.compareTo
		Arrays.sort(holdings_);
		
		return holdings_;
	}
	
	/* Create a string representation of this object */
	public String toString() {
		String out = String.format("%s:%s", this.collectionCode, this.name);
		
		// iterate holding IDs
		if (!this.holdings.isEmpty()) {
			String idString = ":";
			
			// declare a Integer Array to hold the keys
			int sizeOf = this.holdings.keySet().size();
			Integer[] keys_ = this.holdings.keySet().toArray(new Integer[sizeOf]);
			
			// sort the array ascending
			Arrays.sort(keys_);
			
			// for each key, add it to the end of the string
			for (int i: keys_) {
				idString = String.format("%s%d,", idString, i);
			}
			
			// remove trailing comma
			idString = idString.substring(0, idString.length()-1);
			
			// add to initial string
			out = out + idString;
		}		
		return out;
	}
}
