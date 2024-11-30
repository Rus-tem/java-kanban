package Typeoftasks;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String getName, String getDescription, int epicId) {
        super(getName, getDescription);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}

