/*
    Stuart Small
    sjs160530
    CS 4348.501
    Project 3
 */

package sch;

import java.util.*;

public class Scheduler {
    Map<String,Job> mapOut = new HashMap<>(); //map to store jobs based on their name, used for easy lookup during output formatting
    ArrayList<Job> copy; //copy of array list used to reset process array list after changes
    int time; //time passed, pointer


    /*
        scheduling algorithm: first come first serve
        selection function : max[w]
        decision mode : non-preemptive
        throughput : not emphasized
        response time: may be high, especially if there is a large variance in process execution time
        overhead: minimum
        effect of processes: penalizes short processes; penalizes I/O-bound processes
        starvation: no
    */
    protected void schedule_fcfs(ArrayList<Job> jq, int totalDuration){
        Job activeProcess; //process that is being currently run
        time=0;
        System.out.println("\nSCHEDULED USING FIRST COME FIRST SERVE\n--------------------------------------------");
        while(jq.size()>0){
            activeProcess=jq.remove(0);
            activeProcess.outputArr= new String[totalDuration];
            int dur = activeProcess.getDuration();
            for(int i=0; i<totalDuration;){
                while(i<time){i++;}
                for(;dur>0;dur--,i++,time++){//active location
                    activeProcess.outputArr[i]="X";
                }
                if(i>=time && i<activeProcess.outputArr.length){//later spaces
                    activeProcess.outputArr[i]=" ";
                    i++;
                }
            }
            System.out.println(activeProcess.toString());
        }
    }

    /*
        scheduling algorithm: round robin
        selection function :  constant
        decision mode : preemptive (at time quantum)
        throughput : may be low if quantum is too small
        response time:  provides good response time for short processes
        overhead:  minimum
        effect of processes: fair treatment
        starvation: no
    */
    protected void schedule_rr(ArrayList<Job> jq, int totalDuration){
        System.out.println("\n\nSCHEDULED USING ROUND ROBIN\n--------------------------------------------");
        Queue<Job> que = new LinkedList<>();
        int quantum =1;
        time=0;
        instArr(jq,totalDuration);
        checkStart(jq,que,time);
        Job activeProcess=que.peek();
        while(jq.size()>0 || que.size()>0){
            for(int i=0; i<totalDuration;){
                activeProcess=que.poll();
                while(i<time){i++;}
                for(int j=0; j<quantum && i<activeProcess.outputArr.length &&activeProcess.getRemDuration()>0;j++,i++,time++){
                    activeProcess.outputArr[i]="X";
                    activeProcess.decRemDuration();
                }
                checkStart(jq,que,time);
                if(activeProcess.getRemDuration()>0){
                    que.add(activeProcess);
                }else{
                    mapAdd(activeProcess);
                }
            }
            if(!(activeProcess.getRemDuration()>0) && !(jq.size()>0)) {
                activeProcess = null;
            }
        }
        printMap();
        mapOut.clear();
    }

    /*
      scheduling algorithm: shortest process next
      selection function : min [s]
      decision mode : non-preemptive
      throughput : high
      response time: provides good response time for short processes
      overhead: can be high
      effect of processes: penalizes long processes
      starvation: possible
  */
    protected void schedule_spn(ArrayList<Job> jq, int totalDuration){

        System.out.println("\n\nSCHEDULED USING SHORTEST PROCESS NEXT\n--------------------------------------------");
        time=0;
        instArr(jq,totalDuration);
        Job activeProcess=jq.remove(0); //process that is being currently run
        while(jq.size()>0 || activeProcess!=null){
            int dur = activeProcess.getDuration();
            for(int i=0; i<totalDuration;){
                while(i<time){i++;}
                for(;dur>0&&i<totalDuration;dur--,i++,time++){
                    activeProcess.outputArr[i]="X"; //active location
                }
                if(i>=time && i<activeProcess.outputArr.length){ //later spaces
                    activeProcess.outputArr[i]=" ";
                    i++;
                }
            }
            mapAdd(activeProcess);
            if(jq.size()!=0){
                activeProcess=jq.remove(jq.indexOf(getMin(jq,time)));
            }else {
                break;
            }
        }
        printMap();
        mapOut.clear();
    }

    /*
      scheduling algorithm: shortest remaining time
      selection function : min [s - e]
      decision mode : preemptive (at arrival)
      throughput : high
      response time: provides good response time
      overhead: can be high
      effect of processes: penalizes long processes
      starvation: possible
    */
    protected void schedule_srt(ArrayList<Job> jq, int totalDuration){
        System.out.println("\n\nSCHEDULED USING SHORTEST REMAINING TIME\n--------------------------------------------");
        Job min;
        time=0;
        instArr(jq,totalDuration);
        Job activeProcess = jq.remove(0);
        while(jq.size()>0 || activeProcess!=null){ // if the array list is empty or if there is a process active
            for(int i=0; i<totalDuration;i++){
                while(i<time){i++;}
                for(;activeProcess.getRemDuration()>0&&i<totalDuration;i++,time++){
                    activeProcess.outputArr[i]="X";
                    activeProcess.decRemDuration();
                    if(activeProcess.getRemDuration()>0 && !jq.contains(activeProcess)){
                        Job temp = activeProcess;
                        jq.add(temp);
                        min=getMin2(jq,time);
                        activeProcess=jq.remove(jq.indexOf(min));
                    }
                }
                if(i>=time && i<activeProcess.outputArr.length){ //later spaces
                    activeProcess.outputArr[i]=" ";
                    i++;
                }
            }
            mapAdd(activeProcess);//add completed process to map

            //continuation case, either exit or continue
            if(!(activeProcess.getRemDuration()>0) && !(jq.size()>0)){
                activeProcess=null;
            }else{
                activeProcess = jq.remove(jq.indexOf(getMin2(jq,time)));
            }
        }
        printMap();
        mapOut.clear();
    }

