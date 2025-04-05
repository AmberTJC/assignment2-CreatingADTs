/**
 * @author amber c, nawal m, aiana s
 */


package implementations;

import java.util.NoSuchElementException;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;


/**
 * Provides standard queue operations
 * @param <E> elements in the queue
 */
public class MyQueue<E> implements QueueADT<E>
{
	
	private Node<E> front;
	private Node<E> back;
	private int size = 0;
	private int capacity;
	
	private static class Node<E>
	{
		E data;
		Node<E> next;
		Node(E data)
		{
			this.data = data;
			this.next = null;
		}
	}
	
	/**
	 * A constructor for MyQueue that tracks the queues capacity
	 * @param capacity how many elements the queue can hold
	 */
	public MyQueue(int capacity) {
	    if (capacity <= 0) //>=
	    throw new IllegalArgumentException("Queue capacity must be greater than zero");
		this.capacity = capacity;
	}
/**
 * A constructor for MyQueue
 */
	public MyQueue()
	{ this.capacity = Integer.MAX_VALUE;
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
		if(toAdd == null) 
		{
			throw new NullPointerException("null values cannot be added to the queue");
		}
		
		if (size == capacity) 
		{
			throw new IllegalStateException("Queue is full");
		}
		
		Node<E> newNode = new Node<>(toAdd);
		
		if(back == null) 
		{
			front = back = newNode;
		}
		else 
		{
			back.next = newNode;
			back = newNode;
		}
		size++;
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
		if(front == null) 
		{
			throw new EmptyQueueException("the queue is empty");
		}
		
		E data = front.data;
		front = front.next;
		if(front == null) 
		{
			back = null;
		}
		size--;
		return data;
	}

	/**
	 * shows front value without removing it from the queue
	 * Precondition: value cannot be null, queue cannot be empty
	 * Postcondition: value a front is shown but not removed
	 */
	@Override
	public E peek() throws EmptyQueueException
	{
		if (front == null) 
		{
			throw new EmptyQueueException("the queue is empty");
		}
		return front.data;
	}

	/**
	 * removes all values from queue
	 * Precondition: queue exists
	 * Postcondition: queue is empty
	 */
	@Override
	public void dequeueAll()
	{
		front = null;
		back = null;
		size = 0;
		
	}

	/**
	 * Checks whether the queue is empty, returns true if it is false otherwise
	 * Precondition: Queue exists
	 * Postcondition: returns true if empty
	 */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
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
		if (toFind == null) 
		{
			throw new NullPointerException("Cannot have null elements");
		}
		
		Node<E> current = front;
		while (current != null) 
		{
			if(toFind.equals(current.data)) 
			{
				return true;
			}
			current = current.next;
		}
		return false;
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
		Node<E> current = front;
		int position = 1;
		
		while(current != null) 
		{
			if((toFind == null && current.data == null) || (toFind != null && toFind.equals(current.data))) 
			{
				return position;
			}
			current = current.next;
			position++;
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
		return new QueueIterator();
	}
	
	/**
	 * Custom Iterator for the MyQueue class
	 * Precondition: queue exists
	 * Postcondition: queue iterated through
	 */
	private class QueueIterator implements Iterator<E>
	{
		private Node<E> current = front;

		/**
		 * Determines if any value in the queue has a next value
		 * Precondition: queue exists
		 * Postconditions: return true if value has a next value false otherwise
		 */
		@Override
		public boolean hasNext()
		{
			return current != null;
		}

		/**
		 * determines what the next value is in the queue
		 * Precondition: queue exists
		 * Postcondition: returns next value
		 * @exception NoSuchElementException throws when there is no element next
		 */
		@Override
		public E next() throws NoSuchElementException
		{
			if(!hasNext()) 
			{
				throw new NoSuchElementException();
			}
			
			E data = current.data;
			current = current.next;
			
			return data;
		}
		
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
		Object[] result = new Object[size];
		Node<E> current = front;
		int index = 0;
		
		while (current != null) 
		{
			result[index++] = current.data;
			current = current.next;
		}
		return result;
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
		if(holder == null) 
		{
			throw new NullPointerException("The array cannot be null");
		}
		
		int count = size;
		if (holder.length < count) 
		{
			holder = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), count);
		}
		
		Node<E> current = front;
		int index = 0;
		
		while (current != null) 
		{
			holder[index++] = current.data;
			current = current.next;
		}
		if (holder.length > count) 
		{
			holder[count] = null;
		}
		return holder;
	}

	/**
	 * returns true if queue is full
	 * Precondition: queue exists
	 * Postcondition: returns true if queue is full
	 */
	@Override
	public boolean isFull()
	{
		return size == capacity;
	}

	/**
	 * shows how many elements are in the queue
	 * Preconditions: queue exists
	 * Postconditions: return the number of elements in the queue
	 */
	@Override
	public int size()
	{
		return size;
	}

}
