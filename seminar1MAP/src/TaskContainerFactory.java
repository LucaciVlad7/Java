public class TaskContainerFactory implements Factory {

    private static TaskContainerFactory instance;

    private TaskContainerFactory() {

    }

    public static TaskContainerFactory getInstance() {
        if(instance==null){
            instance = new TaskContainerFactory();
        }
        return instance;
    }

    @Override
    public Container createContainer(Strategy strategy) {
        switch (strategy) {
            case FIFO-> {
                return new StackContainer(2);
            }
            case LIFO->{
                return new QueueContainer(2);
            }
            default -> {
                throw new IllegalArgumentException("Unknown Strategy");
            }
        }
    }
}
