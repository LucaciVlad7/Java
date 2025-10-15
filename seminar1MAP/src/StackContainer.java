public class StackContainer extends AbstractContainer{
    public StackContainer(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    protected void ensureCapacity() {
        if (size == tasks.length) {
            Task[] newTasks = new Task[tasks.length * 2];
            System.arraycopy(tasks, 0, newTasks, 0, tasks.length);
            tasks = newTasks;
        }
    }

    @Override
    public void add(Task task) {
        ensureCapacity();
        tasks[size++] = task;
    }

    @Override
    public Task remove(){
        if(isEmpty())
            return null;
        return tasks[--size];
    }
}
