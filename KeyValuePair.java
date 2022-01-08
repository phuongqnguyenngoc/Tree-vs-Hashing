/** 
 * KeyValuePair.java
 * Phuong Nguyen Ngoc 
 * CS231
 * Project: Word Frequences
*/

public class KeyValuePair<K,V> implements Comparable<KeyValuePair<K,V>> {
    private K key;
    private V value;

    // the constructor initializing the key and value fields.
    public KeyValuePair(K k, V v) {
        this.key = k;
        this.value = v;
    }

    //returns the key.
    public K getKey() {
        return this.key;
    }


    //returns the value.
    public V getValue() {
        return this.value;
    }

    //sets the value.
    public void setValue(V v) {
        this.value = v;
    }

    //returns a String containing both the key and value.
    public String toString() {
        String statement = "";
        statement += this.key + "  " + this.value;
        return statement;
    }

    public int compareTo(KeyValuePair<K,V> kv) {
        if (((Integer)(this.value)) < ((Integer)(kv.getValue()))) {
        return -1;
        } 
        else if (((Integer)(this.value)) > ((Integer)(kv.getValue()))) {
        return 1;
        }
        else {
        return 0;
        }
    }



    public static void main(String [] args) {
        KeyValuePair<String,Integer> kv = new KeyValuePair<String,Integer> ("that", 1);
        System.out.println(kv.getKey());
        System.out.println(kv.getValue());
        kv.setValue(3);
        System.out.println(kv.compareTo(new KeyValuePair<String,Integer>("this", 2)));
        System.out.println(kv.getValue());
    }
}