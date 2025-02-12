package collection;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.ValueNotFoundException;
import nl.saxion.cds.solution.DoubleLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestMyDoubleLinkedList {
    private DoubleLinkedList<String> list;
    private static final int BIG_NUMBER_OF_ELEMENTS = 1000;

    @BeforeEach
    void createExampleList() {
        list = new DoubleLinkedList<>();
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        list.addLast("4");
        list.addLast("5");
    }

    @Test
    void GivenSingleElementList_WhenRemovingFirst_ConfirmListIsEmpty() throws EmptyCollectionException {
        list = new DoubleLinkedList<>();
        list.addLast("onlyElement");
        assertEquals(1, list.size());

        String removed = list.removeFirst();
        assertEquals("onlyElement", removed);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
    }

    @Test
    void GivenSingleElementList_WhenRemovingLast_ConfirmListIsEmpty() throws EmptyCollectionException {
        list = new DoubleLinkedList<>();
        list.addLast("onlyElement");
        assertEquals(1, list.size());

        String removed = list.removeLast();
        assertEquals("onlyElement", removed);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
    }

    @Test
    void GivenListWithMultipleElements_WhenRemovingHead_ConfirmChangesAreCorrect() throws ValueNotFoundException, EmptyCollectionException {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");

        System.out.println(list.graphViz());

        list.remove("A");
        assertEquals("[B, C]", list.toString());
        assertEquals(2, list.size());
        assertEquals("B", list.get(0));
    }

    @Test
    void GivenListWithMultipleElements_WhenRemovingTail_ConfirmChangesAreCorrect() throws ValueNotFoundException, EmptyCollectionException {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");

        list.remove("C");

        assertEquals("[A, B]", list.toString());
        assertEquals(2, list.size());
        assertEquals("B", list.get(1));
    }

    @Test
    void GivenEmptyList_WhenCallingGetters_ConfirmListIsActuallyEmpty() {
        DoubleLinkedList<Object> doubleLinkedList = new DoubleLinkedList<>();
        assertTrue(doubleLinkedList.isEmpty());
        assertEquals(0, doubleLinkedList.size());
        assertEquals("[]", doubleLinkedList.toString());
    }

    @Test
    void GivenSheetsList_WhenCallingContains_ConfirmCorrectResponses() {
        assertTrue(list.contains("2"));
        assertTrue(list.contains("5"));
        assertFalse(list.contains("6"));
    }

    @Test
    void GivenSheetsList_WhenAddingAtBeginning_ConfirmChangesAreCorrect() {
        list.addFirst("d1");
        assertEquals(6, list.size());
        assertFalse(list.isEmpty());

        assertThrows(ValueNotFoundException.class, () -> list.remove("d2"));
    }

    @Test
    void GivenSheetsList_WhenAddingAtIndex_ConfirmChangesAreCorrect() {
        list.addAt(1, "test");
        assertEquals(6, list.size());
        assertFalse(list.isEmpty());
        assertEquals("[1, test, 2, 3, 4, 5]", list.toString());
    }

    @Test
    void GivenEmptyList_WhenAccessingAnyIndex_ThenIndexOutOfBoundsThrown() {
        DoubleLinkedList<Object> doubleLinkedList = new DoubleLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> doubleLinkedList.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> doubleLinkedList.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> doubleLinkedList.addAt(-1, 666));
        assertThrows(IndexOutOfBoundsException.class, () -> doubleLinkedList.addAt(1, 666));
        assertThrows(IndexOutOfBoundsException.class, () -> doubleLinkedList.set(-1, 666));
        assertThrows(IndexOutOfBoundsException.class, () -> doubleLinkedList.set(1, 666));
        assertThrows(EmptyCollectionException.class, doubleLinkedList::removeFirst);
        assertThrows(EmptyCollectionException.class, doubleLinkedList::removeLast);
    }

    @Test
    void GivenSheetsList_WhenGettingElementAtIndex_ConfirmCorrectElementIsReturned() {
        assertEquals("1", list.get(0));
        assertEquals("3", list.get(2));
        assertEquals("5", list.get(4));
    }

    @Test
    void GivenSheetsList_WhenRemovingElement_ConfirmChangesAreCorrect() {
        // Remove specific element
        list.remove("5");
        assertEquals("[1, 2, 3, 4]", list.toString());
        assertEquals(4, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    void GivenSheetsList_WhenRemovingFirstElement_ConfirmChangesAreCorrect() {
        assertEquals(5, list.size());
        list.removeFirst();
        assertEquals(4, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    void GivenSheetsList_WhenRemovingLastElement_ConfirmChangesAreCorrect() {
        assertEquals(5, list.size());
        list.removeLast();
        assertEquals(4, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    void GivenSheetsList_WhenRemovingElementAtIndex_ConfirmChangesAreCorrect() {
        assertEquals("3", list.removeAt(2));  // The value at index 2 should be "3"
        assertEquals("[1, 2, 4, 5]", list.toString());  // Check the list after removal
        assertEquals(4, list.size());

        // Remove the element at index 0 (first element)
        assertEquals("1", list.removeAt(0));  // The first element should be "1"
        assertEquals("[2, 4, 5]", list.toString());
        assertEquals(3, list.size());
    }

    @Test
    void GivenSheetsList_WhenIteratingOverElements_ConfirmResultsAreCorrect() {
        Iterator<String> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("1", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("2", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("3", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("4", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("5", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void GivenSheetsList_WithNoElements_ConfirmResultsAreCorrect() {
        list = new DoubleLinkedList<>();
        Iterator<String> iterator = list.iterator();

        assertFalse(iterator.hasNext());
    }

    @Test
    void GivenSheetsList_WithNoElements_ConfirmErrorIsThrown() {
        list = new DoubleLinkedList<>();
        Iterator<String> iterator = list.iterator();

        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    void GivenSheetsList_WhenSettingValueAtIndex_ConfirmChangesAreCorrect() {
        list.set(0, "start");
        assertEquals("[start, 2, 3, 4, 5]", list.toString());

        list.set(list.size() - 1, "end");
        assertEquals("[start, 2, 3, 4, end]", list.toString());
    }

    @Test
    void GivenLargeList_WhenMakingChanges_ConfirmStateRemainsCorrect() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < BIG_NUMBER_OF_ELEMENTS; i++) {
            list.addLast(i);
        }

        assertEquals(BIG_NUMBER_OF_ELEMENTS, list.size());

        // Test for searching an element using linear search
        int v = list.get(0);
        int i = list.linearSearch(v);
        assertEquals(v, list.get(i));
        v = list.get(BIG_NUMBER_OF_ELEMENTS - 1);
        i = list.linearSearch(v);
        assertEquals(v, list.get(i));

        // Test for searching an element using binary search
        v = list.get(0);
        int finalV = v;
        assertThrows(RuntimeException.class, () -> list.binarySearch(Integer::compareTo, finalV));
    }
}
