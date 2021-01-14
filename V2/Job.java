package com.company;

import java.util.Random;

public class Job {

    private String jobName;
    private int jobLength;
    private int jobPriority;
    private int currentPriority;
    private long entryTime;
    private long realEntryTime;
    private long endTime;
    private long waitTime;
    private long originalJobLength;
    Random random = new Random();


    public Job() {
        this.jobName = "Job_";
        this.jobLength = random.nextInt(69) + 1;
        this.jobPriority = random.nextInt(39) + 1;
        this.currentPriority = jobPriority;
        this.entryTime = 0;
        this.realEntryTime = 0;
        this.endTime = 0;
        this.waitTime = 0;
        this.originalJobLength = jobLength;
    }

    public Job(int jobLength, int jobPriority) {
        this.jobName = "Job_";
        this.jobLength = jobLength;
        this.jobPriority = jobPriority;
        this.currentPriority = jobPriority;
        this.entryTime = 0;
        this.realEntryTime = 0;
        this.endTime = 0;
        this.waitTime = 0;
        this.originalJobLength = jobLength;
    }

    @Override
    public String toString() {
        return "...Now executing " + jobName + "; Job length: " + jobLength + " cycles; Current remaining length: " + originalJobLength +
                " cycles; Initial priority: " + jobPriority + "; Current priority: " + currentPriority + "";
    }

    public String executionPrint() {
        return "Execution of " + jobName + " completed; Initial Job length: " + originalJobLength + " cycles; Initial priority: " + jobPriority + "; Entry Time: " + entryTime +
                " cycles; Wait Time: " + waitTime + " cycles; End Time: " + endTime + " cycles.";
    }

    public String getJobName() {
        return jobName;
    }


    public void setJobName(int jobNumber) {
        this.jobName = "Job_" + (jobNumber+1);
    }

    public int getJobLength() {
        return jobLength;
    }

    public void setJobLength(int jobLength) {
        this.jobLength = jobLength;
    }

    public int getJobPriority() {
        return jobPriority;
    }

    public void setJobPriority(int jobPriority) {
        this.jobPriority = jobPriority;
    }

    public int getCurrentPriority() {
        return currentPriority;
    }

    public void setCurrentPriority(int currentPriority) {
        this.currentPriority = currentPriority;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public long getRealEntryTime() {
        return realEntryTime;
    }

    public void setRealEntryTime(long realEntryTime) {
        this.realEntryTime = realEntryTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public long getOriginalJobLength() {
        return originalJobLength;
    }

    public void setOriginalJobLength(long originalJobLength) {
        this.originalJobLength = originalJobLength;
    }
}
