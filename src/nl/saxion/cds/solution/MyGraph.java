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

    @Override
    public void addEdgeBidirectional(V fromValue, V toValue, double weight) {
        addEdge(fromValue, toValue, weight);
        addEdge(toValue, fromValue, weight);
    }

    @Override
    public MyArrayList<SaxGraph.DirectedEdge<V>> getEdges(V value) {
        if (nodes == null || !nodes.contains(value)) {
            return new MyArrayList<>();
        }

        return nodes.get(value);
    }

    @Override
    public double getTotalWeight() {
        double result = 0.0;

        for (Object node : nodes.getKeys()) {
            for (DirectedEdge<V> edge : nodes.get((V) node)) {
                result += edge.weight();
            }
        }

        return result;
    }

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

    @Override
    public SaxGraph<V> minimumCostSpanningTree() {
        MyGraph<V> result = new MyGraph<>(nodes.size());
        MyMinHeap<DirectedEdge<V>> queue = new MyMinHeap<>();
        MyHashMap<V, Boolean> visited = new MyHashMap<>(nodes.size());

        V startNode = (V) nodes.getKeys().get(0);
        System.out.println("Start node: " + startNode);
        visited.add(startNode, true);

        for (DirectedEdge<V> neighbourEdge : getEdges(startNode)) {
            queue.enqueue(neighbourEdge);
        }

        while (!queue.isEmpty()) {
            DirectedEdge<V> edge = queue.dequeue();
            System.out.println(edge);

            if (!visited.contains(edge.to())) {
                result.addEdge(edge.from(), edge.to(), edge.weight());
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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

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
