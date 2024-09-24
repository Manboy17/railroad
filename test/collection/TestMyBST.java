package collection;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.solution.MyBST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMyBST {
    private MyBST<String, String> tree;

    @BeforeEach
    void createExampleList() {
        tree = new MyBST<>();
        tree.add("D", "first");
        tree.add("B", "second");
        tree.add("F", "third");
        tree.add("A", "fourth");
        tree.add("C", "fifth");
    }

    @Test
    void GivenEmptyTree_WhenNoChanges_ConfirmTreeIsEmpty() {
        MyBST<String, String> emptyTree = new MyBST<>();
        assertTrue(emptyTree.isEmpty());
        assertEquals(0, emptyTree.size());
    }

    @Test
    void GivenTree_WhenAddedValues_ConfirmTheSizeAndGraphViz() {
        assertEquals(5, tree.size());
        String graphViz = tree.graphViz("BST");
        assertNotNull(graphViz);
        System.out.println(graphViz);
    }

    @Test
    void GivenTree_WhenRemoving_ConfirmTheResultsAreCorrect() throws KeyNotFoundException {
        assertEquals(5, tree.size());
        assertTrue(tree.contains("A"));
        tree.remove("A");
        assertEquals(4, tree.size());
        assertFalse(tree.contains("A"));

        tree.remove("D");
        assertEquals(3, tree.size());
        assertFalse(tree.contains("D"));

        tree.remove("C");
        assertEquals(2, tree.size());
        assertFalse(tree.contains("C"));
    }

    @Test
    void GivenTree_WhenRemovingNodeWithTwoChildren_ConfirmCorrectBehavior() throws KeyNotFoundException {
        tree.add("E", "sixth");
        assertTrue(tree.contains("F"));
        tree.remove("F");
        assertEquals(5, tree.size());
        assertFalse(tree.contains("F"));
        assertTrue(tree.contains("E"));
    }

    @Test
    void GivenTree_WhenRemovingRootWithTwoChildren_ConfirmCorrectBehavior() throws KeyNotFoundException {
        tree.remove("D");
        assertEquals(4, tree.size());
        assertFalse(tree.contains("D"));
    }

    @Test
    void GivenTree_WhenGettingElements_ConfirmItIsCorrect() {
        assertEquals(5, tree.size());
        assertEquals("fifth", tree.get("C"));
        assertEquals("first", tree.get("D"));
        assertEquals("third", tree.get("F"));
        assertNull(tree.get("L"));
    }

    @Test
    void GivenTree_WhenGettingKeys_ConfirmItIsCorrect() {
        SaxList<String> keys = tree.getKeys();
        assertEquals(5, keys.size());
        assertTrue(keys.contains("A"));
        assertTrue(keys.contains("B"));
        assertTrue(keys.contains("C"));
        assertTrue(keys.contains("D"));
        assertTrue(keys.contains("F"));
    }

    @Test
    void GivenTree_WhenMakeChanges_ConfirmExceptionsAreThrown() {
        assertThrows(DuplicateKeyException.class, () -> tree.add("D", "duplicate"));
        assertThrows(KeyNotFoundException.class, () -> tree.remove("L"));
    }

    @Test
    void GivenTree_WhenCheckingContains_ConfirmCorrectResults() {
        assertTrue(tree.contains("A"));
        assertTrue(tree.contains("B"));
        assertFalse(tree.contains("G"));
    }

    @Test
    void GivenEmptyTree_WhenRemoving_ConfirmExceptionThrown() {
        MyBST<String, String> emptyTree = new MyBST<>();
        assertThrows(KeyNotFoundException.class, () -> emptyTree.remove("A"));
    }

    @Test
    void GivenTree_WhenRemovingAllElements_ConfirmTreeIsEmpty() throws KeyNotFoundException {
        tree.remove("A");
        tree.remove("B");
        tree.remove("C");
        tree.remove("D");
        tree.remove("F");
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }

    @Test
    void GivenEmptyTree_WhenMakeChanges_ConfirmNullIsReturned() {
        MyBST<String, String> tree = new MyBST<>();
        tree.contains("F");
        tree.get("D");
    }
}
