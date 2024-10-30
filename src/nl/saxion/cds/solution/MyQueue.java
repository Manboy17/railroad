package nl.saxion.cds.solution;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxQueue;

public class MyQueue<V> implements SaxQueue<V> {
    private final DoubleLinkedList<V> queue;

    public MyQueue() {
        this.queue = new DoubleLinkedList<>();
    }

    /**
     * Checks if the queue is empty.
     * @return true if the queue is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Returns the size of the queue.
     * @return the size of the queue.
     */
    @Override
    public int size() {
        return queue.size();
    }

    /**
     * Returns the graphviz representation of the queue.
     * @param name name of the produced graph
     * @return the graphviz representation of the queue.
     */
    @Override
    public String graphViz(String name) {
        return queue.graphViz(name);
    }

    /**
     * Pushes a value onto the queue.
     * @param value the value to push
     */
    @Override
    public void enqueue(V value) {
        queue.addLast(value);
    }

    /**
     * Pops a value from the queue.
     * @return the value that was popped
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public V dequeue() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }

        return queue.removeFirst();
    }

    /**
     * Peeks at the value at the front of the queue.
     * @return the value at the front of the queue
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public V peek() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }

        return queue.get(0);
    }

    /**
     * Returns a string representation of the queue.
     * @return a string representation of the queue.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (int i = 0; i < queue.size(); i++) {
            sb.append(queue.get(i)).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}
