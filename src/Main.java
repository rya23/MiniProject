////Things to do
//Implement a better sorting algorithm
//Exception Handling
//Loops initialization numbering
//Better names




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
        int max = arr[1].deadline;
        for (int i = 1; i <= n; i++) {
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
        for (i = 1; i < n; i++) {
            for (j = 1; j < n - i; j++) {
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
                arr = new Job[n+1];
                for (int i = 1; i <= n; i++) {

                    System.out.println("Enter Profit and Deadline of Job " + i + ": ");
                    int profit = sc.nextInt();
                    int deadline = sc.nextInt();
                    arr[i] = new Job(i, profit, deadline);
                    arr[i].printdata();
                }
                break;
            }
            case 2: {
//                n = 4;
//                arr = new Job[n+1];
//                arr[1] = new Job(1, 20, 4);
//                arr[2] = new Job(2, 10, 1);
//                arr[3] = new Job(3, 40, 2);
//                arr[4] = new Job(4, 20, 2);

                n = 6;
                arr = new Job[n+1];
                arr[1] = new Job(1, 30, 3);
                arr[2] = new Job(2, 25, 1);
                arr[3] = new Job(3, 50, 3);
                arr[4] = new Job(4, 40, 2);
                arr[5] = new Job(5, 10, 1);
                arr[6] = new Job(6, 35, 4);
                break;
            }
            default:
                System.out.println("Wrong input");

        }


        sort(arr, n);
        for (int i = 1; i <= n; i++) {
            System.out.print("ID : " + arr[i].id);
            System.out.print("\tProfit : " + arr[i].profit);
            System.out.println("\tDeadline : " + arr[i].deadline);

        }

        solve s = new solve();
        int max = s.maxdeadline(arr, n);
        System.out.println("max : " + max);
        int countJobs = 0, jobProfit = 0;
        int[] result = new int[max+1];
        for (int i = 1; i <= n; i++) {
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
            System.out.println(result[i]);


        }
        System.out.println("Job : " + countJobs + "\t Profit : " + jobProfit);

    }


}
