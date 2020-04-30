import java.util.List;

public interface Algorithm {
    void execute(double[][] emission_P, List<Integer> emission_S, double[][] transition_P);
}
