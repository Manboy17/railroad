# Algoritmen en klassen

## MyArrayList
Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyArrayList.java)

MyArryList is a custom implementation for the SaxList, SaxSearchable, SaxSortable interfaces. During the class we had a live coding session with our teacher, where he showed us the implementation of binary and linear searches. Additionally, we had a discussion about and code examples about simple and quick sorts. 

### My binary search algorithm
Big-O complexity: O(log(N))

Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyArrayList.java).

#### Explanation
I begin with a collection of sorted items, set 2 pointers: one at the beginning of the collection ans the other at the end. Then i divide the collection into 2 halves by finding the middle point. based on the comparison result i made with the target element, i move left pointer to just after the middle if the middle element is less than the target. Move the right pointer to just before the middle if the middle element is greater that the target. if the middle element is equal to the target, i return its pointion afterwards. Finally, if loop exits without finding the target, -1 will be returned. 

Based on how the algorithm works, this time complexity is due to the nature of how the algorithm work itself. It reduces the problem size by half with each iteration of the loop. Since the loop runs O(log n) times and each iteration is O(n), the overall time complexity is O(log n).

#### Tests
One part of the test method named [GivenLargeList_WhenMakingChanges_ConfirmStateRemainsCorrect](../test/collection/TestMyArrayList.java) checks for proper implemented algorithm:

- Correctness for specific elements: It verifies that the binary search correctly finds the specific element (first, last, second-to-last). For each element, it compares the value found at the retured index with the original element.
- Handling missing elements: It tests for the element that is not present in the list.

### My linear search algorithm
Big-O complexity: O(N)

Implementation: [my_implementation](../src//nl/saxion/cds/solution/MyArrayList.java)

#### Explanation
A linear search algorithm traverses the entire array from start to end using a loop. If the element at the current index is equal to the target element, then it returns the index; otherwise, it goes to the next element of the array. If the target element is not found in the array, it returns -1.

Linear search has a time complexity of O(N) because, in the worst scenario, it need to go all over the list to find the needed element or determine that the value is not present. Worth noticing that the time required grows reapidly with the size of the list. 

#### Tests
One part of the test method named [GivenLargeList_WhenMakingChanges_ConfirmStateRemainsCorrect](../test/collection/TestMyArrayList.java) checks for proper implemented algorithm:

- Correctness for specific elements: It verifies that the binary search correctly finds the specific element (first, last, second-to-last). For each element, it compares the value found at the retured index with the original element.
- Handling missing elements: It tests for the element that is not present in the list.

### My SimpleSort Algorithm
Big-O complexity: O(n^2)

Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyArrayList.java)

#### Explanation
For selection sort, the Big O time complexity is O(n²) because it involves two nested loops—one to iterate over the array and the other to find the smallest element. Selection sort works by ensuring that after each iteration, the smallest unsorted element is placed in its correct position in the list. By the end of the process, all elements are sorted. Firstly, we iterate over the list. During each iteration, another loop is created to search for the smallest element in the unsorted portion of the list. This loop compares each element with the current smallest element and then updates the smallest element index if a smaller element is found. The algorithm executes until the list is finally sorted. 

#### Tests
Test [GivenLargeList_WhenMakingChanges_ConfirmStateRemainsCorrect](../test/collection/TestMyArrayList.java) checks multiple things, but it also makes sure that simple sort works as expected. We firstly check if elements at the beggining are not sorted and then sort them using this algorithm and theck 1 more time if list is really sorted then. 


### My QuickSort algorithm
Big-O complexity: O(n log n)

Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyArrayList.java)

#### Explanation
The algorithm selects a "pivot" element from a list as a first element. The array is rearranged so that all elements less than pivot are moved to its left and all greater elements that the pivot are moved to its right. Then i have a seperate method that uses 2 pointers that traverse the list from both ends towards the center. Elements are swapped based on their comparison with the pivot until the pointers cross each other. Once we have found the correct pivot position, quick sort is then recursively called to the sub-arrays from left-to-right and right-to-left. 

With regards to the time complexity, we divide the list into 2 roughly equal halves that requires a linear time O(n).  If the pivot divides the aaray each time, the number of levels in the recursion tree will be about Log n. Consequently, each level of the recursion requires O(n) time to partition nd there are O(log n) levels on average, the total time complexity will be O(n log n).

