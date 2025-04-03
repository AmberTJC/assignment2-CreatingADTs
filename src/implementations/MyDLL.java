package implementations;

import java.io.Serializable;

import utilities.Iterator;
import utilities.ListADT;

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

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public void clear()
	{
		head = null;
		tail = null;
		size = 0;
		
	}

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
		
		if (index == size) 
		{
			return add(toAdd);
		}
		
		if (index == 0) 
		{
			newNode.setNext(head);
			if( head != null) 
			{
				head.setPrev(newNode);
				head = newNode;
			}
			
			if (size == 0) 
			{
				tail = newNode;
			}
			else 
			{
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
			
		}
		return true;
	}

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
			tail.setNext(newNode);
			tail.setPrev(tail);
			tail = newNode;
		}
		size++;
		return true;
	}

	
	
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

	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		if (index <0 || index > size) 
		{
			throw new IndexOutOfBoundsException();
		}
		return getNode(index).getElement();
	}

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

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E[] toArray(E[] toHold) throws NullPointerException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> iterator()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//Helper functions
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

}
