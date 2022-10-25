public class ArrayStack<E>{
    
    //Instance variables
    private E[] elems;  // Used to store the elements of this ArrayStack
    private int top;    // Designates the first free cell
    private int capacity;    // Designates the capacity of the Array

    // Constructor
    @SuppressWarnings( "unchecked" )    
    public ArrayStack( int capacity ) {
        elems = (E[]) new Object[ capacity ];
        top = 0;
        this.capacity = capacity;
    }

    //Instance methods
    public boolean isEmpty() {// Returns true if this ArrayStack is empty
        return ( top == 0 );
    }

    public E peek() {// Returns the top element of this ArrayStack without removing it
        // pre-conditions: ! isEmpty()
        return elems[ top-1 ];
    }

    public E pop() {// Removes and returns the top element of this stack
        // pre-conditions: ! isEmpty()
        E saved = elems[ --top ];// *first* decrements top, then access the value!
        elems[ top ] = null; // scrub the memory!
        return saved;
    }

    public void push( E element ) {// Puts the element onto the top of this stack.
        // Pre-condition: the stack is not full
        elems[ top++ ] = element;// *first* stores the element at position top, then increments top
    }

    public int getCapacity() {// Gets current capacity of the array (for testing purposes)
        return elems.length;
    }

    @SuppressWarnings( "unchecked" )
    public void clear(){// Clears stack.
        this.top = 0;
    }

}