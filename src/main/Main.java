package main;

import taskmanager.*;
import typeoftasks.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        FileBackedTaskManager fileBackedTaskManager = Manager.loadFromFile(new File("E:\\folder_test_git\\text5.txt")); // загрузка файла
        System.out.println(fileBackedTaskManager.getAllTasks());
        System.out.println(fileBackedTaskManager.getAllEpics());
        System.out.println(fileBackedTaskManager.getAllSubtasks());
        System.out.println("sort: " + fileBackedTaskManager.getPrioritizedTasks());

        fileBackedTaskManager.addEpic(new Epic("EPIC", "E1", LocalDateTime.now(), Duration.ofMinutes(1)));
        fileBackedTaskManager.addSubtask(new Subtask("SUBTASK", "SUB1", LocalDateTime.of(2001, 2, 2, 14, 40), Duration.ofMinutes(10), 1));
        fileBackedTaskManager.addSubtask(new Subtask("SUBTASK", "SUB1", LocalDateTime.of(2001, 2, 2, 14, 10), Duration.ofMinutes(10), 1));
        fileBackedTaskManager.addTask(new Task("TASK", "TAKS1", LocalDateTime.of(1999, 2, 2, 14, 20), Duration.ofMinutes(25)));
        taskManager.addTask(new Task("TASK", "TAKS1", LocalDateTime.of(1999, 2, 2, 14, 20), Duration.ofMinutes(25)));
        taskManager.addTask(new Task("TASK", "TAKS2", LocalDateTime.of(1999, 2, 2, 14, 30), Duration.ofMinutes(20)));
        taskManager.addTask(new Task("TASK", "TAKS3", LocalDateTime.of(1999, 2, 2, 13, 30), Duration.ofMinutes(10)));
        taskManager.addTask(new Task("TASK", "TAKS4", LocalDateTime.of(1999, 2, 2, 13, 10), Duration.ofMinutes(60)));
        taskManager.addTask(new Task("TASK", "TAKS5", LocalDateTime.of(1999, 2, 2, 14, 10), Duration.ofMinutes(60)));
        taskManager.addEpic(new Epic("EPIC", "E1", LocalDateTime.now(), Duration.ofMinutes(1)));
        taskManager.addEpic(new Epic("EPIC", "E2", LocalDateTime.of(1999, 2, 2, 14, 20), Duration.ofMinutes(1)));
        taskManager.addSubtask(new Subtask("SUBTASK", "SUB1", LocalDateTime.of(2000, 2, 2, 14, 40), Duration.ofMinutes(50), 6));
//
//        System.out.println( taskManager.getAllTasks());
//        System.out.println(taskManager.getAllEpics());
//        System.out.println(taskManager.getAllSubtasks());
//        System.out.println();
//        System.out.println();
//        System.out.println(taskManager.getPrioritizedTasks());

    /*    System.out.println();
        System.out.println();
        System.out.println("печать из майна" + taskManager.getAllTasks());
        System.out.println();
        System.out.println();
        System.out.println("sort: " + taskManager.getPrioritizedTasks());
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("все задачи: ");
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getAllTasks());
*/
    }
}
