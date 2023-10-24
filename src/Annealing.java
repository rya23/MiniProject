import java.util.Scanner;

//import static java.util.Collections.swap;

class Job {
    int id,time,deadline,holding,penalty;

    Job(int id, int time, int deadline,int holding,int penalty) {
        this.id = id;
        this.time=time;
        this.deadline = deadline;
        this.holding=holding;
        this.penalty=penalty;
    }

    public void printdata() {
        System.out.print("ID : " + id);
        System.out.print("\tTime : " + time);
        System.out.println("\tHolding : " + holding);
        System.out.println("\tDeadline : " + deadline);
        System.out.println("\tPenalty : " + penalty);

    }


}



public class Annealing {

    public static int solve(Job arr [])
    {
        int pen = 0;
        int hold= 0;
        int ptime=0;
        for(int i=1; i<5;i++)
        {
            ptime=ptime+arr[i].time;
            if(arr[i].deadline>ptime)
            {
                int temp = arr[i].deadline-ptime;
                hold = hold + (temp*arr[i].holding);
            }
            else {
                int temp = ptime-arr[i].deadline;
                pen = pen + (temp*arr[i].penalty);

            }

        }
//        System.out.println(hold+pen);
        return hold+pen;
    }

    public static void swap(Job arr[],int i,int j)
    {
        Job temp = arr[i];
        arr[i] = arr[j];
        arr[j]=temp;
    }
    public static void neigh(Job arr [])
    {   int n=4;
        for(int i=1;i<n;i++)
        {
            float prob = i*((float) 1 /(n-1));
            if(prob>Math.random())
            {
                swap(arr,i,i+1);
                break;
            }
            //swap(arr,i,i+1);
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello");

        int n = 4;
        Job[] arr = new Job[n + 1];
        arr[1] = new Job(1, 10, 15,3,10);
        arr[2] = new Job(2, 8,20,2,22);
        arr[3] = new Job(3, 6,10,5,10);
        arr[4] = new Job(4,7,30,4,8);
        int min=solve(arr);
        for(int i=1;i<100;i++)
        {

            neigh(arr);
            int temp=solve(arr);
            System.out.print("minimum " + min);
            System.out.println(" temperory " + temp);

            if(temp<min)
            {
//                System.out.print("Current minimum " + min);
                min=temp;
//                System.out.println("Next minimum " +min);

            }


        }
        for(int i=1;i<=n;i++)
        {
            arr[i].printdata();
        }
        int z = solve(arr);
        System.out.println(z);
        //swap(arr,2,3);
    }

}