package nl.saxion.cds.solution;
import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxStack;

public class MyStack<V> implements SaxStack<V> {
    private final DoubleLinkedList<V> array;

    public MyStack() {
        this.array = new DoubleLinkedList<>();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public String graphViz(String name) {
        return array.graphViz(name);
    }

    @Override
    public void push(V value) {
        array.addLast(value);
    }

    @Override
    public V pop() throws EmptyCollectionException {
        return array.removeLast();
    }

    @Override
    public V peek() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }

        return array.get(array.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (int i = array.size() - 1; i >= 0; i--) {
            sb.append(array.get(i)).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}
