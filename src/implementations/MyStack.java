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
	
	/**
	 * stack construct has no capacity limit
	 */
	public MyStack()
	{
		this.capacity = Integer.MAX_VALUE; 
	}

	/**
	 * Represents a node in the in the stack which contains a element and reference.
	 * @param <E> 
	 */
	private static class Node<E>
	{
		E data;
		Node<E> next;
		Node(E data)
		{
			this.data = data;
		}
	}
    /**
     * adds a element to the top of the stack
     * @throws NullPointerException If the element to add is null.
     * @throws IllegalStateException If the stack is full.
     * Precondition: Stack is not full and initialized. element is not null
     * Postcondition:element is added to the top of the stack
     */
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
   /**
    * Removes and returns the top element from the stack.
    * @return: returns top element from stack.
	 * @throws EmptyStackException if the stack is empty.
	 * Precondition: The stack is not empty and unidealized .
	 * Postcondition: The top element is removed
    */
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

	/**
	 * Looks at the top element of the stack without removing it.
	  * 
	  * @return The top element of the stack.
      * @throws EmptyStackException If the stack is empty.
	  * Precondition: The stack is not empty and initialized
	  * Postcondition: The stack remains unchanged and top element is returned 
	 */
	@Override
	public E peek() throws EmptyStackException
	{
		if(top == null) 
		{
			throw new EmptyStackException();
		}
		return top.data;
	}
	 /**
     * Clears the stack by removing all elements.
     * 
     * Precondition: Stack is initialized.
     * Postcondition: Stack is empty, and size is reset to zero.
     */
	@Override
	public void clear()
	{
		top = null;
		size = 0;
		
	}
	 /**
     * Checks if the stack is empty.
     * 
     * @return True if the stack is empty, false otherwise.
     * Precondition: Stack is initialized.
     * Postcondition: Returns whether the stack contains any elements.
     */
	@Override
	public boolean isEmpty()
	{
		return top == null;
	}

	
	/**
	 * Converts the stack to an array of objects.
	 * 
	 * @return An array with all the elements in the stack
     * Precondition: Stack is initialized.
     * Postcondition: The stack's elements are copied into an array.
	 */
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
	
	  /**
     * Converts the stack to an array of the specified type.
     * 
     * @param holder The array to store the elements.
     * @return An array containing all the elements in the stack.
     * @throws NullPointerException If the array is null
     * Precondition: Holder array is initialized and not null
     * Postcondition: Stack elements are copied into the provided array.
     */
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

	

     /**
     * Checks if the stack contains a specified element.
     * 
     * @param toFind 
     * @return True if the element is found, false otherwise.
     * @throws NullPointerException If the element to find is empty.
     * Precondition: Stack is initialized and element is not null
     * Postcondition: Returns whether the stack contains the specified element.
     */
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

    /**
     * Searches for an element in the stack and returns its position from the top.
     * 
     * @param toFind 
     * @return The position of the element from the top of the stack, or -1 if not found.
     * Precondition: Stack is initialized and the element is not null
     * Postcondition: The position of the element from the top of the stack is returned.
     */
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

    /**
     * Returns an iterator to traverse the stack.
     * 
     * @return An iterator for the stack.
     * Precondition: Stack is initialized.
     * Postcondition: Returns an iterator to traverse the stack.
     */
	@Override
	public Iterator<E> iterator()
	{
		return new StackIterator();
	}
	private class StackIterator implements Iterator<E>
	{
		private Node<E> current = top;

		
		/**
		 * Checks for another element in the stack.
		 */
		@Override
		public boolean hasNext()
		{
			return current != null;
		}
         /**
          * Returns the next element in the stack.
          */
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

    /**
     * two stacks are compared for equality
     * 
     * @param that The stack to compare with.
     * @return True if the stacks are equal, false otherwise.
     * Precondition: Both stacks are initialized.
     * Postcondition: Returns whether both stacks are equal.
     */
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

	 
	
	 /**
	  * Returns the number of elements in the stack.
	  * 
	  * @return  The size of the stack.
	  * Precondition The stack is initialized
	  * Postcondition The number of elements in the stack is returned
	  */
	@Override
	public int size()
	{
		return size;
	}

	 /**
     * Checks if the stack has reached its maximum capacity.
     * 
     * @return True if the stack is full, false otherwise.
     * Precondition: Stack is initialized.
     * Postcondition: Returns whether the stack is full.
     */
	@Override
	public boolean stackOverflow()
	{
		return size==capacity;
	}

}
