public class StrategyTaskRunner implements TaskRunner {
    private Container container;
    private Strategy strategy;

    public StrategyTaskRunner(Container container, Strategy strategy) {
        this.strategy = strategy;
        this.container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    @Override
    public void executeOneTask(){
        if(!container.isEmpty()){
            Task task= container.remove();
            System.out.println("Executing task: " + task);
        }
    }

    @Override
    public void executeAll() {
        while (!container.isEmpty()) {
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task task) {
        container.add(task);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
