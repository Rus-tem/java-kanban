package main;

import taskmanager.*;
import typeoftasks.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        FileBackedTaskManager fileBackedTaskManager = Manager.loadFromFile(taskManager, historyManager);
        FileBackedTaskManager loadFromFile = Manager.loadFromFile(new File("E:\\test.txt"));
        System.out.println( loadFromFile.getAllTasks());
        System.out.println(loadFromFile.getAllEpics());
        System.out.println(loadFromFile.getAllSubtasks());
        System.out.println();
        fileBackedTaskManager.addTask(new Task(typeOfTasks.TASK, "1"));
        fileBackedTaskManager.addTask(new Task(typeOfTasks.TASK,"2"));
        System.out.println(loadFromFile.getAllTasks());
        System.out.println(fileBackedTaskManager.getAllTasks());

    }
}
