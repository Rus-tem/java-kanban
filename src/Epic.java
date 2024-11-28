import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> subtasksIds = new ArrayList<>();

    public Epic(String getName, String getDescription) {
        super(getName, getDescription);

    }
}
