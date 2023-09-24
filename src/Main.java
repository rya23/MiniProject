import java.util.Scanner;

class Job {
    int id, deadline, profit;

    Job(int id, int profit, int deadline) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }

    public void printdata() {
        System.out.print("ID : " + id);
        System.out.print("\tProfit : " + profit);
        System.out.println("\tDeadline : " + deadline);
    }


}

class solve {
    public int maxdeadline(Job[] arr, int n) {
        int max = arr[0].deadline;
        for (int i = 0; i < n; i++) {
            if (arr[i].deadline > max) {
                max = arr[i].deadline;
            }
        }
        return max;
    }

    public boolean findslot(int[] result, int j) {
        return (result[j] == 0);
    }

}

public class Main {

    static void sort(Job[] arr, int n) {
        System.out.println(arr[1].profit);
        int i, j;
        Job temp;
        for (i = 0; i < n - 1; i++) {
            for (j = 0; j < n - 1 - i; j++) {
                if (arr[j].profit < arr[j + 1].profit) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

    }

    public static void main(String[] args) {
        Job[] arr= new Job[0];
        int n=0;
        System.out.println("Hello");

        System.out.println("1. Manual Input  \t 2. Automatic input");
        Scanner sc = new Scanner(System.in);
        int no = sc.nextInt();
        switch (no) {
            case 1: {
                System.out.println("Enter number of Jobs to Schedule : ");
                n = sc.nextInt();
                System.out.println(n);
                arr = new Job[n];
                for (int i = 0; i < n; i++) {

                    System.out.println("Enter Profit and Deadline of Job " + (i + 1) + ": ");
                    int profit = sc.nextInt();
                    int deadline = sc.nextInt();
                    arr[i] = new Job(i + 1, profit, deadline);
                    arr[i].printdata();
                }
                break;
            }
            case 2: {
                n = 4;
                arr = new Job[n];
                arr[0] = new Job(1, 20, 4);
                arr[1] = new Job(2, 10, 1);
                arr[2] = new Job(3, 40, 2);
                arr[3] = new Job(4, 20, 2);

            }
            break;
            default:
                System.out.println("Wrong input");

        }


        sort(arr, n);
        for (int i = 0; i < n; i++) {
            System.out.print("ID : " + arr[i].id);
            System.out.print("\tProfit : " + arr[i].profit);
            System.out.println("\tDeadline : " + arr[i].deadline);

        }

        solve s = new solve();
        int max = s.maxdeadline(arr, n);
        System.out.println("max : " + max);
        int countJobs = 0, jobProfit = 0;
        int[] result = new int[max+1];
        for (int i = 0; i < n; i++) {
            for (int j = arr[i].deadline; j > 0; j--) {
                if (s.findslot(result, j)) {
                    result[j] = arr[i].id;
                    countJobs++;
                    jobProfit += arr[i].profit;
                    break;
                }
            }
        }
        System.out.println("Jobs Done : ");
        for (int i = 1; i <= max; i++) {
            if(result[i]==0) continue;
            arr[result[i]-1].printdata();
        }
        System.out.println("Job : " + countJobs + "\t Profit : " + jobProfit);

    }


}
