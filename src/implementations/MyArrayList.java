//april 4.
package implementations;

import java.io.Serializable;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

public class MyArrayList<E> implements ListADT<E>, Serializable
{
	
	private E[] elements;
	private int size;
	private static final int DEFAULT_CAPACITY = 10;
	
	/**
	 * Constructs an empty list with the default initial capacity.
	 */
	public MyArrayList() 
	{
		elements = (E[]) new Object[DEFAULT_CAPACITY];
		size = 0;
	}
	/**
	  * Returns the number of elements in the list.
	  * 
	  * @return  The size of the list.
	  * Precondition The list is initialized
	  * Postcondition The number of elements in the list is returned
	  */
	@Override
	public int size()
	{
		return size;
	}

	 /**
     * Clears the list, removing all elements.
     * 
     * Precondition: List is initialized.
     * Postcondition: The list is empty, and the size is reset to zero.
     */
	@Override
	public void clear()
	{
		elements = (E[]) new Object[DEFAULT_CAPACITY];
		size = 0;
		
	}

    /**
     * Adds an element at the specified index in the list
     * 
     * @param index the position element is added
     * @param toAdd the element that is added
     * @throws NullPointerException If the element to add is null.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * Precondition: List is initialized, element is no null, and index is valid.
     * Postcondition: The element is added at the specified index, and the size of the list is increased by one.
     */
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException
	{
		if (toAdd == null) 
		{
			throw new NullPointerException("Cannot have null elements");
		}
		if (index < 0 || index > size()) 
		{
			throw new IndexOutOfBoundsException("Index must be greater than zero and less than or equal to size");
		}
		ensureCapacity();
		
		for(int i = size; i > index; i--) 
		{
			elements[i] = elements [i-1];
		}
		elements[index] = toAdd;
		size++;
		return true;
	}
	
	
	 /**
     * Adds an element to the end of the list.
     * 
     * @param toAdd The element to add.
     * @throws NullPointerException If the element to add is null.
     * Precondition: List is initialized and element is not null
     * Postcondition: The element is added at the end of the list, and the size increases by one.
     */
	@Override
	public boolean add(E toAdd) throws NullPointerException
	{
		if(toAdd == null) 
		{
			throw new NullPointerException("Cannot have null elements");
		}
		ensureCapacity();
		elements[size++] = toAdd;
		
		return true;
	}

	 /**
     * makes sure list has enough capacity to increase if full the capacity is doubled
     * 
     * Precondition: List is initialized and capacity might need to be increased
     * Postcondition: When needed the list's capacity is increased.
     */
	private void ensureCapacity() 
	{
		if(size >= elements.length) 
		{
			E[] newArray = (E[]) new Object[elements.length * 2];
			System.arraycopy(elements, 0, newArray, 0, elements.length);
			elements = newArray;
		}
	}
    /**
     * Adds all elements from another list to new list
     * 
     * @param toAdd The list whose elements are to be added.
     * @throws NullPointerException If the list to add is null.
     * Precondition: List is initialized, and the list to add is non-null.
     * Postcondition: All elements from the other list are added to this list.
     */
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException
	{
		if (toAdd == null) 
		{
			throw new NullPointerException("Cannot add a null list");
		}
		
		if (toAdd.isEmpty()) 
		{
			return false;
		}
		
		for (int i = 0; i < toAdd.size(); i++) 
		{
			E item = toAdd.get(i);
			if (item == null) 
			{
				throw new NullPointerException("Cannot have null elements");
			}
			this.add(item);
		}
		
		return true;
	}

