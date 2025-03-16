package main;

import taskmanager.*;
import typeoftasks.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import com.google.gson.JsonElement;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        FileBackedTaskManager fileBackedTaskManager = Manager.loadFromFile(new File("E:\\folder_test_git\\text5.txt")); // загрузка файла


    }
}
