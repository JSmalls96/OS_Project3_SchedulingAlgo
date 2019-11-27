package sch;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static Queue<Job> jobQueue = new LinkedList<>();    //job queue
    static BufferedReader br;
    static File file;
    Scanner in = new Scanner(System.in);    //input scanner

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
        try { //try to read file line, split at tabs, then add to job queue as a newly created job
            while ((line = br.readLine()) != null) {
                String[] temp = line.split("\t");
                jobQueue.add(new Job(temp[0],Integer.parseInt(temp[1]),Integer.parseInt(temp[2])));
            }
        }catch (IOException e){
            System.out.println("IO Exception");
            e.printStackTrace();
        }
    }

    public static void printQueue(){//output queue, testing function.
        for(Job s : jobQueue) {
            System.out.println(s.toString());
        }
    }
}
