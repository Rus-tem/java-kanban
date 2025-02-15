import org.junit.jupiter.api.Test;
import taskmanager.*;
import typeoftasks.Epic;
import typeoftasks.typeOfTasks;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    TaskManager taskManager = Manager.getDefault();

    @Test
        //2
    void testAddEpic() {

        taskManager.addEpic(new Epic(typeOfTasks.EPIC, "Epic1"));
        taskManager.addEpic(new Epic(typeOfTasks.EPIC, "Epic1"));
        Epic epic1 = taskManager.searchByIdEpic(1);
        Epic epic2 = taskManager.searchByIdEpic(1);
        assertEquals(epic1, epic2);
    }

    @Test
        //3
    void testAddEpicToEpic() {

        taskManager.addEpic(new Epic(typeOfTasks.EPIC, "Epic1"));
        taskManager.addEpic(new Epic(typeOfTasks.EPIC, "Epic2"));
        Epic epic1 = taskManager.searchByIdEpic(1);
        Epic epic2 = taskManager.searchByIdEpic(2);
        assertTrue(epic1.getSubtasksIds().isEmpty());
        assertTrue(epic2.getSubtasksIds().isEmpty());
        assertNotEquals(epic1.getId(), epic2.getId());
        assertEquals(epic1.getSubtasksIds(), epic2.getSubtasksIds());
        System.out.println("Подзадачи эпика с ID 1: ");
        taskManager.printSubtasksByEpics(1);
        System.out.println("Подзадачи эпика с ID 2: ");
        taskManager.printSubtasksByEpics(2);
    }
}