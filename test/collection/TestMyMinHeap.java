package collection;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.solution.MyMinHeap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMyMinHeap {
    private MyMinHeap<Integer> buildMinHeap() {
        MyMinHeap<Integer> minHeap = new MyMinHeap<>();

        minHeap.enqueue(21);
        minHeap.enqueue(24);
        minHeap.enqueue(31);
        minHeap.enqueue(65);
        minHeap.enqueue(26);
        minHeap.enqueue(32);
        minHeap.enqueue(16);
        minHeap.enqueue(19);
        minHeap.enqueue(68);
        minHeap.enqueue(13);

        return minHeap;
    }

    @Test
    void GivenNewHeap_WhenDoNothing_ConfirmIsEmpty() {
        MyMinHeap<Integer> minHeap = new MyMinHeap<>();

        assertTrue(minHeap.isEmpty());
        assertEquals(0, minHeap.size());
    }

    @Test
    void GivenNewHeap_WhenAddingExampleData_ConfirmSize() {
        MyMinHeap<Integer> minHeap = buildMinHeap();

        assertFalse(minHeap.isEmpty());
        assertEquals(10, minHeap.size());
        assertEquals("minHeap[ 13 16 21 24 19 32 31 65 68 26 ]", minHeap.toString());
    }

    @Test
    void GivenNewHeap_WhenRemovingExampleData_ConfirmSizeAndStructure() {
        MyMinHeap<Integer> minHeap = buildMinHeap();
        int rootValue = minHeap.dequeue();
        assertEquals(13, rootValue);
        assertEquals(9, minHeap.size());
        assertEquals("minHeap[ 16 19 21 24 26 32 31 65 68 ]", minHeap.toString());
    }

    @Test
    void GivenNewHeap_WhenMakeChanges_ConfirmExceptionsAreThrown() {
        MyMinHeap<Integer> minHeap = new MyMinHeap<>();

        assertThrows(EmptyCollectionException.class, minHeap::dequeue);
        assertThrows(EmptyCollectionException.class, minHeap::peek);
    }

    @Test
    void GivenNewHeap_WhenPeeking_ConfirmItIsCorrectAndStructure() {
        MyMinHeap<Integer> minHeap = buildMinHeap();

        int rootValue = minHeap.peek();
        assertEquals(13, rootValue);

        assertEquals(10, minHeap.size());
        assertEquals("minHeap[ 13 16 21 24 19 32 31 65 68 26 ]", minHeap.toString());
    }
}
