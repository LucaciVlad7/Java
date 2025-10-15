public abstract class AbstractContainer implements Container {
    protected Task[] tasks;
    protected int size;

    public AbstractContainer(int intialCapacity) {
        tasks = new Task[intialCapacity];
        size = 0;
    }

    @Override
    public int size(){return size;}

    @Override
    public boolean isEmpty() { return size == 0; }

    protected abstract void ensureCapacity();

    @Override
    public abstract void add(Task task);
    @Override
    public abstract Task remove();
}
