import java.util.ListIterator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Collection;

// WLinkedList class implements a doubly-linked list that is Iterable
// and provides a ListIterator.  It is a drop-in replacement for
// java.util.LinkedList. The provided implementation is based on Mark
// Allen Weiss's code from Data Structures and Problem Solving Using
// Java 4th edition.
public class WLinkedList<T> implements Iterable<T>{

  // This is the doubly-linked list node.
  private static class Node<T> {
    public T data;
    public Node<T> prev, next;
    public Node( T d, Node<T> p, Node<T> n ) {
      data = d; prev = p; next = n;
    }
  }

  private final Node<T> NOT_FOUND = null; // Used to indicate failure to locate an object
  private int theSize;          // Tracks the size of the list
  private Node<T> beginMarker;  // Dummy node marking the front of the list
  private Node<T> endMarker;    // Dummy node marking the back of the list
  private int modCount = 0;     // Tracks modifications for iterators

  // Construct an empty LinkedList.
  public WLinkedList( ) {
    clear( );
  }
   
  // Change the size of this collection to zero.
  public void clear( ){
    beginMarker = new Node<T>( null, null, null );
    endMarker = new Node<T>( null, beginMarker, null );
    beginMarker.next = endMarker;
      
    theSize = 0;
    modCount++;
  }
   
  // Returns the number of items in this collection.
  // @return the number of items in this collection.
  public int size( ){
    return theSize;
  }
   
   
  // Tests if some item is in this collection.
  // @param x any object.
  // @return true if this collection contains an item equal to x.
  public boolean contains( Object x ){
    return findPos( x ) != NOT_FOUND;
  } 
   
  // Returns the position of first item matching x in this collection,
  // or NOT_FOUND if not found.
  // @param x any object.
  // @return the position of first item matching x in this collection,
  // or NOT_FOUND if not found.
  private Node<T> findPos( Object x ){
    for( Node<T> p = beginMarker.next; p != endMarker; p = p.next )
      if( x == null )
        {
          if( p.data == null )
            return p;
        }
      else if( x.equals( p.data ) )
        return p;
              
    return NOT_FOUND;
  }
   
  // Adds an item to this collection, at the end.
  // @param x any object.
  // @return true.
  public boolean add( T x ){
    addLast( x );   
    return true;         
  }
   
  public boolean addAll(Iterable<T> c){
    boolean added = false;
    for(T thing : c){
      added |= this.add(thing);
    }
    return added;
  }

  // Adds an item to this collection, at specified position.
  // Items at or after that position are slid one position higher.
  // @param x any object.
  // @param idx position to add at.
  // @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
  public void add( int idx, T x ){
    Node<T> p = getNode( idx, 0, size( ) );
    Node<T> newNode = new Node<T>( x, p.prev, p );
    newNode.prev.next = newNode;
    p.prev = newNode;         
    theSize++;
    modCount++;
  }
   
  // Adds an item to this collection, at front.
  // Other items are slid one position higher.
  // @param x any object.
  public void addFirst( T x ){
    add( 0, x );
  }

  // Adds an item to this collection, at end.
  // @param x any object.
  public void addLast( T x ){
    add( size( ), x );
  }    
   
  // Returns the first item in the list.
  // @throws NoSuchElementException if the list is empty.
  public T getFirst( ){
    if( isEmpty( ) )
      throw new NoSuchElementException( );
    return getNode( 0 ).data;    
  }
   
  // Returns the last item in the list.
  // @throws NoSuchElementException if the list is empty.
  public T getLast( ){
    if( isEmpty( ) )
      throw new NoSuchElementException( );
    return getNode( size( ) - 1 ).data;    
  }
   
  // Returns the item at position idx.
  // @param idx the index to search in.
  // @throws IndexOutOfBoundsException if index is out of range.
  public T get( int idx ){
    return getNode( idx ).data;
  }
       
  // Changes the item at position idx.
  // @param idx the index to change.
  // @param newVal the new value.
  // @return the old value.
  // @throws IndexOutOfBoundsException if index is out of range.
  public T set( int idx, T newVal ){
    Node<T> p = getNode( idx );
    T oldVal = p.data;
      
    p.data = newVal;   
    return oldVal;
  }
   
  // Removes the front item in the queue.
  // @return the front item.
  // @throws NoSuchElementException if the list is empty.
  public T remove( ){
    return removeFirst( );    
  }
   
  // Removes the first item in the list.
  // @return the item was removed from the collection.
  // @throws NoSuchElementException if the list is empty.
  public T removeFirst( ){
    if( isEmpty( ) )
      throw new NoSuchElementException( );
    return remove( getNode( 0 ) );    
  }
   
  // Removes the last item in the list.
  // @return the item was removed from the collection.
  // @throws NoSuchElementException if the list is empty.
  public T removeLast( ){
    if( isEmpty( ) )
      throw new NoSuchElementException( );
    return remove( getNode( size( ) - 1 ) );    
  }
   
