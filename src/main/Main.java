package main;

import taskmanager.FileBackedTaskManager;
import taskmanager.HistoryManager;
import taskmanager.Manager;
import taskmanager.TaskManager;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        FileBackedTaskManager fileBackedTaskManager = Manager.loadFromFile(new File("E:\\folder_test_git\\text5.txt")); // загрузка файла

    }
}
