public class Subtask extends Task {
    int epicId;

    public Subtask(String getName, String getDescription, int epicId) {
        super(getName, getDescription);
        this.epicId = epicId;
    }
}

