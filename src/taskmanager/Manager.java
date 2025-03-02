package taskmanager;

import java.io.File;

public class Manager {

    public static TaskManager getDefault() {

        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager loadFromFile(File loadFromFile) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(loadFromFile); // путь сохранения задач
        fileBackedTaskManager.loadFromFile(loadFromFile);
        return fileBackedTaskManager;
    }
}