  // Removes an item from this collection.
  // @param x any object.
  // @return true if this item was removed from the collection.
  public boolean remove( Object x ){
    Node<T> pos = findPos( x );
      
    if( pos == NOT_FOUND )
      return false;
    else
      {
        remove( pos );
        return true;
      }        
  }
   
  // Gets the Node at position idx, which must range from 0 to size( )-1.
  // @param idx index to search at.
  // @return internal node corrsponding to idx.
  // @throws IndexOutOfBoundsException if idx is not between 0 and size()-1, inclusive.
  private Node<T> getNode( int idx ){
    return getNode( idx, 0, size( ) - 1 );
  }
   
  // Gets the Node at position idx, which must range from lower to upper.
  // @param idx index to search at.
  // @param lower lowest valid index.
  // @param upper highest valid index.
  // @return internal node corrsponding to idx.
  // @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
  private Node<T> getNode( int idx, int lower, int upper ){
    Node<T> p;
    if( idx < lower || idx > upper ){
      throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
    }
    if( idx < size( ) / 2 )
      {
        p = beginMarker.next;
        for( int i = 0; i < idx; i++ )
          p = p.next;            
      }
    else
      {
        p = endMarker;
        for( int i = size( ); i > idx; i-- )
          p = p.prev;
      } 
    return p;
  }
   
  // Removes an item from this collection.
  // @param idx the index of the object.
  // @return the item was removed from the collection.
  public T remove( int idx ){
    return remove( getNode( idx ) );
  }
   
  // Removes the object contained in Node p.
  // @param p the Node containing the object.
  // @return the item was removed from the collection.
  private T remove( Node<T> p ){
    p.next.prev = p.prev;
    p.prev.next = p.next;
    theSize--;
    modCount++;
      
    return p.data;
  }
   
  // Tests if this collection is empty.
  // @return true if the size of this collection is zero.
  public boolean isEmpty( ){
    return size( ) == 0;
  }
    
  // Return true if items in other collection
  // are equal to items in this collection
  //(same order, and same according to equals).
  public final boolean equals( Object other ){
    if( other == this )
      return true;
            
    if( ! ( other instanceof Collection ) )
      return false;
        
    Collection rhs = (Collection) other;
    if( size( ) != rhs.size( ) )
      return false;
        
    Iterator<T> lhsItr = this.iterator( );
    Iterator rhsItr = rhs.iterator( );
        
    while( lhsItr.hasNext( ) )
      if( !isEqual( lhsItr.next( ), rhsItr.next( ) ) )
        return false;
                
    return true;            
  }
    
  // Return the hashCode.
  public final int hashCode( ){
    int hashVal = 1;
       
    for( T obj : this )
      hashVal = 31 * hashVal + ( obj == null ? 0 : obj.hashCode( ) );
       
    return hashVal;
  }
    
    
  // Return true if two objects are equal; works
  // if objects can be null.
  private boolean isEqual( Object lhs, Object rhs ){
    if( lhs == null )
      return rhs == null;
    return lhs.equals( rhs );    
  }
    
  // Return a string representation of this collection.
  public String toString( ){
    StringBuilder result = new StringBuilder( "[" );
    for( T obj : this ){
      result.append( obj.toString());
      result.append(", " );
    }
    if(!this.isEmpty()){
      result.delete(result.length()-2,result.length());
    }
    result.append( "]" );
    return result.toString( );
  }    

  ////////////////////////////////////////////////////////////////////////////////
  // Transfer and Coalesce Methods


  // REQUIRED: Transfer all contents of other to the end of this
  // list. Completely empties list other of all elements.
  // 
  // TARGET COMPLEXITY: O(1)
  public void transferFrom(WLinkedList<T> other)
  {
  	  //creates a iterator to traverse through other
  	  Iterator<T> otherIterator = other.iterator();
  	  
  	  //while there are nodes to traverse through
  	  while(otherIterator.hasNext())
  	  {
  	  	  //grabs the item stored in the 'other' node-
  	  	  T item = otherIterator.next();
  	  	  //-and adds it to the end of this list
  	  	  addLast(item);
  	  }
  	  //clears out other
  	  other.clear();
  }

