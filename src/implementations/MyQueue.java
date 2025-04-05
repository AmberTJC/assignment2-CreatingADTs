/**
 * @author amber c, nawal m, aiana s
 */


package implementations;

import java.util.NoSuchElementException;
import implementations.MyDLL;
import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;
import utilities.ListADT;


/**
 * Provides standard queue operations
 * @param <E> elements in the queue
 */
public class MyQueue<E> implements QueueADT<E>
{
	
	private final ListADT<E> list;
	private final int capacity;
	
	/**
	 * A constructor for MyQueue that tracks the queues capacity
	
	 */
	public MyQueue() {
	    
		this.capacity = Integer.MAX_VALUE;
		this.list = new MyDLL<>();
	}
/**
 * A constructor for MyQueue
 * @param capacity how many elements the queue can hold
 */
	public MyQueue(int capacity)
	{ 
		if(capacity <= 0) 
		{
			throw new IllegalArgumentException("Capacity must be greater than zero");
		}
		this.capacity = capacity;
		this.list = new MyDLL<>();
	}

	/**
	 * adds an element to the back of the queue
	 * Precondition: value cannot be null
	 * Postcondition: element added to back of the queue
	 * @param toAdd the element to be added
	 * @exception throws NullPointerException when value is null, IllegalStateException when queue is full
	 */
	@Override
	public void enqueue(E toAdd) throws NullPointerException
	{
		if (toAdd == null) 
		{
			throw new NullPointerException("Cannot enqueue null.");
		}
        if (isFull()) 
        {
        	throw new IllegalStateException("Queue is full.");
        }
        list.add(toAdd);
	}

	/**
	 * removes an element from the front of the queue
	 * Precondition: queue cannot be empty
	 * Postcondition: element is removed from the queue
	 * @exception throws EmptyQueueException when the queue is empty
	 */
	@Override
	public E dequeue() throws EmptyQueueException
	{
		 if (isEmpty()) 
		 {
			 throw new EmptyQueueException("Queue is empty.");
		 }
	        return list.remove(0);
	}

	/**
	 * shows front value without removing it from the queue
	 * Precondition: value cannot be null, queue cannot be empty
	 * Postcondition: value a front is shown but not removed
	 */
	@Override
	public E peek() throws EmptyQueueException
	{
		if (isEmpty()) 
		{
			throw new EmptyQueueException("Queue is empty.");
		}
        return list.get(0);
	}

	/**
	 * removes all values from queue
	 * Precondition: queue exists
	 * Postcondition: queue is empty
	 */
	@Override
	public void dequeueAll()
	{
		list.clear();
		
	}

	/**
	 * Checks whether the queue is empty, returns true if it is false otherwise
	 * Precondition: Queue exists
	 * Postcondition: returns true if empty
	 */
	@Override
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	/**
	 * checks if a given value exists in the queue
	 * Preconditions: queue exists
	 * Postconditions: return true if found false otherwise
	 * @param toFind the value to be searched
	 * @exception throws NullPointerException if value is null
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		return list.contains(toFind);
	}
	/**
	 *searches for a given value within the queue
	 * Preconditions: queue exists
	 * Postconditions: position of the searched value
	 * @param toFind the value to be searched
	 */
	@Override
	public int search(E toFind)
	{
		for (int i = 0; i < list.size(); i++) 
		{
            E element = list.get(i);
            if ((toFind == null && element == null) || (toFind != null && toFind.equals(element))) 
            {
                return i + 1;
            }
		}
		return -1;
	}

/**
 * Custom Iterator for the MyQueue class
 * Precondition: queue exists
 * Postcondition: queue iterated through
 */
	@Override
	public Iterator<E> iterator()
	{
		return list.iterator();
	}
	
	

	/**
	 * compares two queues
	 * Preconditions: Queues must contain equal items appearing in the same order
	 * Postconditions: reruns true if both queues are equal, false otherwise
	 * @param that the queue to be compared
	 */
	@Override
	public boolean equals(QueueADT<E> that)
	{
		if (that == null)
		{
			return false;
		}
		Iterator<E> thisIter = this.iterator();
		Iterator<E> thatIter = that.iterator();
		
		while(thisIter.hasNext() && thatIter.hasNext()) 
		{
			E a = thisIter.next();
			E b = thatIter.next();
			
			if (a == null && b != null) return false;
			if ( a != null && !a.equals(b))return false;  
			if (a == null && b == null) continue;
		}
		return !thisIter.hasNext() && !thatIter.hasNext();
	}

	/**
	 * returns an array of all items in the queue in sequence
	 * Preconditions: Queue must exist
	 * Postconditions: array is returned
	 */
	@Override
	public Object[] toArray()
	{
		return list.toArray();
	}

	/**
	 * returns an array of all items in the queue in sequence
	 * Preconditions: Queue must exist
	 * Postconditions: array is returned
	 * @param holder the array to be returned
	 * @exception throws NullPointerException when the value is null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray(E[] holder) throws NullPointerException
	{
		 return list.toArray(holder);
	}

	/**
	 * returns true if queue is full
	 * Precondition: queue exists
	 * Postcondition: returns true if queue is full
	 */
	@Override
	public boolean isFull()
	{
		return list.size() == capacity;
	}

	/**
	 * shows how many elements are in the queue
	 * Preconditions: queue exists
	 * Postconditions: return the number of elements in the queue
	 */
	@Override
	public int size()
	{
		 return list.size();
	}

}
