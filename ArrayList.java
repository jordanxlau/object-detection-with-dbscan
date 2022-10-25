/*
 * singly linked list of Integers
 *
 * CSI2510 Algortihmes et Structures de Donnees
 * www.uottawa.ca
 *
 * Robert Laganiere, 2022
 *
*/
public class SimpleLinkedList implements Sequence {

  private class Node {
  
    public Integer element;
	public Node next;
	
	public Node(Integer v, Node p) { 
	  element= v;
	  next= p;
	}
  }
 
  private Node head; 
  private int size;

  // construct empty list
  public SimpleLinkedList() {
       
	head= null;
	size= 0;
  }
  
   // add an Integer instance 
  public boolean add(Integer instance) {

    Node newNode= new Node(instance, head);
	head= newNode;
		
	size++;
	
    return true;
  }

  // search if an Integer instance matching 
  // a key value is in the sequence
  public Integer search(int value) {

    Node n= head;
	
    while (n!=null && n.element.compareTo(value)!=0)
	  n= n.next;
	  
	if (n==null)
	  return null;
	else 
	  return n.element;
  }

  // remove and return an Integer instance 
  // matching a key value (first occurrence of)
  public Integer searchAndRemove(int value) {

	Node p= head;
    if (head.element.compareTo(value)==0) {
	  head= head.next;
	  size--;
	  return p.element;  
	}
	
	Node n= head.next;
	
    while (n!=null && n.element.compareTo(value)!=0) {
	  p= n;
	  n= n.next;
	}

    if (n==null) {
	  return null;
	  
	} else {

	  p.next= n.next;
	  n.next= null;
	  size--;
      return n.element; 
	}	
  }
	  
  // remove and return an Integer instance  
  // at a given positional index
  public Integer removeAt(int index) {

    if (index<0 || index>=size)
	  return null;
	
	Node p= head;
	size--;
	
	if (index==0) {
	  head= head.next;
	  
	} else {
	
	  Node n= head;
      for (int i=1; i<index; i++)
	    n= n.next;

      p= n.next;
	  n.next= n.next.next;
	}
	  
	return p.element;
  }
  
  // get the Integer instance at a 
  // given positional index
  public Integer elementAt(int index) {
	  
    if (index<0 || index>=size)
	  return null;
  
	Node n= head;
    for (int i=0; i<index; i++)
	  n= n.next;
	
	return n.element;
  }
  
    // get the number of instances in the sequence
  public int getSize() {
  
	return size;
  }

  // add an Integer instance (least efficient)
  public boolean addOppositeSide(Integer instance) {
 
    Node newNode= new Node(instance, null);
	
	if (head == null) {
	  head= newNode;
	
	} else {
	
	  Node n= head;
	  
	  while (n.next!=null) {
	    n= n.next; 
	  }
	  
	  n.next= newNode;
	}
	
	size++; 
	
    return true;
  }
 
  // string representation
  public String toString() {
  
    StringBuffer s = new StringBuffer("");
  
    for (Node node= head; node!= null; node= node.next) {
	  s.append("["+node.element+"]");
	}
	
	s.append("("+size+")");
	  
	return s.toString();
  }
  
  public static void main(String[] args) {
  
    SimpleLinkedList tab= new SimpleLinkedList();
	
	tab.add(Integer.valueOf(34));
	tab.add(Integer.valueOf(93));
	tab.add(Integer.valueOf(18));
	tab.add(Integer.valueOf(22));
	tab.add(Integer.valueOf(51));
	System.out.println("A1:" + tab);
	tab.add(Integer.valueOf(17));
	System.out.println("A2:" + tab);
	
	tab.removeAt(1);
	System.out.println("B:" + tab);
	
	tab.searchAndRemove(23);
	System.out.println("C1:" + tab);
	
	tab.searchAndRemove(22);
	System.out.println("C2:" + tab);
	
	System.out.println("D1:" + tab.search(67));
	System.out.println("D2:" + tab.search(93));
	
	tab.addOppositeSide(Integer.valueOf(44));
	System.out.println("E:" + tab);
  }
}
