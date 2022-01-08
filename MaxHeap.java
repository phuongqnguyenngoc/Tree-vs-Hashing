
public class MaxHeap<K,V> {

  private int numItems;
  private Object[] heap;

  //constructor that initializes the array with the given capacity
  public MaxHeap(int capacity) {
    numItems = 0;
    heap = new Object[capacity];
  }

  
  // Add KeyValuePair 
  public void add(KeyValuePair<K,V> kv) {
    // expand heap if it's going to be half full
    ensureCapacity();

    //add the KeyValuePair to the next free space in the array
    heap[numItems] = kv;
    numItems++;
    //percolate up to find the suitable position to ensure Heap structure
    percolateUp();
  }

  //double the size if the array is half full
  public void ensureCapacity() {
    if (numItems < heap.length/2) {
      return;
    }
    Object[] newHeap = new Object[2*heap.length];
    for (int i = 0; i < heap.length; i++) {
      newHeap[i] = heap[i];
    }   
    heap = newHeap;

  }

  //restructure the array to ensure the Heap structure
  private void percolateUp() {
    //the index for the last element in the array
    int outOfPlaceInd = numItems - 1;

    // swap with parents until we reach the root or the parent is larger than the current value.
    while (outOfPlaceInd > 0)
    {
      int parentInd = (outOfPlaceInd - 1)/2;
      int compare = ((KeyValuePair<K,V>)(heap[outOfPlaceInd])).compareTo(((KeyValuePair<K,V>)(heap[parentInd])));
  
      //if the current value is larger than its parent, swap
      if (compare > 0) {
        swap(outOfPlaceInd, parentInd);
        // update the curr index to the parent's
        outOfPlaceInd = parentInd;
      }
      //else, the current element is in the right position, return
      else {
        return;
      }
    }
  }
  
  //swap the KeyValuePairs of 2 given position
  private void swap(int i, int j) {
    KeyValuePair<K,V> copy = (KeyValuePair<K,V>)(heap[i]);
    heap[i] = heap[j];
    heap[j] = copy;
  }



  // Remove and return the KeyValuePair with the highest value
  public KeyValuePair<K,V> remove() {
    if (numItems == 0) {
      throw new IllegalStateException("Trying to remove from an empty PQ!");
    }
    
    KeyValuePair<K,V> p = (KeyValuePair<K,V>)(heap[0]);
    // replace the root with the last element on the array
    numItems--;
    heap[0] = heap[numItems];
    
    //percolate down to ensure the Heap structure
    percolateDown();
    
    return p;
  }

  //restructure the array to ensure the Heap structure
  private void percolateDown()
  {    
    int outOfPlaceInd = 0; // parent
    int leftInd = 2*outOfPlaceInd + 1; // left child
    int rightInd = 2*outOfPlaceInd + 2; // right child

    //swap until the outOfPlaceInd value is larger than its larger child's value
    while (leftInd <= numItems - 1)
    {
      int biggerChildInd = leftInd;
      if ((rightInd < numItems) && (((KeyValuePair<K,V>)(heap[rightInd])).compareTo((KeyValuePair<K,V>)(heap[leftInd])) > 0)) {
        biggerChildInd = rightInd;
	  }
      int compare = ((KeyValuePair<K,V>)(heap[outOfPlaceInd])).compareTo((KeyValuePair<K,V>)(heap[biggerChildInd]));
      //if the current value is smaller than its larger child's value, swap and update the indices
      if (compare < 0)
      {
        swap(outOfPlaceInd, biggerChildInd);

        outOfPlaceInd = biggerChildInd;
        leftInd = 2*outOfPlaceInd + 1;
        rightInd = 2*outOfPlaceInd + 2;
      }
      //else, the current element is in the right place
      else
      {
        return;
      }
    }
  }

  //return the number of items on the array
  public int size() {return numItems;}
  
  //return true if the array has no items
  public boolean isEmpty() {return numItems == 0;}

  //toString method that print out the heap layer by layer
  public String toString() {
    String returnStr = "";

    int level = 0;
    int leftn = numItems;
    
    while (leftn > 0) {
      int count = 1;
      double pow = Math.pow(2.0, level);
      while (count <= pow )  {
        if (leftn == 0) 
          break;
        returnStr += heap[numItems-leftn] + " ";
        count++;
        leftn--;
      }

      if (leftn != 0) 
      returnStr += "\n";
      level++;
    }
    returnStr += "\n----------------\n";

    return returnStr;
  }

  
  public static void main(String[] args) {
  MaxHeap<String,Integer> priorityQueue = new MaxHeap<String,Integer>(4);
    priorityQueue.add(new KeyValuePair<String,Integer>("Scott", 6));
    System.out.println(priorityQueue.toString());
    priorityQueue.add(new KeyValuePair<String,Integer>("Bob", 1));
    System.out.println(priorityQueue.toString());
    priorityQueue.add(new KeyValuePair<String,Integer>("Jone", 3));
    System.out.println(priorityQueue.toString());
    priorityQueue.add(new KeyValuePair<String,Integer>("Susan", 4));
    System.out.println(priorityQueue.toString());
    priorityQueue.add(new KeyValuePair<String,Integer>("Nate", 7));
    System.out.println(priorityQueue.toString());
    priorityQueue.add(new KeyValuePair<String,Integer>("Eddy", 2));
    System.out.println(priorityQueue.toString());
    priorityQueue.add(new KeyValuePair<String,Integer>("Doug", 0));
    System.out.println(priorityQueue.toString());

    System.out.println("Size should be 7: " + priorityQueue.size());
    System.out.println(priorityQueue.toString());

    for (int i = 0; i < priorityQueue.numItems; i ++) {
        System.out.println(priorityQueue.heap[i]);
    }
    
    System.out.println("Removing Nate: " + priorityQueue.remove());
    System.out.println(priorityQueue.toString());
    System.out.println("Removing Scot: " + priorityQueue.remove());
    System.out.println(priorityQueue.toString());
    System.out.println("Removing Susan: " + priorityQueue.remove());
    System.out.println(priorityQueue.toString());
    System.out.println("Removing Jone: " + priorityQueue.remove());
    System.out.println(priorityQueue.toString());
    System.out.println("Removing Eddy: " + priorityQueue.remove());
    System.out.println(priorityQueue.toString());
    System.out.println("Removing Bob: " + priorityQueue.remove());
    System.out.println(priorityQueue.toString());
    System.out.println("Removing Dough: " + priorityQueue.remove());
    System.out.println("Size should be 0: " + priorityQueue.size());
  }
  
}