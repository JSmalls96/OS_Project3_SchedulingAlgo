/*
    Stuart Small
    sjs160530
    CS 4348.501
    Project 3
 */
package sch;

public class Job implements Cloneable{
    private String name; //name of job
    private int sTime, duration,remDuration,waitTime; //start time, duration, remaining duration and wait time
    public String[] outputArr; //array to hold the values to be printed or output

    //constructor
    public Job(String name,int sTime, int duration){
        this.name=name;
        this.sTime=sTime;
        this.duration=duration;
        this.waitTime=0;
        this.remDuration=duration;

    }

    //default constructor
    public Job(){
        this.name=null;
        this.sTime=-1;
        this.duration=-1;
        this.remDuration=duration;
        this.waitTime=0;
    }

    //getter for duration
    public int getRemDuration(){
        return this.remDuration;
    }

    //decrements duration by 1
    public void decRemDuration(){
        this.remDuration-=1;
    }

    //returns the wait time for the job
    public int getWaitTime(){
        return (this.waitTime-this.sTime);
    }

    //increments wait time by n
    public void incWaitTime(int n){
        this.waitTime+=n;
    }

    //returns the calculated response ratio
    //RR = (w-s)/s
    public float getResponseRatio(){
        return (((this.waitTime-sTime)+this.duration)/this.duration);
    }

    //getter for name
    public String getName(){
        return this.name;
    }

    //getter for start time
    public int getStart(){
        return this.sTime;
    }

    //getter for duration
    public int getDuration(){
        return this.duration;
    }

    public Job createClone() throws CloneNotSupportedException{
        Job clonedJob = (Job)super.clone();
        return clonedJob;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name+ " ");
        for(int i=0;i<this.outputArr.length;i++){
            if(this.outputArr[i]==null){
                sb.append(" ");
            }else{
                sb.append(this.outputArr[i]);
            }
        }
        return sb.toString();
    }
}
