import java.util.Objects;

public abstract class Task {
    private String taskId;
    private String descriere;
    public Task(String taskId, String descriere) {
        this.taskId = taskId;
        this.descriere = descriere;
    }

    public String getTaskID() { return taskId; }
    public String getDescriere() { return descriere; }
    public void setTaskID(String taskId) { this.taskId = taskId; }
    public void setDescriere(String descriere) { this.descriere = descriere; }
    public String toString() { //facem o suprascriere
        return taskId + " " + descriere;
    }

    public abstract void execute();

    @Override
    public boolean equals(Object o) {
        if(o == null || o.getClass() != getClass())
            return false;
        Task task = (Task) o;
        return Objects.equals(taskId, task.taskId) && Objects.equals(descriere, task.descriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, descriere);
    }
}