    /*
      scheduling algorithm: highest response ratio next
      selection function : max ((w+s)/s)
      decision mode : non-preemptive
      throughput : high
      response time: provides good response time
      overhead: can be high
      effect of processes: good balance
      starvation: no
    */
    protected void schedule_hrrn(ArrayList<Job> jq, int totalDuration){
        System.out.println("\n\nSCHEDULED USING HIGHEST RESPONSE RATIO NEXT\n--------------------------------------------");
        time=0;
        instArr(jq,totalDuration);
        Job activeProcess=jq.remove(0); //process that is being currently run
        while(jq.size()>0 || activeProcess!=null){
            int dur = activeProcess.getDuration();
            for(int i=0; i<totalDuration;i++){
                while(i<time){i++;}
                massIncWaitTime(jq,dur);
                for(;dur>0&&i<totalDuration;dur--,i++,time++){
                    activeProcess.outputArr[i]="X"; //active location
                }
            }
            mapAdd(activeProcess);
            if(jq.size()!=0){
                activeProcess=jq.remove(jq.indexOf(getMaxRR(jq,time)));
            }else {
                break;
            }
        }
        printMap();
        mapOut.clear();

    }

    /*
      scheduling algorithm: feedback, q=1
      selection function :
      decision mode : preemptive (at time quantum)
      throughput : not emphasized
      response time: not emphasized
      overhead: can be high
      effect of processes: may favor I/O-bound process
      starvation: possibles
    */
    protected void schedule_fb(ArrayList<Job> jq, int totalDuration){
        System.out.println("\n\nSCHEDULED USING FEEDBACK WITH A QUANTUM OF 1\n--------------------------------------------");
        Queue<Job> readyQueueTop = new LinkedList<>(); //high priority ready queue
        Queue<Job> readyQueueMid = new LinkedList<>(); //mid priority ready queue
        Queue<Job> readyQueueLow = new LinkedList<>(); //low priority ready queue
        int quantum = 1; //time quantum
        time=0;
        instArr(jq,totalDuration); //instantiate top queue
        do{//ensure
            checkStart(jq,readyQueueTop,time); //fill top queue with any ready job from jq
        }while(readyQueueTop.size()==0);
        Job activeProcess = readyQueueTop.peek();
        while (jq.size()>0 || activeProcess!=null){

        }
    }

    // perform all scheduling algorithms on jobs
    protected void schedule_all(ArrayList<Job> jq, int totalDuration)throws CloneNotSupportedException{
        System.out.println("\n\t\t\tALL SELECTED");
        copy = properClone(jq);
        schedule_fcfs(jq, totalDuration);
        jq = properClone(copy);
        schedule_rr(jq, totalDuration);
        jq = properClone(copy);
        schedule_spn(jq, totalDuration);
        jq = properClone(copy);
        schedule_srt(jq, totalDuration);
        jq = properClone(copy);
        schedule_hrrn(jq,totalDuration);
        jq = properClone(copy);
        schedule_fb(jq, totalDuration);
    }

/*
Helpers
 */

    //given an array, go through and add any job who's start time has passed to the ready queue
    // and remove those elements from the array list
    protected void checkStart(ArrayList<Job> arr,Queue<Job> que,int time){
        if(arr.size()>0){
            for(Job x:arr){
                if(x.getStart()<=time){
                    que.add(x);

                }
            }
            for(Job x:que){
                arr.remove(x);
            }
        }
    }

    //get minimum for spn
    protected Job getMin(ArrayList<Job> arr,int time){
        Job min = null;
        for(Job x:arr){
            min=(min==null||(x.getDuration()<min.getDuration()&& x.getStart()<=time))?x:min;
        }
        return min;
    }

    //get minimum time remaining
    protected Job getMin2(ArrayList<Job> arr,int time){
        Job min = null;
        for(Job x:arr){
            min=(min==null||(x.getRemDuration()<=min.getRemDuration()&& x.getStart()<=time))?x:min;
        }
        return min;
    }

    //instantiate array lists for processes
    protected void instArr(ArrayList<Job> j, int totalDuration){
        for(Job t:j){
            t.outputArr = new String[totalDuration];
        }

    }

    //get highest response ratio from array list
    protected Job getMaxRR(ArrayList<Job> arr,int time){
        Job max = null;
        for(Job x:arr){
            //if there is a job with a higher RR, is waiting past its start time, set that as max
            max=(max==null||(x.getResponseRatio()>=max.getResponseRatio()&& x.getStart()<=time))?x:max;
        }
        return max;
    }

    //increments every job's wait time in the array list by a certain dur
    protected void massIncWaitTime(ArrayList<Job> arr,int dur){
        for(Job x:arr){
            x.incWaitTime(dur);
        }
    }

    //prints array list, used for testing. currently set up to test HRRN schedule
    protected void printArrayList(ArrayList<Job> jq){
        System.out.println("----------------");
        for (int i=0; i<jq.size();i++){
            System.out.println(jq.get(i).getName()+" RR:"+jq.get(i).getResponseRatio());
        }
        System.out.println("----------------");
    }

    //adds a job to the output map
    protected void mapAdd(Job activeProcess){
        if(!mapOut.containsValue(activeProcess)){
            mapOut.put(activeProcess.getName(),activeProcess);
        }
    }

    //prints the map of jobs
    protected void printMap(){
        Iterator<Job> itr = mapOut.values().iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    //clones an array list given a source and returns a clone
    protected ArrayList<Job> properClone(ArrayList<Job> source)throws CloneNotSupportedException{
        ArrayList<Job> destination = new ArrayList<>();
        for(Job j:source){
            destination.add(j.createClone());
        }
        return destination;
    }
}
