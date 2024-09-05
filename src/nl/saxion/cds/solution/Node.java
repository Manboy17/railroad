package nl.saxion.cds.solution;

public class Node<V> {
    V value;
    Node<V> prev;
    Node<V> next;

    Node(V value) {
        this.value = value;
        this.prev = null;
        this.next = null;
    }
}
