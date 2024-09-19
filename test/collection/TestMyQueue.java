package collection;

import nl.saxion.cds.collection.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import nl.saxion.cds.solution.MyQueue;

public class TestMyQueue {
    private MyQueue<Integer> queue;

    @BeforeEach
    void createExampleQueue() {
        this.queue = new MyQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
    }

    @Test
    void WhenGivenQueue_WhileMakingChanges_ConfirmResultsAreCorrect() {
        // check order after dequeued
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.size());

        // confirm front element after peek
        assertEquals(2, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void WhenGivenEmptyQueue_ConfirmItThrowsAnException() {
        MyQueue<String> array = new MyQueue<>();

        assertTrue(array.isEmpty());
        assertThrows(EmptyCollectionException.class, array::peek);
        assertThrows(EmptyCollectionException.class, array::dequeue);
    }

    @Test
    void GivenQueue_WhenNoChanges_ConfirmInitialContent() {
        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
        assertEquals("[ 1 2 3 ]", queue.toString());

        System.out.println(queue.graphViz());
    }

}
