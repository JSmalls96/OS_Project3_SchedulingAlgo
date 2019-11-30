package sch;

import java.util.*;

public class Scheduler {
    Map<String,Job> mapOut = new HashMap<>(); //map to store jobs based on their name, used for easy lookup during output formatting
    ArrayList<Job> copy; //copy of arraylist used to reset process arraylist after changes
    int numProcesses;
    int time; //time passed, pointer
    int wt=0; //wait time in system
    int e; // time spent in execution so far, so would be totalDuration-remDur

    // w = time spent in system so far, waiting
    // e = time spent in execution so far
    // s = total service time required by the process, including e; generally, this quantity must be estimated or supplied by user

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
    public void schedule_fcfs(ArrayList<Job> jq, int totalDuration){
        Job activeProcess; //process that is being currently run
        time=0;
        numProcesses = jq.size(); //number of processes parsed from file
        System.out.println("SCHEDULED USING FIRST COME FIRST SERVE");
        while(jq.size()>0){
            activeProcess=jq.remove(0);
            activeProcess.outputArr= new String[totalDuration];
            int dur = activeProcess.getDuration();
            for(int i=0; i<totalDuration;){
                for(int k=0;k<time && i<activeProcess.outputArr.length;k++,i++){//preceding spaces
                    activeProcess.outputArr[i]=" ";
                }
                for(;dur>0&&i<totalDuration;dur--,i++,time++){//active location
                    activeProcess.outputArr[i]="X";
                }
                if(i>=time && i<activeProcess.outputArr.length){//later spaces
                    activeProcess.outputArr[i]=" ";
                    i++;
                }
            }
            System.out.println(activeProcess.toString());
        }
        System.out.println("Scheduling "+numProcesses+" jobs took a total of "+totalDuration+ " time units");
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
    public void schedule_rr(ArrayList<Job> jq, int totalDuration){
//        Map<Integer,Job> start_value_pair= new HashMap<>(); //store quick lookup for process and start address
//        jq.forEach((n)-> start_value_pair.put(n.getStart(),n)); //lookup for processes by start time
//
//        Queue<Job> readyQueue = new LinkedList<>(); //ready queue
//
//        int quantum = 1;
//        int numProcesses=jq.size();
//        int time=0;
//        int waitTime;
//
//        System.out.println("SCHEDULED USING ROUND ROBIN");
//        Job activeProcess;
//        while ((activeProcess = start_value_pair.get(time)) == null) {//sets the starting time index for the first process
//            for(int i=0;i<numProcesses;i++){
//                jq.get(i).outputArr[time] = " ";
//            }
//            time++;
//        } //following this while the 'time' is the starting time of the first process
//        activeProcess=start_value_pair.get(time);
//        readyQueue.add(activeProcess); //sets the starting time index for the first process
//
////        while (jq.size()>0 || readyQueue.size()>0) {
//            //create condition to be added to queue
//            Job next;
//            if ((next = readyQueue.poll())!=null) {
//                System.out.println(next.toString());
//                if(next.getRemDuration()>0 && next.getRemDuration()>=quantum){
//                    for(int i=0;i<quantum;i++){
//                      next.outputArr[time+i]=next.getName();
//                      time++;
//                    }
//                    readyQueue.add(next);
//                }
//            }
//            System.out.println(next.toString());

//        }
//        output(jq);





           //        while(jq.size()>0){
//            activeProcess=jq.remove(0);
//            activeProcess.outputArr= new String[totalDuration];
//            int dur = activeProcess.getDuration();
//            int remDur = activeProcess.getRemDuration();
//            if(remDur>0){ //dur greater than 0, then only need to process further
//                if(remDur>quantum){
//                    time+=quantum; //increase value of time by how much time process processed
//                    activeProcess.decRemDuration(quantum); //decrease rem duration for job by quantum size
//                    for(int i=0; i<quantum;i++){
//
//                    }
//                }else{
//                    time += remDur; //inc value of time, shows how much time a process is processed
//                    activeProcess.setWaitTime(time-dur); //wait time is time - duration used by process
//                    activeProcess.setRemDuration(0);//process gets fully executed, set remaining duration to 0
//                }
//                jq.add(activeProcess); //pending process
//            }
//        }
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
    public void schedule_spn(ArrayList<Job> jq, int totalDuration){
        System.out.println("Inside spn schedule");
        System.out.println("SCHEDULED USING SHORTEST PROCESS NEXT");
        time=0;
        numProcesses = jq.size(); //number of processes parsed from file
        instArr(jq,totalDuration);
        Job activeProcess=jq.remove(0); //process that is being currently run
        while(jq.size()>0 || activeProcess!=null){
            int dur = activeProcess.getDuration();
//            activeProcess.outputArr= new String[totalDuration];
            for(int i=0; i<totalDuration;){
                for(int k=0;k<time && i<activeProcess.outputArr.length;k++,i++){
                    activeProcess.outputArr[i]=" "; //preceding spaces
                }
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
        System.out.println("\nScheduling "+numProcesses+" jobs took a total of "+totalDuration+ " time units\n");
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
    public void schedule_srt(ArrayList<Job> jq, int totalDuration){
        System.out.println("SCHEDULED USING SHORTEST REMAINING TIME");
        Job min;
        numProcesses=jq.size();
        time=0;
        instArr(jq,totalDuration);
        Job activeProcess = jq.remove(0);
        while(jq.size()>0 || activeProcess!=null){ // if the array list is empty or if there is a process active
            for(int i=0; i<totalDuration;i++){
                for(int k=0;k<time && i<activeProcess.outputArr.length;k++,i++){
                    try{
                        if(activeProcess.outputArr[i].equals('X')){
                            activeProcess.outputArr[i]="X"; //preceding spaces
                        }
                    }catch (NullPointerException e){
                        activeProcess.outputArr[i]=" "; //preceding spaces
                    }
                }
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
            mapAdd(activeProcess);

            if(!(activeProcess.getRemDuration()>0) && !(jq.size()>0)){
                activeProcess=null;
            }else{
                activeProcess = jq.remove(jq.indexOf(getMin(jq,time)));
            }
        }
        printMap();

        mapOut.clear();
        System.out.println("\nScheduling "+numProcesses+" jobs took a total of "+totalDuration+ " time units\n");
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
    public void schedule_hrrn(ArrayList<Job> jq, int totalDuration){
        System.out.println("SCHEDULED USING HIGHEST RESPONSE RATIO NEXT");
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
    public void schedule_fb(ArrayList<Job> jq, int totalDuration){
        int quantum = 1;
        System.out.println("SCHEDULED USING FEEDBACK WITH A QUANTUM OF 1");
    }

    // perform all scheduling algorithms on jobs
    public void schedule_all(ArrayList<Job> jq, int totalDuration)throws CloneNotSupportedException{
        copy = properClone(jq);
        printArrayList(copy);
        printArrayList(jq);
        System.out.println("ALL SELECTED");
        schedule_fcfs(jq, totalDuration);
//        System.out.println("1");
        printArrayList(jq);
//        System.out.println("2");
//        printArrayList(copy);
        jq = properClone(copy);
//        System.out.println("3");
        printArrayList(jq);
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

    public void mapAdd(Job activeProcess){
        if(!mapOut.containsValue(activeProcess)){
            mapOut.put(activeProcess.getName(),activeProcess);
        }
    }

    public void printArrayList(ArrayList<Job> jq){
        System.out.println("----------------");
        for (int i=0; i<jq.size();i++){
            System.out.println(jq.get(i).getName());
        }
        System.out.println("----------------");
    }

    public void printMap(){
        Iterator<Job> itr = mapOut.values().iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    public ArrayList<Job> properClone(ArrayList<Job> source)throws CloneNotSupportedException{
        ArrayList<Job> destination = new ArrayList<>();
        for(Job j:source){
            destination.add(j.createClone());
        }
        return destination;
    }

    public void output(ArrayList<Job> jq){
        System.out.println(jq.size());
        for(int i=0;i<jq.size();i++){
            StringBuilder sb = new StringBuilder();
            sb.append(jq.get(i).getName()+ " ");
            System.out.println(jq.get(i).outputArr.length);
            for(int j=0;i<jq.get(i).outputArr.length;i++){
                sb.append(jq.get(i).outputArr[j]);
            }
            System.out.println(sb.toString());
        }
    }

    //get minimum for spn
    public Job getMin(ArrayList<Job> arr,int time){
        Job min = null;
        for(Job x:arr){
            min=(min==null||(x.getDuration()<min.getDuration()&& x.getStart()<=time))?x:min;
        }
        return min;
    }

    //get minimum for spt
    public Job getMin2(ArrayList<Job> arr,int time){
        Job min = null;
        for(Job x:arr){
            min=(min==null||(x.getRemDuration()<=min.getRemDuration()&& x.getStart()<=time))?x:min;
        }
        return min;
    }

    public void instArr(ArrayList<Job> j, int totalDuration){
        for(Job t:j){
            t.outputArr = new String[totalDuration];
        }

    }
}
