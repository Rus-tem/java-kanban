package tests;

import taskmanager.*;
import typeoftasks.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
        //5
    void testManagersGetDefault() {
        TaskManager taskManager = Manager.getDefault();
        taskManager.addTask(new Task("Task1", "Task1"));
        assertEquals("Task1", taskManager.searchByIdTask(1).getName());
        assertEquals("Task1", taskManager.getHistory().getLast().getName());
    }

    @Test
        // 5
    void testManagerGetDefaultHistory() {
        HistoryManager historyManager = Manager.getDefaultHistory();
        historyManager.add(new Task("Task1", "Task1"));
        assertNotNull(historyManager.getHistory());
        System.out.println(historyManager.getHistory());
    }
}