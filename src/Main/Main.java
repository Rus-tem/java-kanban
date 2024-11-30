package Main;

import Typeoftasks.Epic;
import Typeoftasks.*;
import TaskManager.TaskManager;

public class Main {

    static TaskManager ts;
    public static void main(String[] args) {
        ts = new TaskManager();
        ts.addTask(new Task("Task1", "Task1")); // -- Задача с ID 1
        ts.addTask(new Task("Task2", "Task2")); // -- Задача с ID 2

        ts.addEpic(new Epic("Epic1", "Epic1")); // --  Эпик c ID 3
        ts.addSubtask(new Subtask("Subtask1", "Subtask1", 3)); // -- Подзадача с ID 4
        ts.addSubtask(new Subtask("Subtask2", "Subtask2", 3)); // -- Подзадача с ID 5

        ts.addEpic(new Epic("Epic2", "Epic2")); // -- Эпик с ID 6
        ts.addSubtask(new Subtask("Subtask3", "Subtask3", 6)); // -- Подзадача с ID 7
        System.out.println(ts.printAllTasks());
        System.out.println(ts.printAllEpics());
        System.out.println(ts.printAllSubtasks());

        ts.updateTask(new Task("NewTask1", "NewTask1"), 1, "DONE");
        ts.updateTask(new Task("NewTask2", "NewTask2"), 2, "IN_PROGRESS");
        ts.updateSubtask(new Subtask("NewSubtask2", "NewSubtask2", 3), 4, "DONE");
        ts.updateSubtask(new Subtask("NewSubtask2", "NewSubtask2", 3), 5, "DONE");
        ts.updateSubtask(new Subtask("NewSubtask2", "NewSubtask2", 6), 6, "IN_PROGRESS");
        System.out.println(ts.printAllTasks());
        System.out.println(ts.printAllEpics());
        System.out.println(ts.printAllSubtasks());

        ts.removeByIdTask(1); // Удаление по ID
        ts.removeByIdEpic(3); // Удаление по ID
        System.out.println(ts.printAllTasks());
        System.out.println(ts.printAllEpics());
        System.out.println(ts.printAllSubtasks());

    }
}
