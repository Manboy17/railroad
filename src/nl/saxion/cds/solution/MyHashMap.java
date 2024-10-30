package nl.saxion.cds.solution;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxHashMap;
import nl.saxion.cds.collection.SaxList;

public class MyHashMap<K, V> implements SaxHashMap<K, V> {

    private class Bucket {
        private K key;
        private V value;

        private Bucket(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

    private MyArrayList<Bucket> buckets;
    private int size = 0;

    public MyHashMap(int capacity) {
        buckets = new MyArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            buckets.addLast(new Bucket(null, null));
        }
    }

    /**
     * Checks if the hashmap is empty.
     *
     * @return true if the hashmap is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the hashmap.
     *
     * @return the size of the hashmap.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the index of the bucket which corresponds to the key
     *
     * @param key key to look for
     * @return the index of the bucket
     */
    private int getIndex(K key) {
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode) % buckets.size();

        if (index < 0) {
            index = 0;
        }
        return index;
    }

    /**
     * return the bucket which corresponds to the key
     *
     * @param key key to look for
     * @return the bucket with the key or null if not found
     */
    private Bucket getBucket(K key) {
        int index = getIndex(key);
        assert index >= 0 && index < buckets.size();
        int i = index;
        while (true) {
            Bucket bucket = buckets.get(i);
            if (bucket.key == null) {
                return null;
            }
            if (bucket.key.equals(key)) {
                return bucket;
            }
            i = (i + 1) % buckets.size();
            if (i == index) {
                return null;
            }
        }
    }

    /**
     * Checks if the hashmap contains a key
     *
     * @param key key to search for
     * @return true if the key is found, false otherwise
     */
    @Override
    public boolean contains(K key) {
        return getBucket(key) != null;
    }

    /**
     * Returns the value which corresponds to the key
     *
     * @param key key which is mapped to value to be found
     * @return the value which corresponds to the key
     */
    @Override
    public V get(K key) {
        Bucket bucket = getBucket(key);
        if (bucket == null) {
            return null;
        }

        return bucket.value;
    }

    /**
     * Adds a key-value pair to the hashmap
     *
     * @param key   key which is mapped to value
     * @param value the value to add
     * @throws DuplicateKeyException if the key already exists
     * @throws OutOfMemoryError      if the hashmap is full
     */
    @Override
    public void add(K key, V value) throws DuplicateKeyException, OutOfMemoryError {
        if (contains(key)) {
            throw new DuplicateKeyException("Key already exists!");
        }

        int index = getIndex(key);
        assert index >= 0 && index < buckets.size();
        int i = index;
        while (true) {
            Bucket bucket = buckets.get(i);
            if (bucket == null || bucket.key == null) {
                buckets.set(i, new Bucket(key, value));
                size++;
                break;
            }
            i = (i + 1) % buckets.size();
            if (i == index) {
                throw new OutOfMemoryError("MyHashMap is full!");
            }
        }
    }

    /**
     * Removes a key-value pair from the hashmap
     *
     * @param key key which is mapped to value
     * @return the value which corresponds to the key
     * @throws KeyNotFoundException if the key was not found
     */
    @Override
    public V remove(K key) throws KeyNotFoundException {
        Bucket bucket = getBucket(key);
        if (bucket == null) {
            throw new KeyNotFoundException("Key was not found!");
        }

        bucket.key = null;
        V value = bucket.value;
        bucket.value = null;
        size--;
        return value;
    }

    /**
     * Returns a list of keys in the hashmap
     * @return a list of keys in the hashmap
     */
    @Override
    public SaxList getKeys() {
        MyArrayList list = new MyArrayList(size);
        for (var bucket : buckets) {
            if (bucket != null && bucket.key != null) {
                list.addLast(bucket.key);
            }
        }

        return list;
    }

    /**
     * Returns the graphviz representation of the hashmap.
     * @param name name of the produced graph
     * @return the graphviz representation of the hashmap.
     */
    @Override
    public String graphViz(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("digraph " + name + " {\n");
        int index = 0;
        for (var bucket : buckets) {
            if (bucket != null) {
                builder.append("bucket_");
                builder.append(index);
                builder.append("[label=\"");
                if (bucket == null) {
                    builder.append("NULL");
                } else {
                    builder.append(index);
                    builder.append(":");
                    builder.append(bucket.key);
                    builder.append(",");
                    builder.append(bucket.value);
                }
                builder.append("\"]\n");
                if (index < buckets.size() - 1) {
                    builder.append("bucket_");
                    builder.append(index);
                    builder.append(" -> bucket_");
                    builder.append(index + 1);
                }
                builder.append("\n");
            }
            index++;
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * Returns a string representation of the hashmap.
     * @return a string representation of the hashmap.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        int index = 0;
        for (var bucket : buckets) {
            if (bucket != null && bucket.key != null) {
                builder.append("(");
                builder.append(index);
                builder.append(":");
                builder.append(bucket.key);
                builder.append(",");
                builder.append(bucket.value);
                builder.append(")");
            }
            index++;
        }
        builder.append("}");
        return builder.toString();
    }
}
