package com.company;
public class Main {

    public static void main(String[] args) {

        System.out.println("||<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<||\n"
                + "       ||      CPU SIMULATOR Version 1      ||\n"
                + "||>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>||\n");


        int MaxNumberOfJobs = 5;
        Job[] jobsInputArray = new Job[MaxNumberOfJobs];
        SortedPriorityQueue q = new SortedPriorityQueue();

        final double start = System.nanoTime();
        for(int i = 0; i < MaxNumberOfJobs; i++){
            jobsInputArray[i] = new Job();
            q.insert(jobsInputArray[i]);
        }

        while (!q.isEmpty()){
            q.processQueue();
        }

        final double end = System.nanoTime();
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Current system time(cycles): " + SortedPriorityQueue.currentTime);
        System.out.println("Total number of jobs executed: " + SortedPriorityQueue.nbExecuted);
        System.out.println("Average process waiting time: " + SortedPriorityQueue.totalWaitTime/SortedPriorityQueue.nbExecuted);
        System.out.println("Actual system time needed to execute all jobs: " +  (end - start) / 1000000000 + " seconds.");

    }
}
