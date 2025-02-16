package main;

import taskmanager.*;
import typeoftasks.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        FileBackedTaskManager fileBackedTaskManager = Manager.loadFromFile(new File("E:\\folder_test_git\\text3.txt"));
        System.out.println(fileBackedTaskManager.getAllTasks());
        System.out.println(fileBackedTaskManager.getAllEpics());
        System.out.println(fileBackedTaskManager.getAllSubtasks());
        System.out.println();
        fileBackedTaskManager.addTask(new Task("TASK", "4"));
        fileBackedTaskManager.addTask(new Task("TASK", "5"));
        System.out.println(fileBackedTaskManager.getAllTasks());

    }
}
