package implementations;

import java.io.Serializable;

public class MyDLLNode<E> implements Serializable
{
	private E element;
	private MyDLLNode<E> prev;
	private MyDLLNode<E> next;
	
	 /**
     * Creates a node with the given element. The previous and next nodes are set to null.
     * 
     * @param element The element to store in the node.
     */
	public MyDLLNode (E element) 
	{
		this.element = element;
		this.prev = null;
		this.next = null;
	}
	
    /**
     * Constructs a new node with the specified element which links to prev and next nodes
     * 
     * @param element The element to be stored in the node.
     * @param prev The previous node in the list.
     * @param next The next node in the list.
     * Precondition: The element, prev, and next must be valid reference
     * Postcondition: A new node is created and links to the prev and next nodes
     */
	public MyDLLNode (E element, MyDLLNode<E> prev, MyDLLNode<E> next) 
	{
		this.element = element;
		this.prev = prev;
		this.next = next;
	}

	/**
     * Returns the element stored in the node.
     * 
     * @return The element in the node
     * Precondition: The node is not null.
     * Postcondition: The element is returned from the node.
     */
	public E getElement()
	{
		return element;
	}

	/**
     * Sets the element stored in this node.
     * 
     * @param element The new element to store in the node.
     * @throws NullPointerException If the element is null.
     * Precondition: The element is not null.
     * Postcondition: The element in the node is updated to new value.
     */
	public void setElement(E element)
	{
		this.element = element;
	}

	/**
     * Previous node linked to this node is returned.
     * 
     * @return The previous node in the list.
     * Precondition: The node exists.
     * Postcondition: The previous node is returned.
    */
	public MyDLLNode<E> getPrev()
	{
		return prev;
	}


    /**
     * Previous node linked to this node is set.
     * 
     * @param prev The new previous node to link.
     * Precondition: The node exists.
     * Postcondition: The previous node link is set.
    */
	public void setPrev(MyDLLNode<E> prev)
	{
		this.prev = prev;
	}
 
	/**
     * Returns the next node linked to this node.
     * 
     * @return The next node in the list.
     * Precondition: The node exists.
     * Postcondition: The next node is returned.
     */
	public MyDLLNode<E> getNext()
	{
		return next;
	}

	/**
     * The next node linked to this node is set.
     * 
     * @param next The new next node to link.
     * Precondition: The node exists.
     * Postcondition: The next node link is updated.
     */
	public void setNext(MyDLLNode<E> next)
	{
		this.next = next;
	}
	
	
	
}
