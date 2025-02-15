package typeoftasks;

public class Subtask extends Task {
    private int epicId;

    public Subtask(typeOfTasks getName, String getDescription, int epicId) {
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

