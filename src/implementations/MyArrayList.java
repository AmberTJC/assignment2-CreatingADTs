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
	
	public MyArrayList() 
	{
		elements = (E[]) new Object[DEFAULT_CAPACITY];
		size = 0;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public void clear()
	{
		elements = (E[]) new Object[DEFAULT_CAPACITY];
		size = 0;
		
	}

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

	
	private void ensureCapacity() 
	{
		if(size >= elements.length) 
		{
			E[] newArray = (E[]) new Object[elements.length * 2];
			System.arraycopy(elements, 0, newArray, 0, elements.length);
			elements = newArray;
		}
	}
	
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

	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index > size()) 
		{
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

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
		for (int i = 0; i< size; i++) 
		{
			if (toFind.equals(elements[i])) 
			{
				return true;
			}
		}
		return false;
	}

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

	@Override
	public Object[] toArray()
	{
		Object[] result = new Object[size];
		System.arraycopy(elements, 0, result, 0, size);
		return result;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator<E>
	{
		private int currentIndex = 0;

		@Override
		public boolean hasNext()
		{
			return currentIndex < size;
		}

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
