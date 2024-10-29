package collection;

import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import nl.saxion.cds.solution.MyGraph;

import java.util.Iterator;

public class TestMyGraph {
    private MyGraph<String> graph;

    @BeforeEach
    void setup() {
        this.graph = new MyGraph<>(10);
    }

    @Test
    void GivenEmptyGraph_WhenNoActions_ConfirmItIsEmpty() {
        assertTrue(graph.isEmpty());
        assertEquals(0, graph.size());
    }

    @Test
    void GivenEmptyGraph_WhenAddEdge_ConfirmEdgeIsAdded() {
        graph.addEdge("A", "B", 1.5);
        assertFalse(graph.isEmpty());
        assertEquals(2, graph.size());
        assertEquals(1, graph.getEdges("A").size());
    }

    @Test
    void GivenEmptyGraph_WhenAddEdgeBidirectional_ConfirmEdgesAreAdded() {
        graph.addEdgeBidirectional("A", "B", 1.5);
        assertFalse(graph.isEmpty());
        assertEquals(2, graph.size());
        assertEquals(1, graph.getEdges("A").size());
        assertEquals(1, graph.getEdges("B").size());

        SaxGraph.DirectedEdge<String> edgesA = graph.getEdges("A").get(0);
        assertEquals("A", edgesA.from());
        assertEquals(1.5, edgesA.weight());
        assertEquals("B", edgesA.to());

        SaxGraph.DirectedEdge<String> edgesB = graph.getEdges("B").get(0);
        assertEquals("B", edgesB.from());
        assertEquals(1.5, edgesB.weight());
        assertEquals("A", edgesB.to());
        System.out.println(graph.graphViz());
    }

    @Test
    void GivenEmptyGraph_WhenAddEdges_ConfirmTotalWeightCorrect() {
        graph.addEdge("A", "B", 1.5);
        graph.addEdge("B", "C", 2.5);
        graph.addEdge("C", "D", 3.5);
        assertEquals(7.5, graph.getTotalWeight());
    }

    @Test
    void GivenEmptyGraph_WhenAddEdges_ConfirmDepthFirstSearchCorrect() {
        graph.addEdgeBidirectional("A", "B", 1.5);
        graph.addEdgeBidirectional("B", "C", 2.5);
        graph.addEdgeBidirectional("C", "D", 3.5);
        graph.addEdgeBidirectional("A", "D", 4.5);

        Iterator<String> iterator = graph.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("D", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void GivenEmptyGraph_WhenAddPaths_ConfirmDijkstraCorrect() {
        graph.addEdge("A", "B", 1.5);
        graph.addEdge("B", "C", 2.5);
        graph.addEdge("C", "D", 3.5);
        graph.addEdge("A", "D", 10.0);
        graph.addEdge("B", "D", 4.0);

        SaxGraph<String> result = graph.shortestPathsDijkstra("A");
        System.out.println(result.graphViz());

        assertEquals(4, result.size());
        assertEquals(0.0, result.getEdges("A").get(0).weight());
        assertEquals(1.5, result.getEdges("B").get(0).weight());
        assertEquals(4.0, result.getEdges("C").get(0).weight());
        assertEquals(5.5, result.getEdges("D").get(0).weight());
    }

    @Test
    void GivenEmptyGraph_WhenAddPaths_ConfirmAStarCorrect() {
        graph.addEdge("A", "B", 1.5);
        graph.addEdge("B", "C", 2.5);
        graph.addEdge("C", "D", 3.5);
        graph.addEdge("A", "D", 10.0);
        graph.addEdge("B", "D", 4.0);

        System.out.println(graph.graphViz());

        SaxList<SaxGraph.DirectedEdge<String>> result = graph.shortestPathAStar("A", "D", (current, target) -> 0.0);
        assertEquals(2, result.size());

        SaxGraph.DirectedEdge<String> edge1 = result.get(0);
        assertEquals("A", edge1.from());
        assertEquals("B", edge1.to());
        assertEquals(1.5, edge1.weight());

        SaxGraph.DirectedEdge<String> edge2 = result.get(1);
        assertEquals("B", edge2.from());
        assertEquals("D", edge2.to());
        assertEquals(4.0, edge2.weight());
    }

    @Test
    void GivenEmptyGraph_WhenAddPaths_ConfirmReturnsEmptyArray() {
        graph.addEdge("A", "C", 1.5);
        graph.addEdge("C", "E", 3.5);

        SaxList<SaxGraph.DirectedEdge<String>> result = graph.shortestPathAStar("A", "D", (current, target) -> 0.0);
        assertEquals(0, result.size());
    }

    @Test
    void GivenEmptyGraph_WhenAddPaths_ConfirmMCSTCorrect() {
        graph.addEdgeBidirectional("a", "b", 4);
        graph.addEdgeBidirectional("a", "h", 8);
        graph.addEdgeBidirectional("b", "h", 11);
        graph.addEdgeBidirectional("b", "c", 8);
        graph.addEdgeBidirectional("c", "i", 2);
        graph.addEdgeBidirectional("c", "f", 4);
        graph.addEdgeBidirectional("c", "d", 7);
        graph.addEdgeBidirectional("d", "e", 9);
        graph.addEdgeBidirectional("d", "f", 14);
        graph.addEdgeBidirectional("e", "f", 10);
        graph.addEdgeBidirectional("f", "g", 2);
        graph.addEdgeBidirectional("g", "h", 1);
        graph.addEdgeBidirectional("g", "i", 6);
        graph.addEdgeBidirectional("h", "i", 7);

        SaxGraph<String> result = graph.minimumCostSpanningTree();

        System.out.println(result.graphViz());
        assertEquals(37, result.getTotalWeight());
        assertEquals(9, result.size());

        assertEquals(2, result.getEdges("d").size());
        assertEquals(7.0, result.getEdges("d").get(0).weight());
        assertEquals(9.0, result.getEdges("d").get(1).weight());

        assertEquals(0, result.getEdges("e").size());

        assertEquals(2, result.getEdges("c").size());
        assertEquals(2.0, result.getEdges("c").get(0).weight());
        assertEquals(4.0, result.getEdges("c").get(1).weight());

        assertEquals(2.0, result.getEdges("f").get(0).weight());
        assertEquals(1.0, result.getEdges("g").get(0).weight());
        assertEquals(8.0, result.getEdges("h").get(0).weight());
        assertEquals(4.0, result.getEdges("a").get(0).weight());
        assertEquals(0, result.getEdges("b").size());
    }
}
