import org.junit.jupiter.api.Test;
import taskmanager.*;
import typeoftasks.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtaskTest {

    TaskManager taskManager = Manager.getDefault();

    @Test
        // 2
    void testAddSubtask() {

        taskManager.addEpic(new Epic(TypeOfTasks.EPIC.toString(), "Epic1"));
        taskManager.addSubtask(new Subtask(TypeOfTasks.SUBTASK.toString(), "Subtask1", 1));
        Subtask subtask1 = taskManager.searchByIdSubtask(2);
        Subtask subtask2 = taskManager.searchByIdSubtask(2);
        assertEquals(subtask1, subtask2);
    }

    @Test
        //4
    void testAddSubtaskToSubtask() {
        taskManager.addEpic(new Epic(TypeOfTasks.EPIC.toString(), "Epic1"));
        taskManager.addSubtask(new Subtask(TypeOfTasks.SUBTASK.toString(), "Subtask1", 1));
        taskManager.addSubtask(new Subtask(TypeOfTasks.SUBTASK.toString(), "Subtask2", 1));
        taskManager.printSubtasksByEpics(1);
        taskManager.getAllSubtasks();

    }

}