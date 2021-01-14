package com.company;

public class Main {

    public static void main(String[] args) {

        System.out.println("||<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<||\n"
                + "       ||      CPU SIMULATOR Version 2      ||\n"
                + "||>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>||\n");

        int MaxNumberOfJobs = 5;
        Job[] jobsInputArray = new Job[MaxNumberOfJobs];
        HeapPriorityQueue pq = new HeapPriorityQueue();

       final double start = System.nanoTime();
        for(int i = 0; i < MaxNumberOfJobs; i++){
            jobsInputArray[i] = new Job();
            pq.insert(jobsInputArray[i]);
        }

        while (!pq.isEmpty()){
            pq.processQueue();
        }

        final double end = System.nanoTime();
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Current system time(cycles): " + HeapPriorityQueue.currentTime);
        System.out.println("Total number of jobs executed: " + HeapPriorityQueue.nbExecuted);
        System.out.println("Average process waiting time: " + HeapPriorityQueue.totalWaitTime/HeapPriorityQueue.nbExecuted);
        System.out.println("Actual system time needed to execute all jobs: " +  (end - start) / 1000000000 + " seconds.");

    }
}
 /*jobsInputArray[0] = new Job(5,1);
        jobsInputArray[1] = new Job(2,8);
        jobsInputArray[2] = new Job(3,3);
        jobsInputArray[3] = new Job(4,1);
        jobsInputArray[4] = new Job(6,1);
        jobsInputArray[5] = new Job(2,2);
        jobsInputArray[6] = new Job(1,3);
        jobsInputArray[7] = new Job(7,4);
        jobsInputArray[8] = new Job(4,3);
        jobsInputArray[9] = new Job(2,3);

        for (int i = 0; i <10 ; i ++){
            pq.insert(new Entry(jobsInputArray[i].getJobPriority(), jobsInputArray[i]));
        }
        while (!pq.isEmpty()){
            pq.processQueue();
        }
*/
