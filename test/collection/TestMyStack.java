package collection;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.solution.MyStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestMyStack {
    private MyStack<Integer> stack;

    @BeforeEach()
    void createExampleStack() {
        stack = new MyStack<>();
    }

    @Test
    void GivenEmptyStack_WhenNoChanges_ConfirmItIsEmpty() {
        assertTrue(stack.isEmpty());
    }

    @Test
    void GivenEmptyStack_WhenPushingAndPoppingElements_ConfirmChangesAreCorrect() {
        stack.push(1);
        assertEquals(1, stack.size());
        stack.push(2);
        assertEquals(2, stack.size());
        assertFalse(stack.isEmpty());

        int poppedEl = stack.pop();
        assertEquals(2, poppedEl);
        assertEquals(1, stack.size());

        int peeked = stack.peek();
        assertEquals(1, peeked);
        assertFalse(stack.isEmpty());
    }

    @Test
    void GivenEmptyStack_WhenPeekingElement_ConfirmItThrowsException() throws EmptyCollectionException {
        assertThrows(EmptyCollectionException.class, () -> stack.peek());
    }

    @Test
    void GivenSheetsStack_WhenNoChanges_ConfirmInitialContent() {
        stack.push(1);
        stack.push(2);
        assertEquals("[ 2 1 ]", stack.toString());
        System.out.println(stack.graphViz());
    }

}
