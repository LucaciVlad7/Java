public class QueueContainer extends AbstractContainer {
    private int head = 0;
    private int tail = 0;

    public QueueContainer(int intialCapacity) {
        super(intialCapacity);
    }

    @Override
    protected void ensureCapacity() {
        if (size == tasks.length) {
            Task[] newTasks = new Task[tasks.length * 2];
            for (int i = 0; i < size; i++) {
                newTasks[i] = tasks[(head + i) % tasks.length];
            }
            tasks = newTasks;
            head = 0;
            tail = size;
        }
    }

    @Override
    public void add(Task task) {
        ensureCapacity();
        tasks[tail] = task;
        tail = (tail + 1) % tasks.length;
        size++;
    }

    @Override
    public Task remove() {
        if (isEmpty()) return null;
        Task t = tasks[head];
        head = (head + 1) % tasks.length;
        size--;
        return t;
    }
}
