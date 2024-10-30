package nl.saxion.cds.solution;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxHeap;

public class MyMinHeap<V extends Comparable<V>> implements SaxHeap<V> {
    private final MyArrayList<V> elements = new MyArrayList<V>();
    private final int IS_LEAF_INDEX = -1;

    /**
     * Checks if the heap is empty.
     * @return true if the heap is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns the size of the heap.
     * @return the size of the heap.
     */
    @Override
    public int size() {
        return elements.size();
    }

    /**
     * Returns the graphviz representation of the heap.
     * @param name name of the produced graph
     * @return the graphviz representation of the heap.
     */
    @Override
    public String graphViz(String name) {
        return elements.graphViz(name);
    }

    /**
     * Returns the index of the left child of the parentIndex.
     * @param parentIndex the index of the parent.
     * @return the index of the left child of the parentIndex.
     */
    private int getLeftChildIndex(int parentIndex) {
        assert parentIndex >= 0 && parentIndex < size();
        int childIndex =  2 * parentIndex + 1;
        return childIndex >= size() ? IS_LEAF_INDEX : childIndex;
    }

    /**
     * Returns the index of the right child of the parentIndex.
     * @param parentIndex the index of the parent.
     * @return the index of the right child of the parentIndex.
     */
    private int getRightChildIndex(int parentIndex) {
        assert parentIndex >= 0 && parentIndex < size();
        int childIndex =  2 * parentIndex + 2;
        return childIndex >= size() ? IS_LEAF_INDEX : childIndex;
    }

    /**
     * Returns the index of the parent of the childIndex.
     * @param childIndex the index of the child.
     * @return the index of the parent of the childIndex.
     */
    private int getParentIndex(int childIndex) {
        assert childIndex >= 1 && childIndex < size();
        return ((childIndex - 1) / 2);
    }

    /**
     *  Moves the element at index up the heap until it is in the correct position.
     * @param index the index of the element to move up.
     */
    private void percolateUp(int index) {
        if(index == 0) return;
        int parentIndex = getParentIndex(index);
        V indexValue = elements.get(index);
        V parentValue = elements.get(parentIndex);
        if (indexValue.compareTo(parentValue) >= 0) return;
        elements.set(parentIndex, indexValue);
        elements.set(index, parentValue);
        percolateUp(parentIndex);
    }

    /**
     * Moves the element at index down the heap until it is in the correct position.
     * @param index the index of the element to move down.
     */
    private void percolateDown(int index) {
        int leftIndex = getLeftChildIndex(index);
        if (leftIndex == IS_LEAF_INDEX) return;

        V indexValue = elements.get(index);
        V leftValue = elements.get(leftIndex);
        int rightIndex = getRightChildIndex(index);
        if(rightIndex == IS_LEAF_INDEX) {
            if(indexValue.compareTo(leftValue) > 0) {
                elements.set(leftIndex, indexValue);
                elements.set(index, leftValue);
            }
        } else {
            V rightValue = elements.get(rightIndex);
            V smallestValue = rightValue;
            int smallestIndex = rightIndex;
            if(leftValue.compareTo(rightValue) < 0) {
                smallestValue = leftValue;
                smallestIndex = leftIndex;
            }
            if(indexValue.compareTo(smallestValue) > 0) {
                elements.set(smallestIndex, indexValue);
                elements.set(index, smallestValue);
                percolateDown(smallestIndex);
            }
        }
    }

    /**
     * Pushes a value onto the heap.
     * @param value the value to push
     */
    @Override
    public void enqueue(V value) {
        elements.addLast(value);
        percolateUp(elements.size() - 1);
    }

    /**
     * Pops a value from the heap.
     * @return the value that was popped
     * @throws EmptyCollectionException if the heap is empty
     */
    @Override
    public V dequeue() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }
        V rootElement = elements.get(0);
        if(elements.size() > 1) {
            elements.set(0, elements.removeLast());
            percolateDown(0);
        } else {
            elements.removeLast();
        }
        return rootElement;
    }

    /**
     * Peeks at the value at the top of the heap.
     * @return the value at the top of the heap
     * @throws EmptyCollectionException if the heap is empty
     */
    @Override
    public V peek() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }

        return elements.get(0);
    }

    /**
     * Returns a string representation of the heap.
     * @return a string representation of the heap.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("minHeap[ ");
        for (var element : elements) {
            stringBuilder.append(element);
            stringBuilder.append(" ");
        }
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}
