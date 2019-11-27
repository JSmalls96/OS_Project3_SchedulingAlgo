package sch;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static Queue<Job> jobQueue = new LinkedList<>();    //job queue
    static BufferedReader br;
    static File file;
    static Scheduler sch = new Scheduler();
    static int totalDuration=0;

    public static void main(String[] args) {
        try {//try to open file
            file = new File("C:\\Users\\jacks\\Desktop\\School\\Fall19\\CS4348-OperatingSystems\\OS_Project3_SchedulingAlgo\\resources\\jobs.txt");
            br = new BufferedReader(new FileReader(file));
            parseFile();
//            printQueue();
        }catch (FileNotFoundException e){
            System.out.println("File Not Found! Please figure it out.");
            e.printStackTrace();
        }
    }

    public static void parseFile(){ //take file, fill queue with newly created jobs
        String line;
        int duration;
        try { //try to read file line, split at tabs, then add to job queue as a newly created job
            while ((line = br.readLine()) != null) {
                String[] temp = line.split("\t");
                totalDuration += duration = Integer.parseInt(temp[2]);
                jobQueue.add(new Job(temp[0],Integer.parseInt(temp[1]),duration));
            }
        }catch (IOException e){
            System.out.println("IO Exception");
            e.printStackTrace();
        }
    }

    private static void printQueue(){//output queue, testing function.
        for(Job s : jobQueue) {
            System.out.println(s.toString());
        }
    }

    private static void promptUser(){
        System.out.println("\tSelect Scheduling Algorithm\n\tFCFS\n\tRR\n\tSPN\n\tSRT\n\tHRRN\n\tFB\n\tALL\n\tEXIT");
        Scanner in = new Scanner(System.in);    //input scanner
        switch (in.nextLine()){
            case "FCFS": sch.schedule_fcfs(jobQueue,totalDuration); break;
            case "RR": sch.schedule_rr(jobQueue,totalDuration); break;
            case "SPN": sch.schedule_spn(jobQueue,totalDuration); break;
            case "SRT": sch.schedule_srt(jobQueue,totalDuration); break;
            case "HRRN": sch.schedule_hrrn(jobQueue,totalDuration); break;
            case "FB": sch.schedule_fb(jobQueue,totalDuration); break;
            case "ALL": sch.schedule_all(jobQueue,totalDuration); break;
            case "EXIT": System.exit(0); break;
            case " ": System.out.println("INPUT NEEDED!"); promptUser(); break;
            default: System.out.println("INVALID CHOICE"); promptUser(); break;
        }
    }
}
