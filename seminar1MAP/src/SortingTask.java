import java.util.Arrays;

public class SortingTask extends Task {
    private int[] numbers;       // vectorul de sortat
    private SortingStrategy sorter;     // strategia de sortare

    public SortingTask(String taskId, String descriere, int[] numbers, SortingStrategy sorter) {
        super(taskId, descriere);
        this.numbers = numbers;
        this.sorter = sorter;
    }

    @Override
    public void execute() {
        if (numbers == null || numbers.length == 0) {
            System.out.println("Vector gol!");
            return;
        }

        // aplică strategia de sortare
        sorter.sort(numbers);

        // afișează vectorul sortat
        System.out.println("Vector sortat: " + Arrays.toString(numbers));
    }
}