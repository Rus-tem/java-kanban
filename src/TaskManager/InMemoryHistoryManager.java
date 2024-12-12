package TaskManager;

import Typeoftasks.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> historyTasks = new ArrayList<>();
    private int sizeHistoryMemory;

    public InMemoryHistoryManager(int sizeHistoryMemory) {
        this.sizeHistoryMemory = sizeHistoryMemory;
    }

    @Override
    public void add(Task task) {
        if (historyTasks.size() < sizeHistoryMemory) {
            historyTasks.add(task);
        } else if (historyTasks.size() >= sizeHistoryMemory) {
            historyTasks.addFirst(task);
            historyTasks.removeLast();
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyTasks;
    }
}
