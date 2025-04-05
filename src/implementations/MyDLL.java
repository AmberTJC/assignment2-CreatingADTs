/**
 * @author amber c, nawal m, aiana s
 */


package implementations;

import java.io.Serializable;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;
/**
 * Using the ListADT MyDLL provides a data structure to hold elements in a list.
 * @param <E> The elements to be held by the list
 */
public class MyDLL<E> implements ListADT<E>, Serializable
{
	
	private MyDLLNode<E> head;
	private MyDLLNode<E> tail;
	private int size;
	
	public MyDLL() 
	{
		head = null;
		tail = null;
		size = 0;
	}
/**
 * Size method returns the number of elements in the list
 * Precondition: list exists
 * Postcondition: number of elements returned
 */
	@Override
	public int size()
	{
		return size;
	}

	/**
	 * clears the elements from the list
	 * Precondition: list exists
	 * Postcondition: list is cleared
	 */
	@Override
	public void clear()
	{
		head = null;
		tail = null;
		size = 0;
		
	}

	
	/**
	 * adds an element to the list
	 * Precondition: List exists and value not null or invalid index
	 * Postcondition: value added to the list
	 * @param index the index of the value to be added
	 * @param toAdd the value to be added
	 * @exception throws NullPointerException when value to be added is null and IndexOutOfBoundsException when the
	 * index is not a permissible value
	 */
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException
	{
		if (toAdd == null) 
		{
			throw new NullPointerException("Cannot use null values");
		}
		
		if (index <0 || index > size) 
		{
			throw new IndexOutOfBoundsException();
		}
		
		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
		
		if (index == 0) 
		{
			newNode.setNext(head);
			if( head != null) {
				head.setPrev(newNode);
			}
			head = newNode;
			if (size == 0) {
				tail = newNode;
			}
		}
		
			else if (index == size) {
				 tail.setNext(newNode); 
			     newNode.setPrev(tail);
			     tail = newNode;
			}  
		
			 else {
				MyDLLNode<E> current = getNode(index);
				MyDLLNode<E> previous = current.getPrev();
				
				newNode.setNext(current);
				newNode.setPrev(previous);
			
				if (previous != null) 
				{
					previous.setNext(newNode);
				}
				current.setPrev(newNode);
			}
		size++;
		return true;
	}
/**
 * Adds an element to the list
 * Precondition: List exists
 * Postcondition: value added to list
 * @param toAdd the element to be added
 * @exception throws NullPointerException when value to be added is null
 */
	@Override
	public boolean add(E toAdd) throws NullPointerException
	{

		if (toAdd == null) 
		{
			throw new NullPointerException("Cannot use null values");
		}
		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
		
		if (isEmpty()) 
		{
			head = tail = newNode;
		}
		else 
		{
		    newNode.setPrev(tail); 
		    tail.setNext(newNode); 
		    tail = newNode; 
		}
		size++;
		return true;
	}

	
	/**
	 * Adds all values to list
	 * Precondition: List exists
	 * Postcondition: All values added to list
	 * @param toAdd values to be added
	 * @exception throws NullPointerException when values to be added are null
	 * 
	 */
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException
	{
		if (toAdd == null) 
		{
			throw new NullPointerException();
		}
		for(int i = 0; i< toAdd.size(); i++) 
		{
			add(toAdd.get(i));
		}
		return true;
	}
/**
 * gets the value of a given node
 * Precondition: List exists
 * Postcondition: value retrieved
 * @param index the index of the node to be retrieved
 * @exception throws IndexOutOfBoundsException when index is not permissible 
 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		if (index <0 || index > size) 
		{
			throw new IndexOutOfBoundsException();
		}
		return getNode(index).getElement();
	}
/**
 * removes a node from the list by index
 * Precondition: List exists
 * Postcondition: node is removed
 * @param index the index of the node to be removed
 * @exception throws IndexOutOfBoundsException when the index is not permissible.
 */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException
	{
		if (index <0 || index > size) 
		{
			throw new IndexOutOfBoundsException();
		}
		
		MyDLLNode<E> node = getNode(index);
		
		return unlink(node);
	}
/**
 * removes a node by element
 * Precondition: list exists and node is not null
 * Postcondition: node is removed
 * @param toRemove the element to be removed
 * @exception throws NullPointerException if the element to be removed is null
 */
	@Override
	public E remove(E toRemove) throws NullPointerException
	{
		if (toRemove == null) 
		{
			throw new NullPointerException("Cannot have null values");
		}
		
		MyDLLNode<E> current = head;
		while (current != null) 
		{
			if (toRemove.equals(current.getElement())) 
			{
				return unlink(current);
			}
			current = current.getNext();
		}
		return null;
	}
/**
 * sets the element of a given node
 * Precondition: list exists
 * Postcondition: element of a node is changed
 * @param index the index of the node to be changed
 * @param toChange the element to be changed
 * @exception throws NullPointerException if value is null, IndexOutOfBoundsException if the index is not
 * permissible
 */
	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException
	{
		if (toChange == null) 
		{
			throw new NullPointerException("Cannot have null values");
		}
		if (index <0 || index > size) 
		{
			throw new IndexOutOfBoundsException();
		}
		
		MyDLLNode<E> node = getNode(index);
		E old = node.getElement();
		node.setElement(toChange);
		
		return old;
	}
/**
 * checks whether the list is empty returns true if empty false otherwise
 * Precondition: list exists
 * Postcondition: nothing changes and boolean value is returned
 */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 * searches list for a given value
	 * Precondition: list exists
	 * Postcondition: returns true if value found false otherwise
	 * @param toFind the value to be found
	 * @exception throws NullPointerException if value is null
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		if (toFind == null) 
		{
			throw new NullPointerException("Cannot use null values");
		}
		
