/*
    Stuart Small
    sjs160530
    CS 4348.501
    Project 3
 */
package sch;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Job> jobs = new ArrayList<>();    //job queue
    private static BufferedReader br; //buffer to read file
    private static File file; //file to be read
    private static Scheduler sch = new Scheduler(); //scheduler
    private static int totalDuration=0; //total duration calc during parse

    public static void main(String[] args) {
        start(); //starts execution
    }

    private static void start(){
        try {
            file = new File(".");
            String path = file.getCanonicalPath();
            boolean choice = fileInputType();
            if(choice){
                path += "\\resources\\jobs.txt";
            }else {
                path = path+"\\resources\\"+promptFile();
            }
            file = new File(path);
            br = new BufferedReader(new FileReader(file));
            parseFile();
            promptUser();
        }catch (FileNotFoundException e){
            System.out.println("File Not Found! Please figure it out.\n You may need to add the file to the resource folder");
            System.out.println("EXITING: Run Again and ensure .txt file is in resource folder, or select YES and run default job");
            start();
        }catch (IOException t){}
    }

    private static void parseFile(){ //take file, fill queue with newly created jobs
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

    //how does the file want to be taken
    private static String promptFile(){
        Scanner in = new Scanner(System.in);
        System.out.println("Ensure job is placed inside of resource directory if not jobs.txt, jobs1.txt, or jobs2.txt\nPlease enter file name: ");
        String input = in.nextLine();
        return input;
    }

    private static boolean fileInputType(){
        Scanner in = new Scanner(System.in);
        System.out.print("\nEnter\n\tYES, to run with a default path to jobs.txt\n\tNO, to run with separate input file\n\t\t" +
                "Note: File needs to be placed inside of resource directory if not jobs.txt, jobs1.txt, or jobs2.txt and NO is selected.\n\tEXIT, to exit the program\nINPUT:");
        String input = in.nextLine();
        if(input.equals("YES")){
            System.out.println("\tYES SELECTED");
            return true;
        }else if(input.equals("NO")){
            System.out.println("\tNO SELECTED");
            return false;
        }else if(input.equals("EXIT")){
            System.out.println("\tEXIT SELECTED... GOODBYE");
            System.exit(0);
        }else{
            System.out.println("\tINVALID INPUT: PROMPTING AGAIN");
        }
        return true;
    }

    //takes user prompt
    private static void promptUser(){
        try{
            System.out.println("\nSelect Scheduling Algorithm\n---------------------------" +
                    "\n\tFCFS\n\tRR\n\tSPN\n\tSRT\n\tHRRN\n\tFB\n\tALL\n\tEXIT\n---------------------------");

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
