package nl.saxion.cds.solution;

import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;

import java.util.Iterator;

public class MyGraph<V> implements SaxGraph<V> {
    private MyHashMap<V, MyArrayList<DirectedEdge<V>>> nodes;
    private int size = 0;

    public MyGraph(int capacity) {
        nodes = new MyHashMap<>(capacity);
    }

    /**
     * Add an edge between two nodes
     * @param fromValue originating node value
     * @param toValue   connected node value
     * @param weight   weight of the edge
     * @throws KeyNotFoundException
     */
    @Override
    public void addEdge(V fromValue, V toValue, double weight) throws KeyNotFoundException {
        if (!nodes.contains(fromValue)) {
            nodes.add(fromValue, new MyArrayList<>());
            size++;
        }

        if (!nodes.contains(toValue)) {
            nodes.add(toValue, new MyArrayList<>());
            size++;
        }

        DirectedEdge<V> edge = new DirectedEdge<>(fromValue, toValue, weight);
        nodes.get(fromValue).addLast(edge);
    }

    /**
     * Add an edge between two nodes in both directions
     * @param fromValue originating node value
     * @param toValue  connected node value
     * @param weight  weight of the edge
     */
    @Override
    public void addEdgeBidirectional(V fromValue, V toValue, double weight) {
        addEdge(fromValue, toValue, weight);
        addEdge(toValue, fromValue, weight);
    }

    /**
     * Get all edges originating from a node
     * @param value the value of the node the edges originate from
     * @return a list of edges originating from the node
     */
    @Override
    public MyArrayList<SaxGraph.DirectedEdge<V>> getEdges(V value) {
        if (nodes == null || !nodes.contains(value)) {
            return new MyArrayList<>();
        }

        return nodes.get(value);
    }

    /**
     * Get the total weight of the graph
     * @return the total weight of the graph
     */
    @Override
    public double getTotalWeight() {
        double result = 0.0;
        MyHashMap<V, MyHashMap<V, Boolean>> visitedEdges = new MyHashMap<>(nodes.size());

        for (Object node : nodes.getKeys()) {
            for (DirectedEdge<V> edge : nodes.get((V) node)) {
                V from = edge.from();
                V to = edge.to();

                if (!visitedEdges.contains(from)) {
                    visitedEdges.add(from, new MyHashMap<>(nodes.size()));
                }

                if (!visitedEdges.get(from).contains(to)) {
                    result += edge.weight();
                    visitedEdges.get(from).add(to, true);

                    if (!visitedEdges.contains(to)) {
                        visitedEdges.add(to, new MyHashMap<>(nodes.size()));
                    }
                    visitedEdges.get(to).add(from, true);
                }
            }
        }

        return result;
    }

    /**
     * Find the shortest path between two nodes using Dijkstra's algorithm
     * @param startNode the node to start searching from
     * @return a graph containing the shortest paths
     */
    @Override
    public SaxGraph<V> shortestPathsDijkstra(V startNode) {
        MyGraph<V> result = new MyGraph<>(nodes.size());
        MyHashMap<V, Boolean> visited = new MyHashMap<>(nodes.size());
        MyMinHeap<DirectedEdge<V>> queue = new MyMinHeap<>();

        queue.enqueue(new DirectedEdge<>(startNode, startNode, 0));

        while (!queue.isEmpty()) {
            DirectedEdge<V> edge = queue.dequeue();
            if (!visited.contains(edge.from())) {
                visited.add(edge.from(), true);
                result.addEdge(edge.from(), edge.to(), edge.weight());

                for (DirectedEdge<V> neighbourEdge : getEdges(edge.from())) {
                    if (!visited.contains(neighbourEdge.to())) {
                        double newWeight = neighbourEdge.weight() + edge.weight();

                        queue.enqueue(new DirectedEdge<>(neighbourEdge.to(), neighbourEdge.from(), newWeight));
                    }
                }
            }
        }
        return result;
    }

    /**
     * Find the shortest path between two nodes using A* algorithm
     * @param startNode the node to start searching
     * @param endNode   the target node
     * @param estimator a (handler) function to estimate the distance (weight) between two nodes
     * @return a list of edges representing the shortest path
     */
    @Override
    public SaxList<SaxGraph.DirectedEdge<V>> shortestPathAStar(V startNode, V endNode, SaxGraph.Estimator<V> estimator) {
        MyMinHeap<AStarNode<V>> openList = new MyMinHeap<>();
        MyHashMap<V, AStarNode<V>> closedList = new MyHashMap<>(nodes.size());

        AStarNode<V> start = new AStarNode<>(null, 0, estimator.estimate(startNode, endNode), null);
        openList.enqueue(start);

        while (!openList.isEmpty()) {
            AStarNode<V> currentNode = openList.dequeue();

            V curNodeVal = currentNode.edge == null ? startNode : currentNode.edge.to();

            // found the goal
            if (curNodeVal.equals(endNode)) {
                closedList.add(curNodeVal, currentNode);
                return reconstructPath(currentNode);
            }

            // have not been there yet
            if (!closedList.contains(curNodeVal)) {
                closedList.add(curNodeVal, currentNode);

                for (DirectedEdge<V> neighbourEdge : getEdges(curNodeVal)) {

                    if (closedList.contains(neighbourEdge.to())) continue;

                    double neighbourG = currentNode.g + neighbourEdge.weight();
                    double neighbourH = estimator.estimate(curNodeVal, neighbourEdge.to());
                    AStarNode<V> neighbourNode = new AStarNode<>(neighbourEdge, neighbourG, neighbourH, currentNode);

                    openList.enqueue(neighbourNode);
                }
            }
        }
        return new MyArrayList<>();
    }

