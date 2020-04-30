import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Main {

    private static double sw_P;
    private static double nsw_P;
    private static double[][] emission_P = new double[3][3];
    private static List<Integer> emission_S;
    private static double[][] transition_P = new double[3][3];

    public static void main(String[] args) {
        Initiation(args);
        Algorithm algorithm = new HMM();
        algorithm.execute(emission_P, emission_S, transition_P);
    }

    private static void Initiation(String[] args) {
        if (args.length != 1) {
            System.out.println("Please invoke as the following:");
            System.out.println("java -jar xxx.jar <input file>");
            System.exit(0);
        } else {
            try {
                BufferedReader sc = new BufferedReader(new FileReader(new File(args[0])));
                String str = sc.readLine();
                while (str.length() == 0 || str.charAt(0) == '#') str = sc.readLine();
                nsw_P = Double.parseDouble(str);
                sw_P = (1 - nsw_P) / 2;
                str = sc.readLine();
                while (str.length() == 0 || str.charAt(0) == '#') str = sc.readLine();
                for (int i = 0; i < 3; i++) {
                    String[] strs = str.split(" ");
                    for (int j = 0; j < 3; j++) {
                        emission_P[i][j] = Double.parseDouble(strs[j]);
                    }
                    str = sc.readLine();
                }
                while (str.length() == 0 || str.charAt(0) == '#') str = sc.readLine();
                String[] tempStr = str.split(",");
                emission_S = new ArrayList<Integer>();
                for (String s : tempStr) {
                    if (!s.equals("")) emission_S.add(Integer.valueOf(s));
                }
            } catch (FileNotFoundException e1) {
                System.err.println("Can not find input file");
                e1.printStackTrace();
                System.exit(0);
            } catch (IOException e2) {
                System.err.println("Can not resolve input file");
                e2.printStackTrace();
                System.exit(0);
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i == j) {
                        transition_P[i][j] = nsw_P;
                    } else {
                        transition_P[i][j] = sw_P;
                    }
                }
            }
        }
        System.out.println("States: ");
        System.out.println(emission_S);
        System.out.println("Transition probabilities:");
        System.out.println(Arrays.toString(transition_P[0]) + System.lineSeparator() +
                Arrays.toString(transition_P[1]) + System.lineSeparator() +
                Arrays.toString(transition_P[2]));
        System.out.println("Emission probabilities:");
        System.out.println(Arrays.toString(emission_P[0]) + System.lineSeparator() +
                Arrays.toString(emission_P[1]) + System.lineSeparator() +
                Arrays.toString(emission_P[2]));
    }
}
