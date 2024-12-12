package Tests;

import Status.Status;
import TaskManager.HistoryManager;
import TaskManager.InMemoryHistoryManager;
import TaskManager.InMemoryTaskManager;
import Typeoftasks.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    HistoryManager historyManager = new InMemoryHistoryManager(10);

    @Test
        // 6
    void testAddTasksEpicsSubtasks() {
        inMemoryTaskManager.addTask(new Task("Task1", "Task1"));
        inMemoryTaskManager.addEpic(new Epic("Epic1", "Epic1"));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask1", "Subtask1", 2));
        Task task1 = inMemoryTaskManager.searchByIdTask(1);
        Epic epic1 = inMemoryTaskManager.searchByIdEpic(2);
        Subtask subtask1 = inMemoryTaskManager.searchByIdSubtask(3);
        assertNotNull(task1);
        assertNotNull(epic1);
        assertNotNull(subtask1);
    }

    @Test
        // 7
    void testsTasksEpicsSubtasksById() {
        inMemoryTaskManager.addTask(new Task("Task1", "Task1"));
        inMemoryTaskManager.addTask(new Task("Task2", "Task2"));
        inMemoryTaskManager.addEpic(new Epic("Epic1", "Epic1"));
        inMemoryTaskManager.addEpic(new Epic("Epic2", "Epic2"));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask3", "Subtask3", 3));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask4", "Subtask4", 3));
        Task task1 = inMemoryTaskManager.searchByIdTask(1);
        Task task2 = inMemoryTaskManager.searchByIdTask(2);
        task2.setId(1);
        assertEquals(task1.getId(), task2.getId());
        System.out.println(inMemoryTaskManager.getAllTasks());
        Epic epic1 = inMemoryTaskManager.searchByIdEpic(3);
        Epic epic2 = inMemoryTaskManager.searchByIdEpic(4);
        epic2.setId(3);
        assertEquals(epic1.getId(), epic2.getId());
        System.out.println(inMemoryTaskManager.getAllEpics());
        Subtask subtask1 = inMemoryTaskManager.searchByIdSubtask(5);
        Subtask subtask2 = inMemoryTaskManager.searchByIdSubtask(6);
        subtask2.setId(5);
        assertEquals(subtask1.getId(), subtask2.getId());
        System.out.println(inMemoryTaskManager.getAllSubtasks());

    }

    @Test
        //8
    void testsAllFieldsTaskSubtaksEpic() {

        inMemoryTaskManager.addTask(new Task("Task1", "Task1"));
        Task task1 = inMemoryTaskManager.searchByIdTask(1);
        Task task2 = inMemoryTaskManager.searchByIdTask(1);
        assertEquals(task1.getName(), task2.getName());
        assertEquals(task1.getDescription(), task2.getDescription());
        assertEquals(task1.getStatus(), task2.getStatus());
        assertEquals(task1.getId(), task2.getId());
        inMemoryTaskManager.addEpic(new Epic("Epic2", "Epic2"));
        Epic epic1 = inMemoryTaskManager.searchByIdEpic(2);
        Epic epic2 = inMemoryTaskManager.searchByIdEpic(2);
        assertEquals(epic1.getId(), epic2.getId());
        assertEquals(epic1.getName(), epic2.getName());
        assertEquals(epic1.getDescription(), epic2.getDescription());
        assertEquals(epic1.getStatus(), epic2.getStatus());
        inMemoryTaskManager.addSubtask(new Subtask("Subtask3", "Subtask3", 2));
        Subtask subtask1 = inMemoryTaskManager.searchByIdSubtask(3);
        Subtask subtask2 = inMemoryTaskManager.searchByIdSubtask(3);
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