package Tests;

import Status.Status;
import TaskManager.*;
import Typeoftasks.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    TaskManager taskManager = Manager.getDefault();
    HistoryManager historyManager = Manager.getDefaultHistory();

    @Test
        // 6
    void testAddTasksEpicsSubtasks() {
        taskManager.addTask(new Task("Task1", "Task1"));
        taskManager.addEpic(new Epic("Epic1", "Epic1"));
        taskManager.addSubtask(new Subtask("Subtask1", "Subtask1", 2));
        Task task1 = taskManager.searchByIdTask(1);
        Epic epic1 = taskManager.searchByIdEpic(2);
        Subtask subtask1 = taskManager.searchByIdSubtask(3);
        assertNotNull(task1);
        assertNotNull(epic1);
        assertNotNull(subtask1);
    }

    @Test
        // 7
    void testsTasksEpicsSubtasksById() {
        taskManager.addTask(new Task("Task1", "Task1"));
        taskManager.addTask(new Task("Task2", "Task2"));
        taskManager.addEpic(new Epic("Epic1", "Epic1"));
        taskManager.addEpic(new Epic("Epic2", "Epic2"));
        taskManager.addSubtask(new Subtask("Subtask3", "Subtask3", 3));
        taskManager.addSubtask(new Subtask("Subtask4", "Subtask4", 3));
        Task task1 = taskManager.searchByIdTask(1);
        Task task2 = taskManager.searchByIdTask(2);
        task2.setId(1);
        assertEquals(task1.getId(), task2.getId());
        System.out.println(taskManager.getAllTasks());
        Epic epic1 = taskManager.searchByIdEpic(3);
        Epic epic2 = taskManager.searchByIdEpic(4);
        epic2.setId(3);
        assertEquals(epic1.getId(), epic2.getId());
        System.out.println(taskManager.getAllEpics());
        Subtask subtask1 = taskManager.searchByIdSubtask(5);
        Subtask subtask2 = taskManager.searchByIdSubtask(6);
        subtask2.setId(5);
        assertEquals(subtask1.getId(), subtask2.getId());
        System.out.println(taskManager.getAllSubtasks());
    }

    @Test
        //8
    void testsAllFieldsTaskSubtaksEpic() {

        taskManager.addTask(new Task("Task1", "Task1"));
        Task task1 = taskManager.searchByIdTask(1);
        Task task2 = taskManager.searchByIdTask(1);
        assertEquals(task1.getName(), task2.getName());
        assertEquals(task1.getDescription(), task2.getDescription());
        assertEquals(task1.getStatus(), task2.getStatus());
        assertEquals(task1.getId(), task2.getId());
        taskManager.addEpic(new Epic("Epic2", "Epic2"));
        Epic epic1 = taskManager.searchByIdEpic(2);
        Epic epic2 = taskManager.searchByIdEpic(2);
        assertEquals(epic1.getId(), epic2.getId());
        assertEquals(epic1.getName(), epic2.getName());
        assertEquals(epic1.getDescription(), epic2.getDescription());
        assertEquals(epic1.getStatus(), epic2.getStatus());
        taskManager.addSubtask(new Subtask("Subtask3", "Subtask3", 2));
        Subtask subtask1 = taskManager.searchByIdSubtask(3);
        Subtask subtask2 = taskManager.searchByIdSubtask(3);
        assertEquals(subtask1.getId(), subtask2.getId());
        assertEquals(subtask1.getName(), subtask2.getName());
        assertEquals(subtask1.getDescription(), subtask2.getDescription());
        assertEquals(subtask1.getStatus(), subtask2.getStatus());
        assertEquals(subtask1.getEpicId(), subtask2.getEpicId());
    }

    @Test
        //9
    void testAddToHistoryManager() {
        Task task1 = new Task("Task1", "Task1");
        task1.setId(1);
        historyManager.add(task1);
        task1.setStatus(Status.NEW);
        historyManager.add(task1);
        Task task2 = historyManager.getHistory().get(1);
        assertEquals(task1.getName(), task2.getName());
        assertEquals(task1.getDescription(), task2.getDescription());
        assertEquals(task1.getId(), task1.getId());
        System.out.println(historyManager.getHistory());
    }
}