		MyDLLNode<E> current = head;
		while(current != null) 
		{
			if (toFind.equals(current.getElement())) 
			{
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	/**
	 * Puts values into an array
	 * Precondition: List exists
	 * Postcondition: array created with the values of the list
	 * @param toHold the array created
	 * @exception throws NullPointerException if values are null
	 */
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException
	{
		if (toHold == null) 
		{
			throw new NullPointerException();
		}
		if(toHold.length< size) 
		{
			toHold = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
		}
		MyDLLNode<E> current = head;
		int i = 0;
		
		while (current != null) 
		{
			toHold[i++] = current.getElement();
			current = current.getNext();
		}
		if (toHold.length > size) 
		{
			toHold[size] = null;
		}
		return toHold;
	}

	/**
	 * Puts values into an array
	 * Precondition: List exists
	 * Postcondition: array created with the values of the list
	 */
	@Override
	public Object[] toArray()
	{
		Object[] result = new Object[size];
		MyDLLNode<E> current = head; 
		int i = 0;
		
		while (current != null) 
		{
			result[i++] = current.getElement();
			current= current.getNext();
		}
		return result;
	}

	/**
	 * Iterator to iterate through the values of the list
	 * Precondition: list exists
	 * Postcondition: List iterated through
	 */
	@Override
	public Iterator<E> iterator()
	{
		return new MyDLLIterator();
	}
	
	
	//Helper functions
	/**
	 * retrieves a given node
	 * Precondition: list exists
	 * Postcondition: node is retrieved
	 * @param index the index of the node to be retrieved
	 * @return the node specified to be retrieved
	 * @throws IndexOutOfBoundsException when the index is not permissible
	 */
	private MyDLLNode<E> getNode(int index) throws IndexOutOfBoundsException
	{
		if (index <0 || index >= size) 
		{
			throw new IndexOutOfBoundsException();
		}
		
		MyDLLNode<E> current;
		if(index< size/2) 
		{
			current = head;
			for(int i = 0; i < index; i++) 
			{
				current = current.getNext();
			}
		}
		else 
		{
			current = tail;
			for(int i = size - 1; i > index; i-- ) 
			{
				current = current.getPrev();
			}
		}
		
		return current;
	}
	
	/**
	 * unlinks a node from the list
	 * Precondition: list exists
	 * Postcondition: node is unlinked
	 * @param node the node to be unlinked
	 * @return the unlinked node
	 */
	private E unlink (MyDLLNode<E> node) 
	{
		E data = node.getElement();
		MyDLLNode<E> prev = node.getPrev();
		MyDLLNode<E> next = node.getNext();
		
		if (prev != null) 
		{
			prev.setNext(next);
		}
		else 
		{
			head = next;
		}
		if (next != null) 
		{
			next.setPrev(prev);
		}
		else 
		{
			tail = prev;
		}
		size--;
		return data;
	}

	/**
	 * the custom Iterator for the MyDLL class
	 * Precondition: list exists
	 * Postcondition: List iterated through
	 */
		private class MyDLLIterator implements Iterator<E>
		{
			private MyDLLNode<E> current = head;
			
			/**
			 * Determines if there is another value next in the list
			 * Precondition: list exists
			 * Postcondition: returns the value if it is not null
			 */
			@Override
			public boolean hasNext()
			{
				return current !=null;
			}

			/**
			 * gets the next element in the list
			 * Precondition: list exists
			 * Post condition: next element is retrieved
			 */
			@Override
			public E next() throws NoSuchElementException
			{
				if ( !hasNext()) 
				{
					throw new NoSuchElementException();
				}
				E data = current.getElement();
				current = current.getNext();
				
				return data;
			}
			
		}
}