    /**
     * Reconstruct the path from the start node to the current node
     * @param currentNode the current node
     * @return a list of edges representing the path
     */
    private SaxList<SaxGraph.DirectedEdge<V>> reconstructPath(AStarNode<V> currentNode) {
        MyArrayList<DirectedEdge<V>> result = new MyArrayList<>();
        while (currentNode != null) {
            if (currentNode.edge != null) {
                result.addFirst(currentNode.edge);
            }
            currentNode = currentNode.parent;
        }
        return result;
    }

    /**
     * A node for the A* algorithm
     * @param <V>
     */
    private static class AStarNode<V> implements Comparable<AStarNode<V>> {
        DirectedEdge<V> edge;
        double g;
        double h;
        AStarNode<V> parent;

        public AStarNode(DirectedEdge<V> edge, double g, double h, AStarNode<V> parent) {
            this.edge = edge;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        @Override
        public int compareTo(AStarNode<V> o) {
            return Double.compare(this.g + this.h, o.g + o.h);
        }
    }

    /**
     * Find the minimum cost spanning tree using the Kruskal algorithm
     * @return a graph containing the minimum cost spanning tree
     */
    @Override
    public SaxGraph<V> minimumCostSpanningTree() {
        MyGraph<V> result = new MyGraph<>(nodes.size());
        MyMinHeap<DirectedEdge<V>> queue = new MyMinHeap<>();
        MyHashMap<V, Boolean> visited = new MyHashMap<>(nodes.size());

        V startNode = (V) nodes.getKeys().get(0);
        visited.add(startNode, true);

        for (DirectedEdge<V> neighbourEdge : getEdges(startNode)) {
            queue.enqueue(neighbourEdge);
        }

        while (!queue.isEmpty()) {
            DirectedEdge<V> edge = queue.dequeue();

            if (!visited.contains(edge.to())) {
                result.addEdgeBidirectional(edge.from(), edge.to(), edge.weight());
                visited.add(edge.to(), true);

                for (DirectedEdge<V> neighbourEdge : getEdges(edge.to())) {
                    if (!visited.contains(neighbourEdge.to())) {
                        queue.enqueue(neighbourEdge);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Get an iterator for the graph
     * @return an iterator for the graph
     */
    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            MyHashMap<V, Boolean> visited = new MyHashMap<>(nodes.size());
            MyStack<V> stack = new MyStack<>();
            V nextValue = null;

            {
                V startNode = (V) nodes.getKeys().get(0);
                stack.push(startNode);
            }

            @Override
            public boolean hasNext() {
                while (nextValue == null && !stack.isEmpty()) {
                    V cur = stack.pop();
                    if (!visited.contains(cur)) {
                        visited.add(cur, true);
                        nextValue = cur;

                        MyArrayList<DirectedEdge<V>> neighbourEdges = nodes.get(cur);
                        if (neighbourEdges != null) {
                            for (int i = neighbourEdges.size() - 1; i >= 0; i--) {
                                DirectedEdge<V> edge = neighbourEdges.get(i);
                                V neighbour = edge.to();
                                if (!visited.contains(neighbour)) {
                                    stack.push(neighbour);
                                }
                            }
                        }
                    }
                }
                return nextValue != null;
            }

            @Override
            public V next() {
                V tempV = nextValue;
                if (!hasNext()) {
                    throw new KeyNotFoundException("No more elements");
                }

                nextValue = null;
                return tempV;
            }
        };
    }

    /**
     * Check if the graph is empty
     * @return true if the graph is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the size of the graph
     * @return the size of the graph
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Get a string representation of the graph
     * @param name name of the produced graph
     * @return a string representation of the graph
     */
    @Override
    public String graphViz(String name) {
        StringBuilder builder = new StringBuilder("digraph " + name + "{\n");

        for (Object node : nodes.getKeys()) {
            builder.append(node);
            builder.append(";\n");
            for (DirectedEdge<V> edge : nodes.get((V) node)) {
                builder.append(node);
                builder.append(" -> ");
                builder.append(edge.to());
                builder.append("[label=\"");
                builder.append(edge.weight());
                builder.append("\"];\n");
            }
        }

        builder.append("}\n");
        return builder.toString();
    }
}
