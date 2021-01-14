package com.company;
import java.util.ArrayList;

public class HeapPriorityQueue {

    protected ArrayList<Job> heap = new ArrayList<Job>();
    static int currentTime = 0;
    static int nbExecuted = 0;
    static int nbPriorityChanges= 0;
    static long totalWaitTime = 0;
    static boolean justStarved= false;

    public void print(){
        for (Job object: heap) {
            System.out.println(object);
        }
    }

    public HeapPriorityQueue() {
        super();
    }

    protected int parent(int j) {
        return (j - 1) / 2;
    }

    protected int left(int j) {
        return 2 * j + 1;
    }

    protected int right(int j) {
        return 2 * j + 2;
    }

    protected boolean hasLeft(int j) {
        return left(j) < heap.size();
    }

    protected boolean hasRight(int j) {
        return right(j) < heap.size();
    }

    protected void swap(int i, int j) {
        Job temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    protected void upheap(int j) {
        while (j > 0) {
            int p = parent(j);
            if (heap.get(j).getCurrentPriority() >= heap.get(p).getCurrentPriority())
                break;
            swap(j, p);
            j = p;
        }
    }


    protected void downheap(int j) {
        while (hasLeft(j)) {
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;
            if (hasRight(j)) {
                int rightIndex = right(j);
                if (heap.get(leftIndex).getCurrentPriority() > heap.get(rightIndex).getCurrentPriority() )
                    smallChildIndex = rightIndex;
                if (heap.get(leftIndex).getCurrentPriority() == heap.get(rightIndex).getCurrentPriority()
                        && heap.get(leftIndex).getRealEntryTime() > heap.get(rightIndex).getRealEntryTime())
                    smallChildIndex = rightIndex;
            }
            if (heap.get(smallChildIndex).getCurrentPriority()> heap.get(j).getCurrentPriority())
                break;
            if (heap.get(smallChildIndex).getCurrentPriority() == heap.get(j).getCurrentPriority()) {
                if (heap.get(smallChildIndex).getRealEntryTime() > heap.get(j).getRealEntryTime())
                    break;
            }
            swap(j, smallChildIndex);
            j = smallChildIndex;
        }
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty(){
        if (heap.isEmpty())
            return true;
        else
            return false;
    }

    public Job min() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    public Job insert(Job newest) throws IllegalArgumentException {
        newest.setJobName(currentTime);
        newest.setEntryTime(currentTime);
        newest.setRealEntryTime(currentTime);
        currentTime++;
        heap.add(newest);
        upheap(heap.size() - 1);
        return newest;
    }

    public Job insert1(Job newest) throws IllegalArgumentException {
        currentTime++;
        newest.setRealEntryTime(currentTime);
        heap.add(newest);
        upheap(heap.size() - 1);
        downheap(0);
        return newest;
    }

    public Job processQueue() {
        if (heap.isEmpty())
            return null;

        if (!justStarved && nbExecuted%3 == 0 && nbExecuted != 0){ //checks if any jobs are being starved
             starved();
             System.out.println("-------------------");
             System.out.println("New Priority given to " + heap.get(0).getJobName() );
        }

        Job answer = heap.get(0);
        swap(0, heap.size() - 1);
        int length = heap.get(heap.size() - 1).getJobLength();

        if (length > 1) {
            heap.get(heap.size() - 1).setJobLength(length - 1);
            Job temp = heap.get(heap.size() - 1);
            System.out.println("-------------------");
            System.out.println(temp);

            heap.remove(heap.size() - 1);

            downheap(0);
            insert1(temp);
        }
        //last cycle to execute for this job
        if (length == 1){
            currentTime++;
            //set End Time
            heap.get(heap.size() - 1).setEndTime(currentTime);
            //set Wait Time
            long waitTime = currentTime - heap.get(heap.size() - 1).getEntryTime()
                    - heap.get(heap.size() - 1).getOriginalJobLength();

            totalWaitTime = totalWaitTime + waitTime;
            heap.get(heap.size() - 1).setWaitTime(waitTime);

            System.out.println("-------------------");
            System.out.println(heap.get(heap.size() - 1).executionPrint());

            heap.remove(heap.size() - 1);
            downheap(0);
            nbExecuted++;
            justStarved = false;
        }
        return answer;
    }

    private void starved(){
        Job smallest = heap.get(0);
        for (Job object: heap) {
            if(smallest.getEntryTime() > object.getEntryTime())
                smallest = object;
        }

        //set priority to 1
        smallest.setCurrentPriority(1);
        justStarved = true;
        nbPriorityChanges++;

        //remove starved Job and Insert it again
        heap.remove(smallest);
        insert1(smallest);
    }

}