/** 
 * MyQueue.java
 * Phuong Nguyen Ngoc 
 * CS231
 * Project: Word Frequences
*/

import java.util.Iterator;
import java.util.ArrayList;

public class MyQueue<T> implements Iterable<T> {
    private class Node {
        private Node next;
        private Node prev;
        private T val;

        //a constructor of Node
        public Node(T val) {
            this.next = null;
            this.prev = null;
            this.val = val;
        }

        //return the value of the node
        public T getVal() {
            return this.val;
        }

        //return the next node
        public Node getNext() {
            return this.next;
        }

        //return the prev node
        public Node getPrev() {
            return this.prev;
        }

        //set the next node value to n
        public void setNext(Node n) {
            this.next = n;
        }

        //set the prev node to value n
        public void setPrev(Node p) {
            this.prev = p;
        }
    }
    
    
    
    
    private Node tail;
    private Node head;
    private int size;

    //a constructor
    public MyQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    //a constructor
    public MyQueue(Node head, Node tail, int size) {
        this.head = head;
        this.tail = tail;
        this.size = size;
    }
    

    //add a node to the last of the queue
    public void offer(T val) {
        Node newNode = new Node(val);
        
        if (this.size != 0) {
            newNode.setPrev(this.tail);
            this.tail.setNext(newNode);
            this.tail = newNode; 
            newNode.setNext(null);
        } else {
            this.tail = newNode;
            this.head = newNode;
            newNode.setNext(null);
            newNode.setPrev(null);
        }
        size ++;
    }

    //remove the node from the beginning of the queue
    public T poll() {
        T remove = null;
        if (size == 0) {
            System.out.println("Empty queue, cannot poll! ");
        } else if (size == 1) {
            remove = head.getVal();
            this.tail = null;
            this.head = null;
        } else {
            remove = head.getVal();
            this.head.getNext().setPrev(null);
            this.head = head.getNext();
            
        }
        size --;
        return remove;
    }

    //return the value of the node that is at the beginning of the queue
    public T peek() {
        if (head == null) {
            return null;
        }
        return head.getVal();
    }

    //return the size of the queue
    public int getSize() {
        return this.size;
    }

    public ArrayList<T> toArrayList() {
        ArrayList<T> customer = new ArrayList<T>();
        MyQueue<T> temporary = new MyQueue<T>(this.head, this.tail, this.size);
        
        for (T c: temporary) {
            customer.add(temporary.poll());
        }

        return customer;
        
    }

    //an Iterator to iterate through the queue
    private class LLIterator implements Iterator<T> {
        private Node current;
        //a constructor
        public LLIterator(Node head) {
            current = head;
        }

        //check whether the queue has next or not
        public boolean hasNext() {
            
            if (current != null) {
                return true;
            }
            return false;
        }

        // returns the next item in the list
        public T next() {
            if (this.hasNext() == true) {
                T n = current.getVal();
                current = current.getNext();
                return n;
            } else {
                return null;
            }
            
        }

        public void remove() {

        }
    }

    //Return a new LLIterator pointing to the head of the list
    public Iterator<T> iterator() {
        return new LLIterator(this.head);
    }

    public static void main(String[] args) {
        MyQueue<Integer> test = new MyQueue<Integer>();
        test.offer(1);
        test.offer(2);
        test.offer(3);
        test.offer(4);
        for (Integer i: test) {
            System.out.println(i);
        }
        System.out.println(test.getSize());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.getSize());
        System.out.println(test.toArrayList());
        System.out.println(test.getSize());

    }
}