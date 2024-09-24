package collection;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.solution.MyHashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMyHashMap {
    @Test
    void GivenEmptyHashMap_WhenNoActions_ConfirmItIsEmpty() {
        MyHashMap<Integer, String> map = new MyHashMap<>(10);
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

    @Test
    void GivenEmptyHashMap_WhenAddingValues_ConfirmTheResultsAreCorrect() {
        MyHashMap<Integer, String> map = new MyHashMap<>(10);
        map.add(62, "one");
        map.add(26, "two");
        map.add(35, "three");
        map.add(72, "four");
        map.add(52, "five");
        map.add(44, "six");
        map.add(16, "seven");
        map.add(81, "eight");

        assertEquals(8, map.size());
        assertFalse(map.isEmpty());
        assertTrue(map.contains(26));
        assertEquals("two", map.get(26));
        assertTrue(map.contains(16));
        assertEquals("seven", map.get(16));
        assertNull(map.get(99));
        System.out.println(map.graphViz("MyHashMap"));

        assertThrows(DuplicateKeyException.class, () -> map.add(62, "duplicate"));
        assertThrows(DuplicateKeyException.class, () -> map.add(44, "duplicate"));

        map.add(9, "nine");
        map.add(10, "ten");
        assertThrows(OutOfMemoryError.class, () -> map.add(11, "eleven"));
    }


    @Test
    void GivenHashMap_WhenRemovingValues_ConfirmTheResultsAreCorrect() {
        MyHashMap<Integer, String> map = new MyHashMap<>(10);
        map.add(62, "one");
        map.add(26, "two");

        assertEquals(2, map.size());
        assertTrue(map.contains(62));

        String removedValue = map.remove(62);
        assertEquals("one", removedValue);
        assertEquals(1, map.size());
        assertFalse(map.contains(62));
        assertThrows(KeyNotFoundException.class, () -> map.remove(62));
    }


    @Test
    void GivenHashMap_WhenGettingKeys_ConfirmCorrectKeysAreReturned() {
        MyHashMap<Integer, String> map = new MyHashMap<>(10);
        map.add(62, "one");
        map.add(26, "two");
        map.add(35, "three");

        SaxList<Integer> keys = map.getKeys();

        assertEquals(3, keys.size());
    }


    @Test
    void GivenHashMap_WhenAddingValues_ConfirmToStringOutputIsCorrect() {
        MyHashMap<Integer, String> map = new MyHashMap<>(10);

        map.add(62, "one");
        map.add(26, "two");
        map.add(35, "three");

        String expected = "{(2:62,one)(5:35,three)(6:26,two)}";

        assertEquals(expected, map.toString());
    }
}
