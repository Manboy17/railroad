package nl.saxion.cds.solution;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxBinaryTree;
import nl.saxion.cds.collection.SaxList;

public class MyBST<K extends Comparable<K>, V> implements SaxBinaryTree<K, V> {
    private Node root = null;
    private int size = 0;

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void add(K key, V value) throws DuplicateKeyException {
            int compare = this.key.compareTo(key);
            if(compare == 0) {
                throw new DuplicateKeyException(key.toString());
            }
            if(compare < 0) {
                if(right == null) {
                    right = new Node(key, value);
                } else {
                    right.add(key, value);
                }
            } else {
                if(left == null) {
                    left = new Node(key, value);
                } else {
                    left.add(key, value);
                }
            }
        }

        public V get(K key) {
            int compare = this.key.compareTo(key);
            if(compare == 0) {
                return value;
            }
            if(compare < 0) {
                if(right != null) {
                    return right.get(key);
                }
            } else {
                if(left != null) {
                    return left.get(key);
                }
            }
            return null;
        }

        public void graphViz(StringBuilder builder) {
            builder.append(key.toString() + "[label=\"" + key.toString() + "\\n" + value.toString() + "\"]\n");
            if(left != null) {
                builder.append(key.toString() + " -> " + left.key.toString() + "\n");
                left.graphViz(builder);
            }
            if(right != null) {
                builder.append(key.toString() + " -> " + right.key.toString() + "\n");
                right.graphViz(builder);
            }
        }
    }

    @Override
    public boolean contains(K key) {
        if(root == null) {
            return false;
        }

        return root.get(key) != null;
    }

    @Override
    public V get(K key) {
        if(root == null) {
            return null;
        }

        return root.get(key);
    }

    @Override
    public void add(K key, V value) throws DuplicateKeyException {
        if(root == null) {
            root = new Node(key, value);
        } else {
            root.add(key, value);
        }
        size++;
    }

    @Override
    public V remove(K key) throws KeyNotFoundException {
        if (root == null || !contains(key)) {
            throw new KeyNotFoundException(key.toString());
        }
        V value = get(key);
        if(value != null) {
            root = remove(root, key);
            size--;
        }

        return value;
    }

    /**
     * Used this information to implement remove method
     * https://cs.brynmawr.edu/Courses/cs206/spring2012/slides/08_BinaryTrees.pdf (remove operation part)
     * @param node
     * @param key
     * @return
     * @throws KeyNotFoundException
     */
    private Node remove(Node node, K key) throws KeyNotFoundException {
        int compare = key.compareTo(node.key);
        if(compare < 0) {
            node.left = remove(node.left, key);
        } else if(compare > 0) {
            node.right = remove(node.right, key);
        } else {
            if(node.left != null && node.right != null) {
                Node minNode = findMin(node.right);
                node.key = minNode.key;
                node.value = minNode.value;
                node.right = remove(node.right, minNode.key);
            } else {
                node = (node.left != null) ? node.left : node.right;
            }
        }

        return node;
    }

    private Node findMin(Node node) {
        while(node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public SaxList<K> getKeys() {
        MyArrayList<K> keys = new MyArrayList<>();
        getKeys(root, keys);
        return keys;
    }

    private void getKeys(Node node, MyArrayList<K> keys) {
        if(node != null) {
            getKeys(node.left, keys);
            keys.addLast(node.key);
            getKeys(node.right, keys);
        }
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
        StringBuilder builder = new StringBuilder("digraph " + name + "{");
        if(root != null) {
            root.graphViz(builder);
        }
        builder.append("}");
        return builder.toString();
    }
}
