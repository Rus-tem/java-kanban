package Main;

import TaskManager.*;
import Typeoftasks.Epic;
import Typeoftasks.*;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        inMemoryTaskManager.addTask(new Task("Task1", "Task1")); // -- Задача с ID 1
        inMemoryTaskManager.addTask(new Task("Task2", "Task2")); // -- Задача с ID 2
        inMemoryTaskManager.addTask(new Task("Task3", "Task2")); // -- Задача с ID 2
        inMemoryTaskManager.addTask(new Task("Task4", "Task2")); // -- Задача с ID 2
        inMemoryTaskManager.addTask(new Task("Task5", "Task2")); // -- Задача с ID 2
        inMemoryTaskManager.addTask(new Task("Task6", "Task2")); // -- Задача с ID 2
        inMemoryTaskManager.addEpic(new Epic("Epic7", "epic"));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask8", "Subtask", 7));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask9", "Subtask", 7));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask10", "Subtask", 7));
        inMemoryTaskManager.searchByIdTask(1);
        inMemoryTaskManager.searchByIdTask(2);
        inMemoryTaskManager.searchByIdTask(3);
        inMemoryTaskManager.searchByIdTask(4);
        inMemoryTaskManager.searchByIdTask(5);
        inMemoryTaskManager.searchByIdTask(6);
        inMemoryTaskManager.searchByIdSubtask(8);
        inMemoryTaskManager.searchByIdSubtask(9);
        inMemoryTaskManager.searchByIdSubtask(10);
        inMemoryTaskManager.searchByIdEpic(7);
        System.out.println(inMemoryTaskManager.getHistory());

    }
}
