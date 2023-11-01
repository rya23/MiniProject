import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

class Job {
    int id, time, deadline, holding, penalty;

    Job(int id, int time, int deadline, int holding, int penalty) {
        this.id = id;
        this.time = time;
        this.deadline = deadline;
        this.holding = holding;
        this.penalty = penalty;
    }
}

public class AnnealingAwt {
    private ArrayList<Job> arr = new ArrayList<>();
    private Frame frame;
    private TextArea outputArea;

    public AnnealingAwt() {
        frame = new Frame("Job Scheduling with Annealing");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        outputArea = new TextArea();
        outputArea.setEditable(false);
        frame.add(outputArea, BorderLayout.CENTER);

        Button solveButton = new Button("Solve");
        frame.add(solveButton, BorderLayout.SOUTH);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.setText(""); // Clear previous output
                solve();
            }
        });

        createJobInputFields();

        frame.setVisible(true);
    }

    private void createJobInputFields() {
        Panel inputPanel = new Panel(new GridLayout(5, 2));

        Label timeLabel = new Label("Time:");
        TextField timeField = new TextField();
        Label deadlineLabel = new Label("Deadline:");
        TextField deadlineField = new TextField();
        Label holdingLabel = new Label("Holding Cost:");
        TextField holdingField = new TextField();
        Label penaltyLabel = new Label("Penalty:");
        TextField penaltyField = new TextField();

        inputPanel.add(timeLabel);
        inputPanel.add(timeField);
        inputPanel.add(deadlineLabel);
        inputPanel.add(deadlineField);
        inputPanel.add(holdingLabel);
        inputPanel.add(holdingField);
        inputPanel.add(penaltyLabel);
        inputPanel.add(penaltyField);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Add button to add job
        Button addButton = new Button("Add Job");
        inputPanel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int time = Integer.parseInt(timeField.getText());
                    int deadline = Integer.parseInt(deadlineField.getText());
                    int holding = Integer.parseInt(holdingField.getText());
                    int penalty = Integer.parseInt(penaltyField.getText());

                    arr.add(new Job(arr.size() + 1, time, deadline, holding, penalty));
                    outputArea.append("Job added: " + arr.size() + "\n");

                    // Clear input fields
                    timeField.setText("");
                    deadlineField.setText("");
                    holdingField.setText("");
                    penaltyField.setText("");
                } catch (NumberFormatException ex) {
                    // Handle invalid input
                    outputArea.append("Invalid input. Please enter valid integers.\n");
                }
            }
        });
    }

    private void solve() {
        int n = arr.size();
        int[] neighbour = new int[n];
        for (int i = 0; i < n; i++) {
            neighbour[i] = i;
        }
        int min = solve(arr, neighbour);
        outputArea.append("Current cost: " + min + "\n");

        double t = (double) min / 2;
        double c = 0.9;
        int[] minimum = new int[n];
        while (t > 1) {
            neigh(neighbour, arr, t);
            int temp = solve(arr, neighbour);
            t = t * c;
            if (temp < min) {
                min = temp;
                System.arraycopy(neighbour, 0, minimum, 0, neighbour.length);
            }
        }

        outputArea.append("Optimal job sequence:\n");
        for (int i = 0; i < n; i++) {
            Job job = arr.get(minimum[i]);
            outputArea.append("Job ID: " + job.id + "\tTime: " + job.time + "\tDeadline: " + job.deadline
                    + "\tHolding Cost: " + job.holding + "\tPenalty: " + job.penalty + "\n");
        }
        outputArea.append("Total Cost required: " + solve(arr, minimum) + "\n");
    }

    public static int solve(ArrayList<Job> arr, int[] seq) {
        int pen = 0;
        int hold = 0;
        int ptime = 0;
        for (int i = 0; i < arr.size(); i++) {
            ptime = ptime + arr.get(seq[i]).time;
            if (arr.get(seq[i]).deadline > ptime) {
                int temp = arr.get(seq[i]).deadline - ptime;
                hold = hold + (temp * arr.get(seq[i]).holding);
            } else {
                int temp = ptime - arr.get(seq[i]).deadline;
                pen = pen + (temp * (arr.get(seq[i]).penalty));
            }
        }
        return hold + pen;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void neigh(int[] neighbour, ArrayList<Job> arr, double t) {
        double rand = Math.random();
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            float prob = (i + 1) * ((float) 1 / (n - 1));
            int[] b = new int[n];
            System.arraycopy(neighbour, 0, b, 0, neighbour.length);
            if (prob > rand) {
                swap(neighbour, i, i + 1);
                double z = Math.abs(solve(arr, neighbour) - solve(arr, b)) / t;
                if (solve(arr, b) > solve(arr, neighbour)) {
                    // System.out.println("Accepted : " + solve(arr, neighbour));
                    break;
                } else if (Math.random() < Math.exp(-z)) {
                    break;
                } else {
                    // System.out.println("\tRejected : " + solve(arr, neighbour));
                    System.arraycopy(b, 0, neighbour, 0, neighbour.length);
                }
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnnealingAwt();
            }
        });
    }
}
