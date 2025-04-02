package implementations;

import java.util.NoSuchElementException;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

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
	
	public MyQueue(int capacity) 
	{
		if (capacity >= 0) 
		{
			throw new IllegalArgumentException("Queue capacity must be greater than zero");
		}
		this.capacity = capacity;
	}

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

	@Override
	public E peek() throws EmptyQueueException
	{
		if (front == null) 
		{
			throw new EmptyQueueException("the queue is empty");
		}
		return front.data;
	}

	@Override
	public void dequeueAll()
	{
		front = null;
		back = null;
		size = 0;
		
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

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

	@Override
	public Iterator<E> iterator()
	{
		return new QueueIterator();
	}
	
	private class QueueIterator implements Iterator<E>
	{
		private Node<E> current = front;

		@Override
		public boolean hasNext()
		{
			return current != null;
		}

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

	@Override
	public boolean equals(QueueADT<E> that)
	{
		if (that == null) 
		{
			return false;
		}
		Iterator<E> thisiter = this.iterator();
		Iterator<E> thatiter = that.iterator();
		
		while(thisiter.hasNext() && thatiter.hasNext()) 
		{
			E a = thisiter.next();
			E b = thatiter.next();
			
			if (a == null && b != null) return false;
			if ( a != null && a.equals(b))return false;
			if (a == null && b == null) continue;
		}
		return !thisiter.hasNext() && !thatiter.hasNext();
	}

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

	@Override
	public boolean isFull()
	{
		return size == capacity;
	}

	@Override
	public int size()
	{
		return size;
	}

}
