package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.StackADT;

public class MyStack<E> implements StackADT<E>
{
	private int capacity;
	private int size = 0;
	private Node<E> top;
	
	public MyStack(int capacity) 
	{
		if (capacity <= 0 ) 
		{
			throw new IllegalArgumentException("Capacity must be greater than zero");
		}
		this.capacity = capacity;
	}
	
	public MyStack()
	{
		this.capacity = Integer.MAX_VALUE; 
	}

	private static class Node<E>
	{
		E data;
		Node<E> next;
		Node(E data)
		{
			this.data = data;
		}
	}

	@Override
	public void push(E toAdd) throws NullPointerException
	{
		if (toAdd == null) 
		{
			throw new NullPointerException("Element added to stack cannot be null");
		}
		if(size == capacity) 
		{
			throw new IllegalStateException("Stack is full");
		}
		
		Node<E> newNode = new Node<>(toAdd);
		newNode.next = top;
		top = newNode;
		size++;
		
	}

	@Override
	public E pop() throws EmptyStackException
	{
		if(top == null) 
		{
			throw new EmptyStackException();
		}
		E data = top.data;
		top = top.next;
		size--;
		return data;
	}

	@Override
	public E peek() throws EmptyStackException
	{
		if(top == null) 
		{
			throw new EmptyStackException();
		}
		return top.data;
	}

	@Override
	public void clear()
	{
		top = null;
		size = 0;
		
	}

	@Override
	public boolean isEmpty()
	{
		return top == null;
	}

	@Override
	public Object[] toArray()
	{
		int count = 0;
		
		Node<E> current = top;
		while (current != null) 
		{
			count++;
			current = current.next;
		}
		
		Object[] myArray = new Object[count];
		current = top;
		for (int i = 0; i < count; i++) 
		{
			myArray[i] = current.data;
			current = current.next;
		}
		return myArray;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray(E[] holder) throws NullPointerException
	{
		if (holder == null) 
		{
			throw new NullPointerException("Input cannot be null");
		}
		
		int count = 0;
		
		Node<E> current = top;
		while (current != null) 
		{
			count++;
			current = current.next;
		}
		if (holder.length < count) 
		{
			holder = (E[])java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), count);
		}
		
		current = top;
		for( int i = 0; i < count; i++) 
		{
			holder[i] = current.data;
			current = current.next;
		}
		
		if (holder.length > count) 
		{
			holder[count] = null;
		}
		return holder;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		if (toFind == null) 
		{
			throw new NullPointerException("Cannot find null value");
		}
		
		Node<E> current = top;
		while (current != null) 
		{
			if (toFind.equals(current.data)) 
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
		Node<E> current = top;
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
		return new StackIterator();
	}
	private class StackIterator implements Iterator<E>
	{
		private Node<E> current = top;

		@Override
		public boolean hasNext()
		{
			return current != null;
		}

		@Override
		public E next() throws NoSuchElementException
		{
			if (!hasNext()) 
			{
				throw new NoSuchElementException();
			}
			
			E data = current.data;
			current = current.next;
			return data;
		}
		
	}

	@Override
	public boolean equals(StackADT<E> that)
	{
		if(that == null) 
		{
			return false;
		}
		
		Iterator<E> thisIter = this.iterator();
		Iterator<E> thatIter = that.iterator();
		
		while (thisIter.hasNext()&& thatIter.hasNext()) 
		{
			E a = thisIter.next();
			E b = thatIter.next();
			
			  if (a == null && b != null) return false;      
		      if (a != null && b == null) return false;       
		      if (a == null && b == null) continue;          
		      if (!a.equals(b)) return false;   
		}
		return !thisIter.hasNext() && !thatIter.hasNext();  
	}

	 
	
	
	@Override
	public int size()
	{
		return size;
	}

	@Override
	public boolean stackOverflow()
	{
		return size==capacity;
	}

}
