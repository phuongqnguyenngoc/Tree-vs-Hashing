/** 
 * BSTMap.java
 * Phuong Nguyen Ngoc 
 * CS231
 * Project: Comparing Data Structures
*/

import java.util.Comparator;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

import java.util.ArrayList;

import java.util.*;

public class BSTMap<K,V> implements MapSet<K,V> {
    private TNode root;
    private Comparator<K> comp;
    private int size;

    //constructor initializes the map 
    public BSTMap (Comparator<K> comp) {
        this.comp = comp;
        this.size = 0;
        this.root = null;
    }

    // adds or updates a key-value pair. Check special case then call the put method on the root Node
    public V put(K key, V value) {
        
        if (root == null) {
            TNode newNode = new TNode(key, value);
            this.root = newNode;
            this.size ++;
            return null;
        }
        else {
            
            return root.put(key, value, this.comp);
            
        } 
        
        
    }

    //get the value V associated with a key. Check special case then call the get method on the root
    public V get( K key) {
        if (root != null) {
            return root.get(key, this.comp);
        }
        return null;
    }

    //returns true if the map already contains a node with the specified key. Check special case then call the identical method of the TNode class
    public boolean containsKey (K key) {
        if (root == null) {
            return false;
        } else {
            return root.containsKey(key, this.comp);
        }
    }

    //returns an ArrayList that contains all of the keys in the map. Check special case then call the identical method of the TNode class
    public ArrayList<K> keySet() {
        
        if (root == null) {
            return null;
        } else {
            ArrayList<K> container = new ArrayList<K>();
            root.keySet(container);
            return container;
        }

    } 

    //returns an ArrayList that contains all of the values in the map. Check special case then call the identical method of the TNode class
    public ArrayList<V> values() {
        if (root == null) {
            return null;
        } else {
            ArrayList<V> valueContainer = new ArrayList<V>();
            root.values(valueContainer);
            return valueContainer;
        }
    }

    //returns an ArrayList that contains all of the KeyValuePair in the map. Check special case then call the identical method of the TNode class
    public ArrayList<KeyValuePair<K,V>> entrySet() {
        if (root == null) {
            return null;
        } else {
            ArrayList<KeyValuePair<K,V>> set = new ArrayList<KeyValuePair<K,V>>();
            root.entrySet(set);
            return set;
        }
    }

    //return an ArrayList that contains all KeyValuePair in In-Order traversal. Check special case then call the identical method of the TNode class 
    public ArrayList<KeyValuePair<K,V>> inOrder() {
        if (root == null) {
            return null;
        } else {
            ArrayList<KeyValuePair<K,V>> set = new ArrayList<KeyValuePair<K,V>>();
            root.inOrder(set);
            return set;
        }
    }

    //return the map's size
    public int size() {
        return size;
    }

    //return an ArrayList of KeyValuePair of the middle points. 
    //this 1st method initiate the 2 arrayLists, then call the 2nd method to modify them
    public ArrayList<KeyValuePair<K,V>> addNext() {
        ArrayList<KeyValuePair<K,V>> balanced = new ArrayList<KeyValuePair<K,V>>();
        ArrayList<KeyValuePair<K,V>> inOrd = this.inOrder(); 
        int max = inOrd.size() - 1; //get the index
        this.addNext(inOrd, 0, max, balanced);
        return balanced;
    }

    //modify the balanced arrayList using the inOrd arrayList
    public void addNext(ArrayList<KeyValuePair<K,V>> inOrd, int min, int max, ArrayList<KeyValuePair<K, V>> balanced ) {
        
        if (max < min) {
            return;
        }
        int index = (max + min)/2;

        balanced.add(inOrd.get(index));

        addNext(inOrd, min, index -1, balanced);
        addNext(inOrd, index + 1, max, balanced);    
    } 

    //initiate the inOrder arrayList and pass it down to the second balancedTree method
    public void balancedTree() {
        
        ArrayList<KeyValuePair<K,V>> balanced = this.inOrder();

        clear();
        this.balancedTree(balanced, 0, balanced.size() - 1);
    }