#### Tests
Test [GivenLargeList_WhenMakingChanges_ConfirmStateRemainsCorrect](../test/collection/TestMyArrayList.java) checks for proper implemented algorithm:

The test ensures that after applying quick sort, the list is correctly sorted. 

Another test [GivenListWithIntegers_WhenQuickSorted_ThenListIsSorted](../test/collection/TestMyArrayList.java): this test ensures not only that the sorting is done correctly, it also provides a validation by checking the list if it is sorted. 

## DoubleLinkedList

Implementation: [my_implementation](../src/nl/saxion/cds/solution/DoubleLinkedList.java)

### My LinearSearch Algorithm
Big O complexity: O(n)

#### Explanation
This algorithm iterates over all nodes, starting from the head and moving towards the tail. Each iteration takes O(1), but since you mau need to loop over all n nodes, the overall time complexity is O(n). Worst cases, where element is not present or this is a tail. Firsly, we loop through each node and then check if the current node's value matches the element we are looking for. If it is equal - return its position, it not - move to the next node. 

#### Tests
Test [GivenLargeList_WhenMakingChanges_ConfirmStateRemainsCorrect](../test/collection/TestMyDoubleLinkedList.java) searches for an element using linear search. We get the first element from created list, then get the position of its element using linear search and compare the values. 

## My BST
Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyBST.java). Worth saying that our teacher showed us how to implement add, get, getKeys, graphViz methods. 

#### Explanation
The MyBST class implements a generic Binary Search Tree (BST) with nodes that hold key-value pairs. The class supports standard BST operations such as adding, removing, searching, and retrieving all keys, and provides visualization of the tree structure in GraphViz format. 

In the absolute worst case, a binary tree with N elements would be like a linked list.
Hence, there would be N levels, and a search would take N traversals. That's why it's O(N) in the worst case. And this is why we need to balance the trees to achieve O(log N) search. ([reference](https://medium.com/@samip.sharma963/binary-search-tree-and-its-big-o-f75eef7a985))

#### Tests
All tests can be found [here](../test/collection/TestMyBST.java). Overall some tests overview:

[GivenTree_WhenCheckingContains_ConfirmCorrectResults](../test/collection/TestMyBST.java) : It confirms that the method correctly identifies the presence of keys in a populated tree.

[GivenTree_WhenGettingElements_ConfirmCorrectResults](../test/collection/TestMyBST.java) : This ensures that retrieval works accurately.

[GivenTree_WhenMakeChanges_ConfirmExceptionsAreThrown](../test/collection/TestMyBST.java) : This test verifies that error handling works correctly. 

## My HashMap
Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyHashMap.java). Worth saying that our teacher showed us how to implement add, get, remove, getKeys, graphViz, toString methods.

#### Explanation
The Bucket inner class is a simple container for storing key-value pairs. Each bucket can hold one key-value pair or be null. To create a custom HashMap, i have used MyArrayList implemented earlier due to its dynamic resizing. To implement it, i have used linear probing known for its technic to handle collisions in hash tables. When 2 or more keys hash to the same index, linear probing finds the next available slot in a sequential order. This is done by using this code:
```
int hashCode = key.hashCode();
int index = Math.abs(hashCode) % buckets.size();
```
If the calculated index is occupied, it will search for the next open slot:
```
i = (i + 1) % buckets.size();
```

The efficiency of the <b>"contains"</b> method depends on how well the hash function distributes keys across buckets. We have 2 cases:

Average case, where most keys will be in different buckets. Time complexity is O(1).
Worst case, where many keys collide, so the method may acheck several buckets one after the other. Time complexity is O(n).

#### Tests
To prove that all implemented methods work as expected, the [following tests](../test/collection/TestMyHashMap.java) have been written

[GivenEmptyHashMap_WhenAddingValues_ConfirmTheResultsAreCorrect](../test/collection/TestMyHashMap.java) test checks if adding buckets work properly. We check map size, the value by its key, throw an exception if capacity is exceeded.
[GivenHashMap_WhenRemovingValues_ConfirmTheResultsAreCorrect](../test/collection/TestMyHashMap.java) test checkes if removing a bucket works properly, map size changes accordingly, throw exception for invalid key. 

Additional tests cover operations such as retrieving all keys and generating a graphViz representation.


## My MinHeap
Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyMinHeap.java). Worth saying that our teacher showed us how to implement enqueue, dequeue, toString methods. 

