import java.util.ArrayList;
import java.util.List;

public class HMM implements Algorithm {
    private int[] states;
    private double[] init;

    public HMM() {
        this.states = new int[]{0, 1, 2};
        this.init = new double[]{1.0/3, 1.0/3, 1.0/3};
    }

    public void execute(double[][] emission_P, List<Integer> emission_S, double[][] transition_P) {
        long start = System.currentTimeMillis();
        double[][] calculation = new double[states.length][emission_S.size()];
        int[][] path = new int[states.length][emission_S.size()-1];
        for (int i = 0; i < states.length; i++) {
            calculation[i][0] = init[i] * emission_P[i][emission_S.get(0) - 1];
        }
        for (int i = 1; i < emission_S.size(); i++) {
            for (int m = 0; m < states.length; m++) {
                double temp = 0.0;
                int curr = 0;
                for (int n = 0; n < states.length; n++) {
                    if (calculation[n][i-1] * transition_P[n][m] >= temp) {
                        temp = calculation[n][i - 1] * transition_P[n][m];
                        curr = n;
                    }
                }
                path[m][i-1] = curr;
                calculation[m][i] = temp * emission_P[m][emission_S.get(i) - 1];
            }
        }
        double ans = 0.0;
        int temp = 0;
        for (int i = 0; i < states.length; i++) {
            if (calculation[i][emission_S.size()-1] > ans) {
                ans = calculation[i][emission_S.size()-1];
                temp = i;
            }
        }
        ArrayList<Integer> answer = new ArrayList<Integer>();
        answer.add(temp + 1);
        int index = temp;
        for (int i = emission_S.size()-2; i >= 0; i--) {
            answer.add(0, path[index][i] + 1);
            index = path[index][i];
        }
        System.out.println("Most possible dices sequence:");
        System.out.println(answer);
        System.out.println("Probability of this sequence: ");
        System.out.printf("%.3e", ans);
        System.out.println();
        System.out.println("Time consuming: " + System.lineSeparator() + (System.currentTimeMillis() - start) + "ms");
    }
}
