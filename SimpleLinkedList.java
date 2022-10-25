import java.util.NoSuchElementException;

public class LinkedList<E>{//This implementation is ?doubly-linked? and contains an iterator

    //Private nested/inner/helper class for Nodes of the list
    private static class Node<T> {
        
        //Node instance variables
        private T value;
        private Node<T> prev;
        private Node<T> next;

        //Node Constructor
        private Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    //Instance variables
    private Node<E> head;
    private int size;

    //Constructor
    public LinkedList() {
        head = new Node<E>(null, null, null); // dummy node!
        head.prev = head.next = head;
        size = 0;
    }

    //Private helper class for an Iterator
    private class LinkedListIterator implements Iterator<E>{

        //Iterator instance variables
        private Node<E> current = head;

        //Iterator instance methods
        public boolean hasNext() {
            return (current.next != head);
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            current = current.next;
            return current.value;
        }

    }

    //Generate Iterator Method
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    //Other Instance Methods
    public int size() {
        return size;
    }

    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));

        Node<E> p = head.next;

        for (int i = 0; i < index; i++)
            p = p.next;

        return p.value;
    }

    public void addFirst(E elem) {
        if (elem == null)
            throw new NullPointerException();

        Node<E> second = head.next;

        head.next = new Node<E>(elem, head, second);
        second.prev = head.next;

        size++;
    }

    public void add(E elem) {
        if (elem == null)
            throw new NullPointerException();        

        Node<E> before = head.prev, after = head;

        before.next = new Node<E>(elem, before, after);
        after.prev = before.next;

        size++;
    }
    
}