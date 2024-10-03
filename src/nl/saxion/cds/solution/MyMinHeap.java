package nl.saxion.cds.solution;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxHeap;

public class MyMinHeap<V extends Comparable<V>> implements SaxHeap<V> {
    private final MyArrayList<V> elements = new MyArrayList<V>();
    private final int IS_LEAF_INDEX = -1;

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public String graphViz(String name) {
        return null;
    }

    private int getLeftChildIndex(int parentIndex) {
        assert parentIndex >= 0 && parentIndex < size();
        int childIndex =  2 * parentIndex + 1;
        return childIndex >= size() ? IS_LEAF_INDEX : childIndex;
    }

    private int getRightChildIndex(int parentIndex) {
        assert parentIndex >= 0 && parentIndex < size();
        int childIndex =  2 * parentIndex + 2;
        return childIndex >= size() ? IS_LEAF_INDEX : childIndex;
    }

    private int getParentIndex(int childIndex) {
        assert childIndex >= 1 && childIndex < size();
        return ((childIndex - 1) / 2);
    }

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

    @Override
    public void enqueue(V value) {
        elements.addLast(value);
        percolateUp(elements.size() - 1);
    }

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

    @Override
    public V peek() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }

        return elements.get(0);
    }

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
