/******************************************************************************
 *  Name:    Sanjay Khadda
 *
 *  Description:  Write a generic data type for a deque and a randomized queue.
 *  The goal of this assignment is to implement elementary data structures using
 *  arrays and linked lists, and to introduce you to generics and iterators.
 ******************************************************************************/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int length;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    public Deque() {                          // construct an empty deque
        first = null;
        last = first;
        length = 0;
    }
    public boolean isEmpty() {                 // is the deque empty?
        return (length == 0);
    }
    public int size() {                        // return the number of items on the deque
        return length;
    }
    public void addFirst(Item item) {         // add the item to the front
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node oldFirst = first;
        first = new Node();
        first.next = oldFirst;
        first.item = item;
        first.prev = null;
        if (length == 1) {
            last = first;
        }
        else if(length == 2) {
          last = oldFirst;
        }
        length++;
    }
    public void addLast(Item item) {           // add the item to the end
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node oldLast = last;
        last = new Node();
        last.prev = oldLast;
        last.next = null;
        last.item = item;
        if (length == 1) {
            first = last;
        }
        else if(length == 2){
          first = oldLast;
        }
        length++;
    }
    public Item removeFirst() {               // remove and return the item from the front
        if (length == 0) {
            throw new java.util.NoSuchElementException();
        }
        Item ret = first.item;

        if (length == 1) {
            first = null;
            last = first;
            length--;
            return ret;
        }
        Node newFirst = first.next;
        first = new Node();
        first = newFirst;
        first.prev = null;
        length--;
        return ret;
    }
    public Item removeLast() {                // remove and return the item from the end
        if (length == 0) {
            throw new java.util.NoSuchElementException();
        }
        Item ret = last.item;
        if (length == 1) {
            first = null;
            last = first;
            length--;
            return ret;
        }
        Node newLast = last.prev;
        last = new Node();
        last = newLast;
        last.next = null;
        length--;
        return ret;
    }
    private class DequeIterator implements Iterator<Item> {
        private Node cur = first;

        public boolean hasNext() {
            return cur.next != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
    }
    public Iterator<Item> iterator() {  // return an iterator over items in order from front to end
        return new DequeIterator();
    }
}
