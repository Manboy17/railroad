package nl.saxion.cds.solution;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxQueue;

public class MyQueue<V> implements SaxQueue<V> {
    private final DoubleLinkedList<V> queue;

    public MyQueue() {
        this.queue = new DoubleLinkedList<>();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public String graphViz(String name) {
        return queue.graphViz(name);
    }

    @Override
    public void enqueue(V value) {
        queue.addLast(value);
    }

    @Override
    public V dequeue() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }

        return queue.removeFirst();
    }

    @Override
    public V peek() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }

        return queue.get(0);
    }

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
