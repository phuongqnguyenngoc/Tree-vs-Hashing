/** 
 * LinkedList.java
 * Phuong Nguyen Ngoc 
 * CS231
 * Project: Word Frequences
*/

import java.util.Iterator;    // defines the Iterator interface
import java.util.ArrayList;   
import java.util.Collections; // contains a shuffle function
import java.util.Random;

//implements Iterable interface to enables foreach loops
public class LinkedList<T> implements Iterable<T> {
    private class Node {
        private Node next;
        private T thing;

        //constructor initializes the node
        public Node(T item) {
            next = null;
            thing = item;
        }

        //returns the value of the container field.
        public T getThing() {
            return this.thing;
        }

        //sets next to the given node.
        public void setNext(Node n) {
            next = n;
        }

        //returns the next field.
        public Node getNext() {
            return next;
        }
    }

    Node head;
    int size;

    //constructor that initializes the fields so it is an empty list.
    public LinkedList() {
        head = null;
        size = 0;
    }

    //empties the list
    public void clear() {
        head = null;
        size = 0;
    }

    //returns the size of the list.
    public int size() {
        return size;
    }

    //inserts the item at the beginning of the list.
    public void addFirst (T item) {
        Node newNode = new Node(item);
        newNode.setNext(this.head);
        head = newNode;
        size ++;
    }

    //appends the item at the end of the list.
    public void addLast(T item) {
        Node p = head;
        Node newNode = new Node(item);
        if (head != null) {
            while(p.getNext() != null) {
                p = p.getNext();
            }
            p.setNext(newNode);
            newNode.setNext(null);
            size ++;
        } else {
            head = newNode;
            head.setNext(null);
            size ++;
        }
        
    }

    //inserts the item at the specified poistion in the list.
    public void add(int index, T item) {
        Node p = head;
        Node newNode = new Node(item);
        if (index == 0) {
            this.addFirst(item);
        } else { 
            for (int i = 1; i < index; i ++) {
                p = p.getNext();
            }
            newNode.setNext(p.getNext());
            p.setNext(newNode);
            size ++;
        }
        
        
        
    }

    //removes the item at the specified position in the list.
    public T remove(int index) {
        Node p = head;
        T val = null;
    
        if (index == 0) {
            val = head.getThing();
            head.setNext(head.getNext());        
        } else if (head == null) {
            System.out.println("linked list empty, cannot remove! ");
        } else {
            for (int i = 1; i < index; i ++) {
                p = p.getNext();
            }
    
            val = p.getNext().getThing();
            p.setNext(p.getNext().getNext());
            size --; 
        }
        return val;   
        
    }

    //subclass that handles traversing the list
    private class LLIterator implements Iterator<T> {
        private Node current;
        //the constructor for the LLIterator given the head of a list.
        public LLIterator(Node head) {
            current = head;
        }

        //check if there are still values to traverse 
        public boolean hasNext() {
            
            if (current != null) {
                return true;
            }
            return false;
        }

        //returns the next item in the list
        public T next() {
            if (this.hasNext() == true) {
                T n = current.getThing();
                current = current.getNext();
                return n;
            } else {
                return null;
            }
            
        }

        public void remove() {

        }
    }

    // Return a new LLIterator pointing to the head of the list
    public Iterator<T> iterator() {
        return new LLIterator(this.head);
    }

    //returns an ArrayList of the list contents in order.
    public ArrayList<T> toArrayList() {
        Node p = head;
        ArrayList<T> theArray = new ArrayList<T>();
        while (p != null) {
            T val = p.getThing();
            theArray.add(val);
            p = p.getNext();
        }
        return theArray;
    }

    //returns an ArrayList of the list contents in shuffled order.
    public ArrayList<T> toShuffledList() {
        Node p = head;

        Random rand = new Random();
        ArrayList<T> theShuffled = new ArrayList<T>();
        ArrayList<T> another = this.toArrayList();


        for (int i = 0; i < size; i ++) {
            int index = rand.nextInt(size-i);
            theShuffled.add(another.remove(index));
        }
        
        return theShuffled;
        
    }

}
    