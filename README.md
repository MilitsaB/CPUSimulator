# CPUSimulator
Program that simulates a single threaded CPU by executing processes on a computer system.

-> Version 1 is implemented using a sorted Priority Queue ADT
-> Version 2 is implemented using a heap based Priority Queue ADT
Time complexity and space complexity were compared for both programs in order to determine which Priority Queue ADT has a better performance.
Heap priority queue performed much better as its complexity is O(n log n).

Note that a starvation avoidance algorithm is used in order to allow the low priority processes are reassigned priorities on a scheduled basis
