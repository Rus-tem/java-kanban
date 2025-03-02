import typeoftasks.Task;
import taskmanager.*;

import org.junit.jupiter.api.Test;
import typeoftasks.*;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    TaskManager taskManager = Manager.getDefault();

    @Test
        //1
    void testAddTask() {

        taskManager.addTask(new Task(TypeOfTasks.TASK.toString(), "Task1", LocalDateTime.now(), Duration.ofMinutes(10))); // -- Задача с ID 1
        Task task1 = taskManager.searchByIdTask(1);
        Task task2 = taskManager.searchByIdTask(1);
        assertEquals(task1, task2);
    }
}