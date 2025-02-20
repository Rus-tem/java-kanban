import org.junit.jupiter.api.Test;
import taskmanager.*;
import typeoftasks.*;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
        //5
    void testManagersGetDefault() {
        TaskManager taskManager = Manager.getDefault();
        taskManager.addTask(new Task(TypeOfTasks.TASK.toString(), "Task1"));
        assertEquals(TypeOfTasks.TASK.toString(), taskManager.searchByIdTask(1).getName());
        assertEquals(TypeOfTasks.TASK.toString(), taskManager.getHistory().getLast().getName());
    }

    @Test
        // 5
    void testManagerGetDefaultHistory() {
        HistoryManager historyManager = Manager.getDefaultHistory();
        historyManager.add(new Task(TypeOfTasks.TASK.toString(), "Task1"));
        assertNotNull(historyManager.getHistory());
        System.out.println(historyManager.getHistory());
    }
}