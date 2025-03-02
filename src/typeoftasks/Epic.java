package typeoftasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksIds = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String getName, String getDescription, LocalDateTime startTime, Duration duration) {
        super(getName, getDescription, startTime, duration);
    }

    public List<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(List<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

}
