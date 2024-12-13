package TaskManager;

import Typeoftasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {

    List<Task> historyTasks = new ArrayList<>();

    void add(Task task);

    List<Task> getHistory();
}
