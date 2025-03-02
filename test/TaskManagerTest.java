import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taskmanager.*;
import typeoftasks.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest<T extends ManagersTest> {
    TaskManager taskManager = Manager.getDefault();
    FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(new File("path:\\"));

    @Test
    void testEpics() {
        taskManager.addEpic(new Epic("EPIC", "E1", LocalDateTime.now(), Duration.ofMinutes(1)));
        taskManager.addSubtask(new Subtask("SUBTASK", "SUB1", LocalDateTime.of(2001, 2, 2, 14, 40), Duration.ofMinutes(10), 1));
        Epic epic1 = taskManager.searchByIdEpic(1);
        Subtask subtask1 = taskManager.searchByIdSubtask(2);
        assertEquals(epic1.getId(), subtask1.getEpicId());
        assertEquals(epic1.getStatus(), subtask1.getStatus());
    }

    @Test
    void testCross() {
        taskManager.addTask(new Task("TASK", "TAKS1", LocalDateTime.of(1999, 2, 2, 12, 20), Duration.ofMinutes(15)));
        taskManager.addTask(new Task("TASK", "TAKS2", LocalDateTime.of(1999, 2, 2, 13, 30), Duration.ofMinutes(15)));
        taskManager.addTask(new Task("TASK", "TAKS3", LocalDateTime.of(1999, 2, 2, 14, 30), Duration.ofMinutes(15)));
        assertFalse(taskManager.getPrioritizedTasks().isEmpty());
        System.out.println(taskManager.getPrioritizedTasks());
    }

    @Test
    void history() {
        taskManager.addTask(new Task("TASK", "TAKS1", LocalDateTime.of(1999, 2, 2, 14, 20), Duration.ofMinutes(25)));
        taskManager.addTask(new Task("TASK", "TAKS2", LocalDateTime.of(1999, 2, 2, 12, 0), Duration.ofMinutes(20)));
        taskManager.addTask(new Task("TASK", "TAKS3", LocalDateTime.of(1999, 2, 2, 13, 0), Duration.ofMinutes(20)));
        assertTrue(taskManager.getHistory().isEmpty());
        Task task1 = taskManager.searchByIdTask(1);
        Task task2 = taskManager.searchByIdTask(2);
        Task task3 = taskManager.searchByIdTask(3);
        assertFalse(taskManager.getHistory().isEmpty());
        System.out.println(taskManager.getHistory());
        taskManager.removeByIdTask(1);
        System.out.println(taskManager.getHistory());
        assertFalse(taskManager.getHistory().isEmpty());
        taskManager.removeByIdTask(2);
        assertFalse(taskManager.getHistory().isEmpty());
        System.out.println(taskManager.getHistory());
        taskManager.removeByIdTask(3);
        System.out.println(taskManager.getHistory());
        assertTrue(taskManager.getHistory().isEmpty());


    }

}