# Algoritmen en klassen

## MijnArrayList
Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyArrayList.java)

### My binary search algorithm
Big-O complexity: O(Log(N))

Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyArrayList.java)

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


### My QuickSort algorithm
Big-O complexity: O(n log n)

Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyArrayList.java)

#### Explanation
The algorithm selects a "pivot" element from a list as a first element. The array is rearranged so that all elements less than pivot are moved to its left and all greater elements that the pivot are moved to its right. Then i have a seperate method that uses 2 pointers that traverse the list from both ends towards the center. Elements are swapped based on their comparison with the pivot until the pointers cross each other. Once we have found the correct pivot position, quick sort is then recursively called to the sub-arrays from left-to-right and right-to-left. 

With regards to the time complexity, we divide the list into 2 roughly equal halves that requires a linear time O(n).  If the pivot divides the aaray each time, the number of levels in the recursion tree will be about Log n. Consequently, each level of the recursion requires O(n) time to partition nd there are O(log n) levels on average, the total time complexity will be O(n log n).

#### Tests
One part of the test method named [GivenLargeList_WhenMakingChanges_ConfirmStateRemainsCorrect](../test/collection/TestMyArrayList.java) checks for proper implemented algorithm:

The test ensures that after applying quick sort, the list is correctly sorted. 

Another test moethod named [GivenListWithIntegers_WhenQuickSorted_ThenListIsSorted](../test/collection/TestMyArrayList.java): this test ensures not only that the sorting is done correctly, it also provides a validation by checking the list if it is sorted. 

## My BST
Implementation:

## My HashMap
Implementation:

## My MinHeap
Implementation:

### My Queue
Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyQueue.java)

#### Explanation
The MyQueue class is a queue implementation using a custom MyArrayList to store its elements. It implements the SaxQueue interface, providing standard queue operations like enqueue, dequeue, peek.  The main concept of this data structure is "First In, First Out", meaning that the first element added to the queue will be the first one to be removed. 

#### Tests
All tests can be found [here](../test/collection/MyQueueTest.java). Overall, i check if all methods work as expected and throw "EmptyCollectionException" exception if queue is empty. 


"WhenGivenQueue_WhileMakingChanges_ConfirmResultsAreCorrect" test ensures that elements are dequeued in the correct order and that peeking at the front of the queue correctly reflects the current state of the queue.

"WhenGivenEmptyQueue_ConfirmItThrowsAnException" test ensures that the queue correctly handles operations when it is empty by throwing the appropriate exceptions



### My Stack
Implementation: [my_implementation](../src/nl/saxion/cds/solution/MyStack.java)

#### Explanation
The MyStack class is a stack implementation using a custom MyArrayList to store its elements. It implements the SaxStack interface, providing standard stack operations like push, pop, and peek. The main concept of this data structure is "Last In, First Out", meaning that the most recently added element is the first one to be removed.

#### Tests
All tests can be found [here](../test/collection/MyStackTest.java). Overall, i check if all methods work as expected and throw "EmptyCollectionException" exception if stack is empty. 

"GivenEmptyStack_WhenPushingAndPoppingElements_ConfirmChangesAreCorrect" test ensures that pushing and popping elements to and from the stack changes the stack's size and content as expected. It also confirms that peeking reflects the correct top element without affecting the stack size.

"GivenEmptyStack_WhenPeekingElement_ConfirmItThrowsException" test ensures that the stack correctly handles the peek operation by throwing the appropriate exception when the stack is empty.

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