    /**
     * Returns the element at a specific index
     * 
     * @param index The index of the element to get
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * Precondition: List is initialized, and index exists
     * Postcondition: The element at the index is returned
     */
	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= size()) 
		{
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

	 /**
     * Removes the element at the specified index and returns it.
     * 
     * @param index The index of the element to remove.
     * @return The removed element.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * Precondition: List is initialized, and index is valid.
     * Postcondition: The element at the specific index is removed, and the size of the list reduces
     */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= size()) 
		{
			throw new IndexOutOfBoundsException("Index must be greater than zero but less than size");
		}
		
		E removed = elements[index];
		
		for (int i = index; i < size -1; i++) 
		{
			elements[i] = elements [i + 1];
			
		}
		
		elements[size -1] = null;
		size --;
		return removed;
	}

	  /**
     * The spefifc element is rmeoved at first occurence
     * 
     * @param toRemove The element to remove.
     * @return The removed element, or null if the element is not found.
     * @throws NullPointerException If the element to remove is null.
     * Precondition: List is initialized, and element is non-null.
     * Postcondition: The specified element is removed, or null is returned if not found.
     */
	@Override
	public E remove(E toRemove) throws NullPointerException
	{
		if (toRemove == null) 
		{
			throw new NullPointerException("Cannot have null elements");
		}
		
		for (int i = 0; i< size; i++) 
		{
			if (toRemove.equals(elements[i])) 
			{
				return remove(i);
			}
		}
		return null;
	}

    /**
     * 
     * new element replaces the specified one at the index and returns the old one
     * @param index The index of the element to replace.
     * @param toChange The new element to set.
     * @return The old element that was replaced.
     * @throws NullPointerException If the new element is null.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * Precondition: List is initialized, and element is non-null.
     * Postcondition: The element at the specified index is replaced, and the old element is returned.
     */
	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException
	{
		if (toChange == null) 
		{
			throw new NullPointerException("Cannot have null elements");
		}
		if (index < 0 || index >= size()) 
		{
			throw new IndexOutOfBoundsException("Index must be greater than zero but less than size");
		}
		
		E old = elements[index];
		elements[index] = toChange;
		return old;
	}

	 /**
     * Checks if the list is empty.
     * 
     * @return True if the list is empty, false otherwise.
     * Precondition: List is initialized.
     * Postcondition: Returns whether the list contains any elements.
     */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}
    
	 /**
     * Checks if the list contains the specified element.
     * 
     * @param toFind The element to search for.
     * @return True if the element is found, false otherwise.
     * @throws NullPointerException If the element to search for is null.
     * Precondition: List is initialized, and element is non-null.
     * Postcondition: Returns whether the list contains the specified element.
     */
	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		if (toFind == null) 
		{
			throw new NullPointerException("Cannot have null elements");
		}
		for (int i = 0; i< size; i++) 
		{
			if (toFind.equals(elements[i])) 
			{
				return true;
			}
		}
		return false;
	}

	 /**
     * Copies the list elements into the given array.
     * 
     * @param toHold The array to hold the elements.
     * @return The array containing the elements of the list.
     * @throws NullPointerException If the provided array is null.
     * Precondition: Array is initialized and non-null.
     * Postcondition: Elements are copied into the provided array.
     */
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException
	{
		if (toHold == null) 
		{
			throw new NullPointerException();
		}
		if (toHold.length < size) 
		{
			toHold = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
		}
		System.arraycopy(elements, 0, toHold, 0, size);
		
		if(toHold.length > size) 
		{
			toHold[size] = null;
		}
		return toHold;
	}

	 /**
     * Converts the list to an array of objects.
     * 
     * @return An array containing all the elements in the list.
     * Precondition: List is initialized.
     * Postcondition: Returns an array containing the list elements.
     */
	@Override
	public Object[] toArray()
	{
		Object[] result = new Object[size];
		System.arraycopy(elements, 0, result, 0, size);
		return result;
	}

	 /**
     * Returns an iterator to traverse the list.
     * 
     * @return An iterator for the list.
     * Precondition: List is initialized.
     * Postcondition: Returns an iterator for the list.
     */
	@Override
	public Iterator<E> iterator()
	{
		return new ArrayListIterator();
	}
	/**
	 * Implements iterator for Array list.
	 */
	private class ArrayListIterator implements Iterator<E>
	{
		private int currentIndex = 0;

		/**
		 * Checks for next element in list.
		 * @return True if there is a next element, false otherwise.
		 * 
		 */
		@Override
		public boolean hasNext()
		{
			return currentIndex < size;
		}

		/**
		 * Returns next element in list.
		 * @return The next element.
         * @throws NoSuchElementException If there are no more elements in the list.
		 */
		@Override
		public E next() throws NoSuchElementException
		{
			if(!hasNext()) 
			{
				throw new NoSuchElementException("No more elements in the iteration");
			}
			return elements[currentIndex++];
		}
		
	}

}