    //put the Nodes in the map that ensures balanced tree
    public void balancedTree(ArrayList<KeyValuePair<K,V>> balanced, int min, int max) {
        
        if (max < min) {
            return;
        }
        int index = (max + min)/2;
        
        put(balanced.get(index).getKey(),balanced.get(index).getValue());

        balancedTree(balanced, min, index -1);
        balancedTree(balanced, index + 1, max);  
    }
    
   
    //toString method prints out Nodes layer by layer from top to bottom. Check special case then call the byLayer method to modify the String

    public String toString() {
        String layer = "";
        if (root != null) {
            return byLayer(root);
        }
        return layer;
    }

    // public String toString() {
    //     String layer = "";
    //     if (root != null) {
    //         return root.preOrder("root", 0);
    //     }
    //     return layer;
    // }

    //return a String that contains Key and Value of the Nodes which are presented on the same line if of the same level
   public String byLayer(TNode node) {        
        String structure = "";
        MyQueue<TNode> queue = new MyQueue<TNode>();
        queue.offer(node);
        queue.offer(null);
        while (queue.getSize() > 1) {
        // size has to be greater than 1, or else it will keep poll and offer null when it reaches the end of the tree    
            TNode parent = queue.poll();           
            if (parent != null) {
                structure += parent.kv + "\t";
                if (parent.left != null) {
                    queue.offer(parent.left);
                } 
                if (parent.right != null) {
                    queue.offer(parent.right);
                } 
            } else {
                structure += "\n" ;
                queue.offer(null);
            }  
        }

        return structure;

    }

   
    //print out n KeysValuePair with the highest Value
    public void getMax(int n) {
        ArrayList<Integer> val = (ArrayList<Integer>) this.values(); //casting
        ArrayList<KeyValuePair<K,V>> kv = this.entrySet();
        for (int i = 0; i < n; i ++) {
            int index = val.indexOf(Collections.max(val)) ;        
            System.out.println(kv.remove(index));
            val.remove(index);
        }       
    }

    
   //clear the map
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    // public int depth(TNode root) {
    //     if (root == null) {
    //         return 1 + Math.max(depth(root.left),depth(root.right));
    //     } else return 0;
    // }

    public int depth(TNode node) {
        if (node == null) {
            return 0;
        } else {
            int depthLeft = depth(node.getLeft());
            int depthRight = depth(node.getRight());
            if (depthLeft > depthRight) {
                return (depthLeft + 1);
            } else {
                return (depthRight + 1);
            }
            // return 1 + Math.max(depth(node.getLeft()), depth(node.getRight()));
        }
    }

    public int depth() {
        return (depth(root) -1);
    }



    
    //subclass TNode
    private class TNode {
        private KeyValuePair<K,V> kv;
        private TNode left;
        private TNode right;
                
        //constructor initiate a TNode
        public TNode(K k, V v) {
            this.kv = new KeyValuePair(k,v);
            this.left = null;
            this.right = null;
            
        }

        public TNode getLeft() {
            return this.left;
        }

        public TNode getRight() {
            return this.right;
        }
        //put the Key into the map based on alphabetical order
        public V put ( K key, V value, Comparator<K> comp) {
            int c = comp.compare(key, this.kv.getKey());
            if (c == 0) {
                V v = this.kv.getValue();
                this.kv.setValue(value);
                return v;
            } else if (c < 0) {
                if (this.left == null) {
                    TNode newNode = new TNode(key, value);
                    this.left = newNode;
                    size ++;
                    return null;
                }
                else {
                    return this.left.put(key, value, comp);
                }
            } else {
                if (this.right == null) {
                    TNode newNode = new TNode(key, value);
                    this.right = newNode;
                    size ++;
                    return null;
                } else {
                    return this.right.put(key, value, comp);
                }
            }   
        }

