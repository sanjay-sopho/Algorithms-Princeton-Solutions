/******************************************************************************
 *  Name:    Sanjay Khadda
 *
 *  Description:  Write a generic data type for a deque and a randomized queue.
 *  The goal of this assignment is to implement elementary data structures using
 *  arrays and linked lists, and to introduce you to generics and iterators.
 ******************************************************************************/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private int length;
    private Item[] a;
    // construct an empty randomized queue
    public RandomizedQueue() {
        length = 0;
        a = (Item[]) new Object[1];
    }
    // is the queue empty?
    public boolean isEmpty() {
        return (length == 0);
    }
    // return the number of items on the queue
    public int size() {
        return length;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < length; i++) {
            copy[i] = a[i];
        }
        a = copy;
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        a[length++] = item;
        if (length == a.length)
            resize(2 * a.length);
    }
    // remove and return a random item
    public Item dequeue() {
        if (length == 0) throw new java.util.NoSuchElementException();
        int el = StdRandom.uniform(length);
        Item item = a[el];
        a[el] = a[length-1];
        a[--length] = null;
        if (length > 0 && length == a.length/4)
            resize(a.length/2);
        return item;
    }
    // return (but do not remove) a random item
    public Item sample() {
        if (length == 0)
            throw new java.util.NoSuchElementException();
        int el = StdRandom.uniform(length);
        return a[el];
    }
    private class RandomizedQueueIterator implements Iterator<Item> {

        private int i = length;
        private int[] order;

        public RandomizedQueueIterator() {
            order = new int[i];
            for (int j = 0; j < i; ++j) {
                order[j] = j;
            }
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return i > 0;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return a[order[--i]];
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

}
