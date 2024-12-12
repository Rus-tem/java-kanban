package Tests;

import TaskManager.InMemoryTaskManager;
import Typeoftasks.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
        //1
    void testAddTask() {

        inMemoryTaskManager.addTask(new Task("Task1", "Task1")); // -- Задача с ID 1
        Task task1 = inMemoryTaskManager.searchByIdTask(1);
        Task task2 = inMemoryTaskManager.searchByIdTask(1);
        assertEquals(task1, task2);
    }


}