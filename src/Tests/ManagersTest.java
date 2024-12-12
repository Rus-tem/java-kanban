package Tests;

import TaskManager.Managers;
import TaskManager.*;
import Typeoftasks.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
        //5
    void testManagersGetDefault() {
        TaskManager taskManager = Managers.getDefault();
        taskManager.addTask(new Task("Task1", "Task1"));
        assertEquals("Task1", taskManager.searchByIdTask(1).getName());
        assertEquals("Task1", taskManager.getHistory().getLast().getName());
    }

    @Test
        // 5
    void testManagerGetDefaultHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        historyManager.add(new Task("Task1", "Task1"));
        assertNotNull(historyManager.getHistory());
        System.out.println(historyManager.getHistory());
    }
}