package taskmanager;

import java.io.File;

public class Manager {

    public static TaskManager getDefault() {

        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager loadFromFile(TaskManager taskManager, HistoryManager historyManager) {

        return new FileBackedTaskManager();
    }

    public static FileBackedTaskManager loadFromFile(TaskManager taskManager, HistoryManager historyManager, File loadFromFile) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        fileBackedTaskManager.loadFromFile(loadFromFile);
        return fileBackedTaskManager;
    }

    public static FileBackedTaskManager loadFromFile(File loadFromFile) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        fileBackedTaskManager.loadFromFile(loadFromFile);
        return fileBackedTaskManager;
    }
}