package sch;

import java.util.Queue;

public class Scheduler {
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
    public void schedule_fcfs(Queue<Job> jq, int totalDuration){
        Job temp;
        System.out.println("SCHEDULED USING FIRST COME FIRST SERVE");
        while((temp = jq.poll())!= null){

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
    public void schedule_rr(Queue<Job> jq, int totalDuration){
        int quantum = 1;
        System.out.println("SCHEDULED USING ROUND ROBIN");
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
    public void schedule_spn(Queue<Job> jq, int totalDuration){
        System.out.println("SCHEDULED USING SHORTEST PROCESS NEXT");
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
    public void schedule_srt(Queue<Job> jq, int totalDuration){
        System.out.println("SCHEDULED USING SHORTEST REMAINING TIME");
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
    public void schedule_hrrn(Queue<Job> jq, int totalDuration){
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
    public void schedule_fb(Queue<Job> jq, int totalDuration){
        int quantum = 1;
        System.out.println("SCHEDULED USING FEEDBACK WITH A QUANTUM OF 1");
    }

    // perform all scheduling algorithms on jobs
    public void schedule_all(Queue<Job> jq, int totalDuration){
        System.out.println("ALL SELECTED");
        schedule_fcfs(jq, totalDuration);
        schedule_rr(jq, totalDuration);
        schedule_spn(jq, totalDuration);
        schedule_srt(jq, totalDuration);
        schedule_hrrn(jq,totalDuration);
        schedule_fb(jq, totalDuration);
    }

}
