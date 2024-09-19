package nl.saxion.cds.solution;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.collection.SaxSearchable;
import nl.saxion.cds.collection.ValueNotFoundException;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<V> implements SaxList<V>, SaxSearchable<V> {
    private Node<V> head;
    private Node<V> tail;
    private int size;

    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean contains(V value) {
        Node<V> current = head;
        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public V get(int index) throws IndexOutOfBoundsException {
        checkIndex(index);

        Node<V> n = this.head;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }

        return n.value;
    }

    @Override
    public void addLast(V value) {
        Node<V> newNode = new Node<>(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void addFirst(V value) {
        Node<V> newNode = new Node<>(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAt(int index, V value) throws IndexOutOfBoundsException {
        if(index == 0) {
            addFirst(value);
        } else if(index == size) {
            addLast(value);
        } else {
            checkIndex(index);

            Node<V> newNode = new Node<>(value);
            Node<V> current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }

            Node<V> prevNode = current.prev;
            prevNode.next = newNode;
            newNode.prev = prevNode;
            newNode.next = current;
            current.prev = newNode;

            size++;
        }
    }

    @Override
    public void set(int index, V value) throws IndexOutOfBoundsException {
        checkIndex(index);

        Node<V> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.value = value;
    }

    @Override
    public V removeLast() throws EmptyCollectionException {
        if(size == 0) {
            throw new EmptyCollectionException();
        }
        V value = tail.value;
        if(size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return value;
    }

    @Override
    public V removeFirst() throws EmptyCollectionException {
        if(isEmpty()) {
            throw new EmptyCollectionException();
        }

        V value = head.value;
        if(size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return value;
    }

    @Override
    public V removeAt(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        if(index == 0) {
            return removeFirst();
        } else if(index == size - 1) {
            return removeLast();
        }
        Node<V> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        Node<V> prevNode = current.prev;
        Node<V> nextNode = current.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        size--;

        return current.value;
    }

    @Override
    public void remove(V value) throws ValueNotFoundException {
        Node<V> current = head;
        while (current != null) {
            if (current.value.equals(value)) {
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    removeLast();
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    size--;
                }
                return;
            }
            current = current.next;
        }
        throw new ValueNotFoundException("Value not found");
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            private Node<V> current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public V next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                V value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        Node<V> current = head;
        while (current != null) {
            builder.append(current.value);
            if (current.next != null) {
                builder.append(", ");
            }
            current = current.next;
        }
        builder.append(']');
        return builder.toString();
    }


    @Override
    public String graphViz(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("digraph ").append(name).append(" {\n");
        builder.append("    rankdir=LR;\n");
        builder.append("    node [shape=record];\n");

        Node<V> current = head;
        int index = 0;

        while (current != null) {
            builder.append("    ")
                    .append(index)
                    .append(" [label=\"{<prev> | <value> ")
                    .append(current.value)
                    .append(" | <next>}\"];\n");
            current = current.next;
            index++;
        }

        current = head;
        index = 0;

        while (current != null && current.next != null) {
            builder.append("    ")
                    .append(index)
                    .append(":<next> -> ")
                    .append(index + 1)
                    .append(":<value> [arrowhead=vee, arrowtail=dot, dir=both, tailclip=false, arrowsize=1.2];\n");

            builder.append("    ")
                    .append(index + 1)
                    .append(":<prev> -> ")
                    .append(index)
                    .append(":<value> [arrowhead=vee, arrowtail=dot, dir=both, tailclip=false, arrowsize=1.2];\n");

            current = current.next;
            index++;
        }

        builder.append("}");
        return builder.toString();
    }


    private void checkIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public int linearSearch(V element) {
        Node<V> current = head;
        int pos = 0;

        while(current.next != null) {
            if(current.value.equals(element)) {
                return pos;
            }

            current = current.next;
            pos++;
        }

        return pos;
    }

    @Override
    public int binarySearch(Comparator<V> comparator, V element) {
        throw new RuntimeException("Not implemented yet!");
    }
}
