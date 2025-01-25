import typeoftasks.Task;
import taskmanager.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    TaskManager taskManager = Manager.getDefault();

    @Test
        //1
    void testAddTask() {

        taskManager.addTask(new Task("Task1", "Task1")); // -- Задача с ID 1
        Task task1 = taskManager.searchByIdTask(1);
        Task task2 = taskManager.searchByIdTask(1);
        assertEquals(task1, task2);
    }
}