  // REQUIRED: Produce a new list which combines all elements from the
  // lists in the parameter array.  All lists in the parameter array
  // lists[] are completely emptied.
  // 
  // TARGET COMPLEXITY: O(N)
  // N: the size of the parameter array lists[]
  public static <T> WLinkedList<T> coalesce(WLinkedList<T> lists[])
  {
  	  //creates a iterator to traverse through lists
  	  WLinkedList<T> unitedList = new WLinkedList();
  	  
  	  //the for loop moves through lists
  	  for(int i=0; i<lists.length-1;i++)
  	  {
  	  	  //grabs the list stored in the 'lists' node-
  	  	  WLinkedList<T> subList = lists[i];
  	  	  //-and begins traversing through it
  	  	  Iterator<T> subIterator = subList.iterator();
  	  	  while(subIterator.hasNext())
  	  	  {
  	  	  	  //the sublist's contents are added to the united list
  	  	  	  T item = subIterator.next();
  	  	  	  unitedList.addLast(item);
  	  	  }
  	  	  //finally, the sub list is emptied
  	  	  subList.clear();
  	  }

  	//a single, compiled list is returned
    return unitedList;
  }
    

  ////////////////////////////////////////////////////////////////////////////////
  // Iteration Methods

  // Obtains an Iterator object used to traverse the collection.
  // @return an iterator positioned prior to the first element.
  public Iterator<T> iterator( ){
    return new LinkedListIterator( 0 );
  }
   
  // Obtains a ListIterator object used to traverse the collection bidirectionally.
  // @return an iterator positioned prior to the requested element.
  // @param idx the index to start the iterator. Use size() to do complete
  // reverse traversal. Use 0 to do complete forward traversal.
  // @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
  public ListIterator<T> listIterator( int idx ){
    return new LinkedListIterator( idx );
  }


  // Obtains a ListIterator object used to traverse the collection bidirectionally.
  // @return an iterator positioned prior to the first element
  public ListIterator<T> listIterator( ){
    return new LinkedListIterator( 0 );
  }
  
  
//-----------------------------------------------------------------------------
  // This is the implementation of the LinkedListIterator.
  // It maintains a notion of a current position and of
  // course the implicit reference to the LinkedList.
  public class LinkedListIterator implements ListIterator<T>{
    private Node<T> current;                 // Current node, return data on call to next()
    private Node<T> lastVisited = null;      // Used for calls to remove
    private boolean lastMoveWasPrev = false; // Necessary for implementing previous()
    private int expectedModCount = modCount; // How many modifications iterator expects
      
    // Construct an iterator
    public LinkedListIterator( int idx ){
      current = getNode( idx, 0, size( ) );  
    }
      
    // Can the iterator be moved to the next() element
    public boolean hasNext( ){
      if( expectedModCount != modCount )
        throw new ConcurrentModificationException( );
      return current != endMarker;
    }
      
    // Move the iterator forward and return the passed-over element
    public T next( ){
      if( !hasNext( ) )
        throw new NoSuchElementException( ); 
                
      T nextItem = current.data;
      lastVisited = current;
      current = current.next;
      lastMoveWasPrev = false;
      return nextItem;
    }
      
    // Remove the item that was most recently returned by a call to
    // next() or previous().
    public void remove( ) {
      if( expectedModCount != modCount )
        throw new ConcurrentModificationException( );
      if( lastVisited == null )
        throw new IllegalStateException( );
             
      WLinkedList.this.remove( lastVisited );
      lastVisited = null;
      if( lastMoveWasPrev )
        current = current.next;
      expectedModCount++;       
    }

    // REQUIRED: Can the iterator be moved with previous()
    // 
    // TARGET COMPLEXITY: O(1)
    public boolean hasPrevious( )
    {
    	if( expectedModCount != modCount ) //?
    		throw new ConcurrentModificationException( );
    	return current != beginMarker;
    }
      
    // REQUIRED: Move the iterator backward and return the passed-over
    // element
    // 
    // TARGET COMPLEXITY: O(1)
    public T previous( )
    {
    	if( !hasPrevious( ) )
        throw new NoSuchElementException( ); 
                
     	 T previousItem = current.data;
     	 lastVisited = current;
     	 current = current.prev;
     	 lastMoveWasPrev = true;
     	 return previousItem;
    }         

    // REQUIRED: Add the specified data to the list before the element
    // that would be returned by a call to next()
    // (a.k.a. after current)
    // TARGET COMPLEXITY: O(1)
    public void add(T x)
    {
    	//if current isn't the last node...
    	if(hasNext())
    	{
    		//set up the order as current->newNode->current.next
    		Node<T> newNode = new Node(x, current, current.next);
    		current.next = newNode;
    	}
    	//if current was the last node, set the new node's next to null
    	else
    	{
    		Node<T> newNode = new Node(x, current, null);
    		current.next = newNode;
    	}
    }        

    // OPTIONAL: Set the data associated with the last next() or
    // previous() call to the specified data
    public void set(T x)
    {
    	current.prev.data = x;
    }
    // OPTIONAL: Return the integer index associated with the element
    // that would be returned by next()
    public int nextIndex()
    {
      throw new UnsupportedOperationException();
    }
    // OPTIONAL: Return the integer index associated with the element
    // that would be returned by previous()
    public int previousIndex()
    {
      throw new UnsupportedOperationException();
    }
  }
   
}

