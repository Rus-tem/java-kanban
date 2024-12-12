package Tests;

import TaskManager.InMemoryTaskManager;
import Typeoftasks.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
        //2
    void testAddEpic() {
        inMemoryTaskManager.addEpic(new Epic("Epic1", "Epic1"));
        inMemoryTaskManager.addEpic(new Epic("Epic1", "Epic1"));
        Epic epic1 = inMemoryTaskManager.searchByIdEpic(1);
        Epic epic2 = inMemoryTaskManager.searchByIdEpic(1);
        assertEquals(epic1, epic2);

    }

    @Test
        //3
    void testAddEpicToEpic() {

        inMemoryTaskManager.addEpic(new Epic("Epic1", "Epic1"));
        inMemoryTaskManager.addEpic(new Epic("Epic2", "Epic2"));
        Epic epic1 = inMemoryTaskManager.searchByIdEpic(1);
        Epic epic2 = inMemoryTaskManager.searchByIdEpic(2);
        assertTrue(epic1.getSubtasksIds().isEmpty());
        assertTrue(epic2.getSubtasksIds().isEmpty());
        assertNotEquals(epic1.getId(), epic2.getId());
        assertEquals(epic1.getSubtasksIds(), epic2.getSubtasksIds());
        System.out.println("Подзадачи эпика с ID 1: ");
        inMemoryTaskManager.printSubtasksByEpics(1);
        System.out.println("Подзадачи эпика с ID 2: ");
        inMemoryTaskManager.printSubtasksByEpics(2);


    }
}