package nl.saxion.cds.solution;
import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxStack;

public class MyStack<V> implements SaxStack<V> {
    private final DoubleLinkedList<V> array;

    /**
     * Constructor for the MyStack class.
     */
    public MyStack() {
        this.array = new DoubleLinkedList<>();
    }

    /**
     * Checks if the stack is empty.
     * @return true if the stack is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * Returns the size of the stack.
     * @return the size of the stack.
     */
    @Override
    public int size() {
        return array.size();
    }

    /**
     * Returns the graphviz representation of the stack.
     * @param name name of the produced graph
     * @return the graphviz representation of the stack.
     */
    @Override
    public String graphViz(String name) {
        return array.graphViz(name);
    }

    /**
     * Pushes a value onto the stack.
     * @param value the value to push
     */
    @Override
    public void push(V value) {
        array.addLast(value);
    }

    /**
     * Pops a value from the stack.
     * @return the value that was popped
     * @throws EmptyCollectionException if the stack is empty
     */
    @Override
    public V pop() throws EmptyCollectionException {
        return array.removeLast();
    }

    /**
     * Peeks at the value at the top of the stack.
     * @return the value at the top of the stack
     * @throws EmptyCollectionException if the stack is empty
     */
    @Override
    public V peek() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }

        return array.get(array.size() - 1);
    }

    /**
     * Returns a string representation of the stack.
     * @return a string representation of the stack.
     */
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
