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

        /**
         * Constructor for Node
         * @param key key
         * @param value value
         */
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Add a new key value pair to the tree
         * @param key key
         * @param value value
         * @throws DuplicateKeyException if the key already exists
         */
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

        /**
         * Get the value of a key
         * @param key key
         * @return value of the key
         */
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

        /**
         * Graphviz's representation of the tree
         * @param builder StringBuilder to append the graphviz representation
         */
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

    /**
     * Check if the tree contains a key
     * @param key key to search for
     * @return true if the key is in the tree, false otherwise
     */
    @Override
    public boolean contains(K key) {
        if(root == null) {
            return false;
        }

        return root.get(key) != null;
    }

    /**
     * Get the value of a key
     * @param key key which is mapped to value to be found
     * @return the value which corresponds to the key
     */
    @Override
    public V get(K key) {
        if(root == null) {
            return null;
        }

        return root.get(key);
    }

    /**
     * Add a new key value pair to the tree
     * @param key   key which is mapped to value
     * @param value the value to add
     * @throws DuplicateKeyException if the key already exists
     */
    @Override
    public void add(K key, V value) throws DuplicateKeyException {
        if(root == null) {
            root = new Node(key, value);
        } else {
            root.add(key, value);
        }
        size++;
    }

    /**
     * Remove a key value pair from the tree
     * @param key key which is mapped to value
     * @return the value which corresponds to the key
     * @throws KeyNotFoundException if the key is not found
     */
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
     * @param node root node
     * @param key key to remove
     * @return new root node
     * @throws KeyNotFoundException if the key is not found
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

    /**
     * Find the minimum node in the tree
     * @param node root node
     * @return minimum node
     */
    private Node findMin(Node node) {
        while(node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Get all keys in the tree
     * @return a list of keys in the tree
     */
    @Override
    public SaxList<K> getKeys() {
        MyArrayList<K> keys = new MyArrayList<>();
        getKeys(root, keys);
        return keys;
    }

    /**
     * Get all keys in the tree
     * @param node root node
     * @param keys list of keys
     */
    private void getKeys(Node node, MyArrayList<K> keys) {
        if(node != null) {
            getKeys(node.left, keys);
            keys.addLast(node.key);
            getKeys(node.right, keys);
        }
    }

    /**
     * Check if the tree is empty
     * @return true if the tree is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the size of the tree
     * @return the size of the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Get the graphviz representation of the tree
     * @param name name of the produced graph
     * @return the graphviz representation of the tree
     */
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
