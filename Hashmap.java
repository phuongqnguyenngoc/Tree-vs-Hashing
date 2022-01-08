import java.util.ArrayList;
import java.lang.Math;

public class Hashmap<K,V> implements MapSet<K,V> {
    private Object[] hmap ;
    private int taken;
    private int collision;
    private int numItems;

    //constructor initialize the array of given size and gives each position in the array a BSTMap
    public Hashmap(int size) {
        this.hmap = new Object[size];
        for (int i = 0; i < hmap.length; i ++) {
            hmap[i] = new BSTMap<>(new StringAscending());
        }
        this.taken = 0;
        this.numItems = 0;
    }
    
    //return the index to put the new key in the array 
    public int hashFunction(K new_key) {
        int number = Math.abs(((String)(new_key)).hashCode());
        int index = number % hmap.length;
        return index;
    }

    // adds or updates a key-value pair. Check special case then call the put method on the root Node
    public V put( K new_key, V new_value ) {
        //ensure the array always less than half full
        if (this.taken >= this.hmap.length/2) {
            ArrayList<KeyValuePair<K,V>> set = this.entrySet();
            this.hmap = new Object[this.hmap.length*2];
            for (int i = 0; i < hmap.length; i ++) {
                hmap[i] = new BSTMap<>(new StringAscending());
            }
            this.taken = 0;
            this.collision = 0;
            this.numItems = 0;
            for (int i = 0; i < set.size(); i ++) {
                this.put(set.get(i).getKey(), set.get(i).getValue());
            }
        }
        //access the BSTMap of the indexed position
        BSTMap<K,V> map = (BSTMap<K,V>)(hmap[this.hashFunction(new_key)]);
        
        //put the key into the BSTMap
        V val = map.put(new_key, new_value);
        if (map.size() == 1 && val == null) {
            this.taken += 1;
            this.numItems += 1;
        } else {   
            if (val == null)  {
                this.collision += 1;
                this.numItems += 1;
            }  
                       
        }

        return val;    
    }

    //return true if the key has already existed
    public boolean containsKey( K key ) {
        Object map = hmap[this.hashFunction(key)];
        if (map == null) {
            return false;
        }
        return ((BSTMap<K,V>)(map)).containsKey(key);
    }

    //return the value of the key
    public V get( K key ) {
        return ((BSTMap<K,V>)(hmap[this.hashFunction(key)])).get(key);
    }


    //return an ArrayList of all the keys
    public ArrayList<K> keySet() {
        ArrayList<K> key = new ArrayList<K>();
        for (int i = 0; i < hmap.length; i ++) {
            // key.add((BSTMap<K,V>)(hmap[i])).keySet());
            BSTMap<K,V> map = (BSTMap<K,V>)(hmap[i]);
            ArrayList<K> k = map.keySet();
            if (k != null) {
                for (int j = 0; j < k.size(); j ++) {
                    key.add(k.get(j));
                }
            }
            
            
        }
        return key;
    }

    //return an ArrayList of all the values
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<V>();
        for (int i = 0; i < hmap.length; i ++) {
            // key.add((BSTMap<K,V>)(hmap[i])).keySet());
            BSTMap<K,V> map = (BSTMap<K,V>)(hmap[i]);
            ArrayList<V> v = map.values();
            if (v != null) {
                for (int j = 0; j < v.size(); j ++) {
                    values.add(v.get(j));
                }
            }
            
            
        }
        return values;
    }

    //return an ArrayList of all the KeyValuePair
    public ArrayList<KeyValuePair<K,V>> entrySet() {
        ArrayList<KeyValuePair<K,V>> set = new ArrayList<KeyValuePair<K,V>>();
        for (int i = 0; i < hmap.length; i ++) {
            // key.add((BSTMap<K,V>)(hmap[i])).keySet());
            BSTMap<K,V> map = (BSTMap<K,V>)(hmap[i]);
            ArrayList<KeyValuePair<K,V>> kv = map.entrySet();
            if (kv != null) {
                for (int j = 0; j < kv.size(); j ++) {
                    set.add(kv.get(j));
                }
            }
            
            
        }
        return set;
    }

    //return the number of collisions
    public int getCollision() {
        return this.collision;
    }

    //return number of items
    public int size() {
        return this.numItems;
    }

    //clear the hashmap
    public void clear() {
        this.hmap = new Object[this.hmap.length];
        for (int i = 0; i < hmap.length; i ++) {
            hmap[i] = new BSTMap<>(new StringAscending());
        }
        this.taken = 0;
        this.collision = 0;
        this.numItems = 0;
    }

    //return ArrayList of the N most frequent KeyValuePair
    public ArrayList<KeyValuePair<K,V>> getMostFrequent(int most) {
        ArrayList<KeyValuePair<K,V>> set = this.entrySet();
        MaxHeap<K,V> mostFrequent = new MaxHeap<K,V>(1280);
        for (KeyValuePair<K,V> kv : set) {
            mostFrequent.add(kv);
        }
        ArrayList<KeyValuePair<K,V>> frequent = new ArrayList<KeyValuePair<K,V>>();
        for (int i = 0; i < most ; i ++ ) {
            frequent.add(mostFrequent.remove());
        }
        return frequent;

    }

    


    public static void main (String[] args) {
        Hashmap<String,Integer> test = new Hashmap<String,Integer>(20);
        System.out.println(test.put("Ant", 1));
        // System.out.println(test.get("Ant"));
        test.put("Bear", 2);
        test.put("Cat", 1);
        test.put("Dog", 55);
        test.put("Eagle", 1);
        test.put("Fly", 22);

        System.out.println(test.containsKey("Dog"));
        System.out.println(test.containsKey("Turtle"));

        System.out.println(test.get("Dog"));

        System.out.println(test.entrySet());
        System.out.println(test.keySet());
        System.out.println(test.values());

        System.out.println(test.getCollision());

        test.put("Duck", 99);
        test.put("Chicken", 2);
        test.put("Dolphin", 88);
        test.put("Hedgehog", 2);
        test.put("Whale", 11);
        System.out.println(test.hashFunction("Bird"));
        test.put("Bird", 2);

        System.out.println(test.entrySet());

        System.out.println(test.getMostFrequent(5));
    }
}