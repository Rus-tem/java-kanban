import org.junit.jupiter.api.Test;
import taskmanager.*;
import typeoftasks.*;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
        //5
    void testManagersGetDefault() {
        TaskManager taskManager = Manager.getDefault();
        taskManager.addTask(new Task(TypeOfTasks.TASK.toString(), "Task1", LocalDateTime.now(), Duration.ofMinutes(10)));
        assertEquals(TypeOfTasks.TASK.toString(), taskManager.searchByIdTask(1).getName());
        assertEquals(TypeOfTasks.TASK.toString(), taskManager.getHistory().getLast().getName());
    }

    @Test
        // 5
    void testManagerGetDefaultHistory() {
        HistoryManager historyManager = Manager.getDefaultHistory();
        historyManager.add(new Task(TypeOfTasks.TASK.toString(), "Task1", LocalDateTime.now(), Duration.ofMinutes(10)));
        assertNotNull(historyManager.getHistory());
        System.out.println(historyManager.getHistory());
    }
}