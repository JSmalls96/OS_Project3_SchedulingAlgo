package sch;

public class Job implements Cloneable{
    private String name; //name of job
    private int sTime, duration,remDuration,waitTime; //start time and duration of job
    private boolean running = false;
    public String[] outputArr;

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

    public int getRemDuration(){
        return this.remDuration;
    }

    public void setRemDuration(int n){
        this.remDuration=n;
    }

    public void decRemDuration(int n){
        this.remDuration-=n;
    }

    public void decRemDuration(){
        this.remDuration-=1;
    }

    public int getWaitTime(){
        return this.waitTime;
    }

    public void incWaitTime(){
        this.waitTime++;
    }

    public void setWaitTime(int n){
        this.waitTime=n;
    }

    public void incWT(){
        this.waitTime++;
    }

    public void setOutputArr(int totalDuration){
        this.outputArr= new String[totalDuration];
    }


    public String getName(){
        return this.name;
    }

    public int getStart(){
        return this.sTime;
    }

    public int getDuration(){
        return this.duration;
    }

    public boolean getState(){
        return this.running;
    }

    public Job createClone() throws CloneNotSupportedException{
        Job clonedJob = (Job)super.clone();
        return clonedJob;
    }
    public void toggleRunning(){
        this.running = ((this.running)?false:true);
    }

//    @Override
//    public String toString() {
//        return "name: "+this.name+" start time: "+this.sTime+" duration: "+this.duration;
//    }


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