        //return true if the Key is available in the map
        public boolean containsKey(K key, Comparator<K> comp) {
            int c = comp.compare(key, this.kv.getKey());
            if (c == 0) {
                return true;
            } else if (c < 0) {
                if (this.left == null) {
                    return false;
                } else {
                    return this.left.containsKey(key, comp);                   
                }
            } else {
                if (this.right == null) {
                    return false;
                } else { 
                    return this.right.containsKey(key, comp);                   
                }
            }
        }

        //return the value of the key or null
        public V get(K key, Comparator<K> comp) {
            int c = comp.compare(key, this.kv.getKey());
            if (c == 0) {
                V v = this.kv.getValue();
                return v;
            } 
            else if (c < 0) {
                if (this.left == null) {
                    return null;
                } else {
                    return this.left.get(key, comp);
                }
            } else {
                if (this.right == null) {
                    return null;
                } else {
                    return this.right.get(key, comp);
                }
            }
            
        }

        
        //return an ArrayList of Keys in PreOrder traversal
        public void keySet(ArrayList<K> something) {
            something.add(this.kv.getKey());

            if (this.left != null) {
                this.left.keySet(something);
            }

            if (this.right != null) {
                this.right.keySet(something);
            }    
        }

        //return an ArrayList of Values in PreOrder traversal
        public void values(ArrayList<V> something) {
            something.add(this.kv.getValue());

            if (this.left != null) {
                this.left.values(something);
            }

            if (this.right != null) {
                this.right.values(something);
            }
        }

        //return an ArrayList of KeyValuePairs in PreOrder traversal
        public void entrySet(ArrayList<KeyValuePair<K,V>> something) {
            something.add(this.kv);
            
            if (this.left != null) {
                this.left.entrySet(something);
            }
            
            if (this.right != null) {
                this.right.entrySet(something);
            }
        }

        //return an ArrayList of KeyValuePairs in InOrder traversal
        public void inOrder(ArrayList<KeyValuePair<K,V>> something) {
            if (this.left != null) {
                this.left.inOrder(something);
            }

            something.add(this.kv);
            
            if (this.right != null) {
                this.right.inOrder(something); 
                
            }

           
        }

        //return a String that represents the structure of the map, including the position and level of each TNode
        public String preOrder(String left_right, int level) {
            String left = "";
            String right = "";
            String structure = left_right;
                       
            for (int i = 0; i < level + 1; i ++) {
                structure += "  ";
            }   
            structure += kv + "\n";
            if (this.left != null) {
                left = this.left.preOrder("left ", level + 1);
            } 
            if (this.right != null) {
                right = this.right.preOrder("right", level + 1);
            }
            return structure + left + right;
        }

        

      
    }

    

    public static void main( String[] argv ) {
        // create a BSTMap
        BSTMap<String, Integer> bst = new BSTMap<String, Integer>( new StringAscending() );

        
        bst.put( "d", 20 );
       
        
        bst.put( "b", 10 );
        bst.put( "c", 11 );
        bst.put( "a", 80 );
        bst.put( "e", 5 );
        bst.put( "f", 6 );

        bst.put( "p", 14 );
        bst.put( "q", 88 );
        bst.put( "r", 70 );
        bst.put( "s", 30 );
        bst.put( "t", 9 );

        bst.put( "o", 24 );
        bst.put( "l", 90 );
        bst.put( "m", 100 );
        bst.put( "n", 3 );
        bst.put( "k", 9 );

        System.out.println( bst.get( "eleven" ) );
        System.out.println( bst.containsKey("six"));
        System.out.println( bst.containsKey("seven"));
        System.out.println( bst.keySet());
        System.out.println( bst.get( "five"));
        System.out.println( bst.inOrder());
        System.out.println(bst.size);
        System.out.println( bst.entrySet());
        System.out.println(bst);
        System.out.println("addnext" + bst.addNext());
        System.out.println("get max "); bst.getMax(5);
        System.out.println(bst.entrySet());
        bst.balancedTree();
        System.out.println(bst);
        System.out.println(bst.depth());
       
    }



}

