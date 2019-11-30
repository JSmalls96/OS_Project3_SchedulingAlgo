package sch;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static ArrayList<Job> jobs = new ArrayList<>();    //job queue
    static ArrayList<Job> copy = new ArrayList<>();    //copy
    static BufferedReader br;
    static File file;
    static Scheduler sch = new Scheduler();
    static int totalDuration=0;

    public static void main(String[] args) {
        try {//try to open file
            file = new File("C:\\Users\\jacks\\Desktop\\School\\Fall19\\CS4348-OperatingSystems\\OS_Project3_SchedulingAlgo\\resources\\jobs.txt");
            br = new BufferedReader(new FileReader(file));
            parseFile();
            promptUser();
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
                jobs.add(new Job(temp[0],Integer.parseInt(temp[1]),duration));
            }
        }catch (IOException e){
            System.out.println("IO Exception");
            e.printStackTrace();
        }
    }

    private static void printQueue(){//output queue, testing function.
        System.out.println("Printing Queue");
        for(Job s : jobs) {
            System.out.println(s.toString());
        }
    }

    private static void promptUser(){
        try{
            System.out.println("\n\tSelect Scheduling Algorithm\n\tFCFS\n\tRR\n\tSPN\n\tSRT\n\tHRRN\n\tFB\n\tALL\n\tEXIT");
            Scanner in = new Scanner(System.in);    //input scanner
            System.out.print("Make your selection: ");
            switch (in.nextLine()){
                case "FCFS": sch.schedule_fcfs(jobs,totalDuration);break;
                case "RR": sch.schedule_rr(jobs,totalDuration); break;
                case "SPN": sch.schedule_spn(jobs,totalDuration);break;
                case "SRT": sch.schedule_srt(jobs,totalDuration); break;
                case "HRRN": sch.schedule_hrrn(jobs,totalDuration); break;
                case "FB": sch.schedule_fb(jobs,totalDuration); break;
                case "ALL": sch.schedule_all(jobs,totalDuration);break;
                case "EXIT": System.exit(0); break;
                case " ": System.out.println("INPUT NEEDED!"); promptUser(); break;
                default: System.out.println("INVALID CHOICE"); promptUser(); break;
            }
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
    }
}
