package sch;

public class Job {
    private String name; //name of job
    private int sTime, duration; //start time and duration of job

    //constructor
    public Job(String name,int sTime, int duration){
        this.name=name;
        this.sTime=sTime;
        this.duration=duration;
    }

    //default constructor
    public Job(){
        this.name=null;
        this.sTime=-1;
        this.duration=-1;
    }

    @Override
    public String toString() {
        return "name: "+this.name+" start time: "+this.sTime+" duration: "+this.duration;
    }
}
