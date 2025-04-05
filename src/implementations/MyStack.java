package implementations;

import java.util.EmptyStackException;

import java.util.NoSuchElementException;
import utilities.ListADT;
import utilities.Iterator;
import utilities.StackADT;

public class MyStack<E> implements StackADT<E>
{
	private final ListADT<E> arrayList;
    private final int capacity;
	
	public MyStack(int capacity) 
	{
		if (capacity <= 0 ) 
		{
			throw new IllegalArgumentException("Capacity must be greater than zero");
		}
		this.capacity = capacity;
		this.arrayList = new MyArrayList<>();
	}
	
	/**
	 * stack construct has no capacity limit
	 */
	public MyStack()
	{
		this.capacity = Integer.MAX_VALUE; 
		this.arrayList = new MyArrayList<>();
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
			 throw new NullPointerException("Element cannot be null.");
		 }
	        if (stackOverflow()) 
	        {
	        	throw new IllegalStateException("Stack is full.");
	        }
	        arrayList.add(toAdd);
		
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
		if (isEmpty()) 
		{
			throw new EmptyStackException();
		}
        return arrayList.remove(arrayList.size() - 1);
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
		if (isEmpty()) 
	{
		throw new EmptyStackException();
	}
    return arrayList.get(arrayList.size() - 1);
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
		arrayList.clear();
		
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
		return arrayList.isEmpty();
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
		Object[] result = new Object[arrayList.size()];
	    for (int i = arrayList.size() - 1, j = 0; i >= 0; i--, j++) 
	    {
	        result[j] = arrayList.get(i);
	    }
	    return result;
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
	@Override
	public E[] toArray(E[] holder) throws NullPointerException
	{
		if (holder == null) {
	        throw new NullPointerException("Input cannot be null");
	    }
 
	    int count = arrayList.size();  // Replaces node traversal to count elements
 
	    if (holder.length < count) {
	        holder = (E[]) java.lang.reflect.Array.newInstance(
	            holder.getClass().getComponentType(), count
	        );
	    }
 
	
	    int currentIndex = arrayList.size() - 1;  
	    for (int i = 0; i < count; i++) {
	        holder[i] = arrayList.get(currentIndex);  
	        currentIndex--;                      
	    }
 
	    if (holder.length > count) {
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
		return arrayList.contains(toFind);
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
		for (int i = arrayList.size() - 1, position = 1; i >= 0; i--, position++) 
		{
            E element = arrayList.get(i);
            if ((toFind == null && element == null) || (toFind != null && toFind.equals(element))) 
            {
                return position; 
            }
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
		 private int currentIndex = arrayList.size() - 1;

		
		/**
		 * Checks for another element in the stack.
		 */
		@Override
		public boolean hasNext()
		{
			return currentIndex >= 0;
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
	            return arrayList.get(currentIndex--);
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
		return arrayList.size();
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
		return arrayList.size() == capacity;
	}

}