#### Explanation
The minHeap is a type of data structure where the root element is always smaller than its child elements. This is done by 2 helper methods: percolateUp and percolateDown. PercolateUp method, which percolates a new item as far up in the tree as it needs to go to maintain the heap property. The percolateDown method ensures that the largest child is always moved down the tree. Because the heap is a complete binary tree, any nodes past the halfway point will be leaves and therefore have no children ([resource](https://runestone.academy/ns/books/published/javads/trees_binary-heap-implementation.html)). For my implementation i have used a custom MyArrayList due to its generic and it can be easity grown in size. 

#### Tests

All tests can be found [here](../test/collection/TestMyMinHeap.java). Overall, i check if all methods work as expected and throw EmptyCollectionException correctly. 

[GivenNewHeap_WhenAddingExampleData_ConfirmSize](../test/collection/TestMyMinHeap.java) test ensures that enqueue method works correctly together with a toString method. We created a new heap and added some integer values. By the end we test the proper order of integers. 

[GivenNewHeap_WhenRemovingExampleData_ConfirmSizeAndStructure](../test/collection/TestMyMinHeap.java) test ensures that dequeue works correctly together with a toString method. We created a new heap and added some integer values. We removed the root element and test the proper order of integers. 

[GivenNewHeap_WhenMakeChanges_ConfirmExceptionsAreThrown](../test/collection/TestMyMinHeap.java) test ensures that exceptions are thrown where needed. 

### My Queue
Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyQueue.java)

#### Explanation
The MyQueue class is a queue implementation using a custom DoubleLinkedList to store its elements. It implements the SaxQueue interface, providing standard queue operations like enqueue, dequeue, peek.  The main concept of this data structure is "First In, First Out", meaning that the first element added to the queue will be the first one to be removed. 

Using DoubleLinkedList is beneficial as it allows to:
- Provide efficient O(1) time complexity for both enqueue and dequeue operations
- Avoid element shifting and dynamically manage array size

#### Tests
All tests can be found [here](../test/collection/MyQueueTest.java). Overall, i check if all methods work as expected and throw "EmptyCollectionException" exception if queue is empty. 


[WhenGivenQueue_WhileMakingChanges_ConfirmResultsAreCorrect](../test/collection/TestMyQueue.java) test ensures that elements are dequeued in the correct order and that peeking at the front of the queue correctly reflects the current state of the queue.

[WhenGivenEmptyQueue_ConfirmItThrowsAnException](../test/collection/TestMyQueue.java) test ensures that the queue correctly handles operations when it is empty by throwing the appropriate exceptions



### My Stack
Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyStack.java)

#### Explanation
The MyStack class is a stack implementation using a custom DoubleLinkedList to store its elements. It implements the SaxStack interface, providing standard stack operations like push, pop, and peek. The main concept of this data structure is "Last In, First Out", meaning that the most recently added element is the first one to be removed. 

Using DoubleLinkedList is beneficial as allows to:
- Perform frequent insertions and deletions without worrying about resizing overhead.
- Avoid the performance hit of dynamic resizing present in ArrayList.

#### Tests
All tests can be found [here](../test/collection/MyStackTest.java). Overall, i check if all methods work as expected and throw "EmptyCollectionException" exception if stack is empty. 

[GivenEmptyStack_WhenPushingAndPoppingElements_ConfirmChangesAreCorrect](../test/collection/TestMyStack.java) test ensures that pushing and popping elements to and from the stack changes the stack's size and content as expected. It also confirms that peeking reflects the correct top element without affecting the stack size.

[GivenEmptyStack_WhenPeekingElement_ConfirmItThrowsException](../test/collection/TestMyStack.java) test ensures that the stack correctly handles the peek operation by throwing the appropriate exception when the stack is empty.

## My Graph
Implementation:

### My iterative depth first search algorithms
Classificatie:

Implementation:

### My iterative breadth first search algorithm
Classificatie:

Implementation:

### My Dijkstra algorithm
Classification:

Implementation:

### My A* algorithm
Classification:

Implementation:

### My MCST algorithm  
Classification:

Implementation:

# Technical design My Application

## Class diagram and reading the data

# Station search by station code

# Station search based on the beginning of the name

## Implementation shortest route

## Implementation minimum cost spanning tree 

## Implementation graphic representation(s)