package typeoftasks;

import status.Status;

public class Task {
    private int id;
    private typeOfTasks name;
    private String description;
    private Status status;

    public Task( typeOfTasks name, String description) {
        this.name = name;
        this.description = description;
    }



    @Override // переопределяем toString
    public String toString() {
        return "Номер : " + id +
                ", Название : " + name +
                ", Описание : " + description +
                ", Стату : " + status;
    }

    public int getId() {
        return id;
    }

    public typeOfTasks getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(typeOfTasks name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
