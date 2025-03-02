package typeoftasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String getName, String getDescription, LocalDateTime getStartTime, Duration getDuration, int epicId) {
        super(getName, getDescription, getStartTime, getDuration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}

