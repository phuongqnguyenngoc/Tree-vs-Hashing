import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

import java.lang.Math;

public class LinkedListHashmap<K,V> implements MapSet<K,V> {
    private Object[] hmap ;
    private int taken;
    private int collision;
    private int numItems;

    //constructor initialize the array of given size and gives each position in the array a LinkedList
    public LinkedListHashmap(int size) {
        this.hmap = new Object[size];
        for (int i = 0; i < hmap.length; i ++) {
            hmap[i] = new LinkedList<KeyValuePair<K,V>>();
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
                hmap[i] = new LinkedList<KeyValuePair<K,V>>();
            }
            this.taken = 0;
            this.collision = 0;
            this.numItems = 0;
            for (int i = 0; i < set.size(); i ++) {
                this.put(set.get(i).getKey(), set.get(i).getValue());
            }
        }

        
        //access the LinkedList of the indexed position
        LinkedList<KeyValuePair<K,V>> map =  (LinkedList<KeyValuePair<K,V>>)(hmap[this.hashFunction(new_key)]);
        //put the key into the LinkedList
        if (map.size() == 0) {
            KeyValuePair<K,V> newKV = new KeyValuePair<K,V>(new_key, new_value);
            map.addFirst(newKV);
            taken ++;
            this.numItems ++;
            return null;  
        } else {
            for (KeyValuePair<K,V> kv : map) {
                if (  new_key.equals(kv.getKey()) ) {
                    V val = kv.getValue();
                    
                    kv.setValue(new_value);
                    return val;
                } 
            }
                
            KeyValuePair<K,V> newKV = new KeyValuePair<K,V>(new_key, new_value);
            map.addLast(newKV);
            this.collision ++;
            this.numItems ++;
            return null;                           
        }     
    }

    //return true if the key has already existed
    public boolean containsKey( K key ) {
        LinkedList<KeyValuePair<K,V>> map =  (LinkedList<KeyValuePair<K,V>>)(hmap[this.hashFunction(key)]);
        if (map == null) {
            return false;
        } else {
            for (KeyValuePair<K,V> kv: map) {
                if (  key.equals(kv.getKey()) ) {
                    return true;
                }
            } 
            return false;
        }
    }

    //return the value of the key
    public V get( K key ) {
        LinkedList<KeyValuePair<K,V>> map =  (LinkedList<KeyValuePair<K,V>>)(hmap[this.hashFunction(key)]);
        if (map == null) {
            return null;
        } else {
            for (KeyValuePair<K,V> kv: map) {
                if (  key.equals(kv.getKey()) ) {
                    return kv.getValue();
                }
            } 
            return null;
        }
    }

    //return an ArrayList of all the keys
    public ArrayList<K> keySet() {
        ArrayList<K> key = new ArrayList<K>();
        for (int i = 0; i < hmap.length; i ++) {
            // key.add((BSTMap<K,V>)(hmap[i])).keySet());
            LinkedList<KeyValuePair<K,V>> map =  (LinkedList<KeyValuePair<K,V>>)(hmap[i]);
            for (KeyValuePair<K,V> kv: map) {
                key.add(kv.getKey());
            } 
            
            
        }
        return key;
    }

    //return an ArrayList of all the values
    public ArrayList<V> values() {
        ArrayList<V> val = new ArrayList<V>();
        for (int i = 0; i < hmap.length; i ++) {
            // key.add((BSTMap<K,V>)(hmap[i])).keySet());
            LinkedList<KeyValuePair<K,V>> map =  (LinkedList<KeyValuePair<K,V>>)(hmap[i]);
            for (KeyValuePair<K,V> kv: map) {
                val.add(kv.getValue());
            } 
            
            
        }
        return val;
    }

    //return an ArrayList of all the KeyValuePair
    public ArrayList<KeyValuePair<K,V>> entrySet() {
        ArrayList<KeyValuePair<K,V>> set = new ArrayList<KeyValuePair<K,V>>();
        for (int i = 0; i < hmap.length; i ++) {
            // key.add((BSTMap<K,V>)(hmap[i])).keySet());
            LinkedList<KeyValuePair<K,V>> map =  (LinkedList<KeyValuePair<K,V>>)(hmap[i]);
            for (KeyValuePair<K,V> kv: map) {
                set.add(kv);
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
            hmap[i] = new LinkedList<KeyValuePair<K,V>>();
        }
        this.taken = 0;
        this.collision = 0;
        this.numItems = 0;
    }

    


    public static void main (String[] args) {
        Hashmap<String,Integer> test = new Hashmap<String,Integer>(20);
        System.out.println(test.put("Ant", 1));
        test.put("Bear", 2);
        test.put("Cat", 1);
        test.put("Dog", 2);
        test.put("Eagle", 1);
        test.put("Fly", 2);

        System.out.println(test.containsKey("Dog"));
        System.out.println(test.containsKey("Turtle"));

        System.out.println(test.get("Dog"));

        System.out.println(test.entrySet());
        System.out.println(test.keySet());
        System.out.println(test.values());

        System.out.println(test.getCollision());

        test.put("Duck", 1);
        test.put("Chicken", 2);
        test.put("Dolphin", 1);
        test.put("Hedgehog", 2);
        test.put("Whale", 1);
        System.out.println(test.hashFunction("Bird"));
        test.put("Bird", 2);

        System.out.println(test.entrySet());
    }
}