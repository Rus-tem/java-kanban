package Main;

import TaskManager.*;
import Typeoftasks.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        taskManager.addTask(new Task("Task1", "Task1"));
        taskManager.addTask(new Task("Task2", "Task2"));
        taskManager.addTask(new Task("Task3", "Task3"));
        taskManager.addTask(new Task("Task4", "Task4"));
        taskManager.addTask(new Task("Task5", "Task5"));
        taskManager.addTask(new Task("Task6", "Task6"));
        taskManager.addTask(new Task("Task7", "Task7"));
        taskManager.addTask(new Task("Task8", "Task8"));
        taskManager.addTask(new Task("Task9", "Task9"));
        taskManager.addTask(new Task("Task10", "Task10"));
        taskManager.addTask(new Task("Task11", "Task11"));
        taskManager.searchByIdTask(1);
        taskManager.searchByIdTask(2);
        taskManager.searchByIdTask(3);
        taskManager.searchByIdTask(4);
        taskManager.searchByIdTask(5);
        taskManager.searchByIdTask(6);
        taskManager.searchByIdTask(7);
        taskManager.searchByIdTask(8);
        taskManager.searchByIdTask(9);
        taskManager.searchByIdTask(10);
        taskManager.searchByIdTask(11);
        System.out.println(taskManager.getHistory());

    }
}
