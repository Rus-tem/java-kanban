package main;

import taskmanager.*;
import typeoftasks.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        taskManager.addTask(new Task("Task1", "Task1"));
        taskManager.addTask(new Task("Task2", "Task2"));
        taskManager.addEpic(new Epic("Epic1", "EmptyEpic1"));
        taskManager.addEpic(new Epic("Epic2", "Epic2"));
        taskManager.addSubtask(new Subtask("Subtask1", "Subtask4", 4));
        taskManager.addSubtask(new Subtask("Subtask2", "Subtask3", 4));
        taskManager.addSubtask(new Subtask("Subtask3", "Subtask3", 4));
        taskManager.searchByIdTask(1);
        taskManager.searchByIdEpic(4);
        taskManager.searchByIdSubtask(5);
        taskManager.searchByIdEpic(4);
        taskManager.searchByIdEpic(3);
        taskManager.searchByIdSubtask(5);
        taskManager.searchByIdSubtask(6);
        System.out.println(taskManager.getHistory());
        taskManager.removeByIdEpic(4);
        taskManager.removeByIdSubtask(5);
        System.out.println(taskManager.getHistory());


    }
}
