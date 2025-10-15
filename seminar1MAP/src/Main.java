import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        MessageTask[] tasks = new MessageTask[5];

        tasks[0] = new MessageTask("1", "Feedback lab1", "Ai obtinut 9.60", "Gigi", "Ana", LocalDateTime.of(2018, 9, 27, 0, 0));
        tasks[1] = new MessageTask("2", "Feedback lab2", "Ai obtinut 8.50", "Gigi", "Ana", LocalDateTime.of(2018, 10, 1, 0, 0));
        tasks[2] = new MessageTask("3", "Feedback lab3", "Ai obtinut 9.00", "Gigi", "Ana", LocalDateTime.of(2018, 10, 8, 0, 0));
        tasks[3] = new MessageTask("4", "Feedback lab4", "Ai obtinut 10.00", "Gigi", "Ana", LocalDateTime.of(2018, 10, 15, 0, 0));
        tasks[4] = new MessageTask("5", "Feedback lab5", "Ai obtinut 9.30", "Gigi", "Ana", LocalDateTime.of(2018, 10, 22, 0, 0));

        // afisam fiecare task in formatul cerut
        for (MessageTask task : tasks) {
            String dateStr = task.getDate().toLocalDate().toString(); // doar data
            System.out.println(String.format(
                    "id=%s | description=%s | message=%s | from=%s | to=%s | date=%s",
                    task.getTaskID(),
                    task.getDescriere(),
                    task.getMesaj(),
                    task.getFrom(),
                    task.getTo(),
                    dateStr
            ));
        }
        StrategyTaskRunner runner = new StrategyTaskRunner(null, Strategy.FIFO);

        // Cream un vector de MessageTask
        MessageTask[] tasksMessage = new MessageTask[] {
                new MessageTask("1", "Descriere 1", "Salut!", "Alice", "Bob", LocalDateTime.now()),
                new MessageTask("2", "Descriere 2", "Cum esti?", "Charlie", "Dave", LocalDateTime.now()),
                new MessageTask("3", "Descriere 3", "La revedere!", "Eve", "Frank", LocalDateTime.now())
        };

        // Adaugăm task-urile în runner
        for (MessageTask task : tasksMessage) {
            runner.addTask(task);
        }

        // Executăm toate task-urile
        System.out.println("Executing tasks using strategy: " + Strategy.FIFO);
        runner.executeAll();

        StrategyTaskRunner runner2 = new StrategyTaskRunner(null, Strategy.FIFO);
        for (MessageTask task : tasksMessage) {
            runner2.addTask(task);
        }

        // Decorăm cu PrinterTaskRunner
        PrinterTaskRunner printerRunner = new PrinterTaskRunner(runner2);

        System.out.println("\nExecuting tasks using PrinterTaskRunner with strategy: " + Strategy.FIFO);
        printerRunner.executeAll();

        StrategyTaskRunner runner3 = new StrategyTaskRunner(null, Strategy.FIFO);
        for (MessageTask task : tasksMessage) {
            runner3.addTask(task);
        }

        DelayTaskRunner delayRunner = new DelayTaskRunner(runner3);
        System.out.println("\nExecuting tasks using DelayTaskRunner with strategy: " + Strategy.FIFO);
        delayRunner.executeAll();

        StrategyTaskRunner runner4 = new StrategyTaskRunner(null, Strategy.FIFO);
        for (MessageTask task : tasksMessage) {
            runner4.addTask(task);
        }

        PrinterTaskRunner printerDelayRunner = new PrinterTaskRunner(new DelayTaskRunner(runner4));

        System.out.println("\nExecuting tasks using PrinterTaskRunner decorating DelayTaskRunner with strategy: " + Strategy.FIFO);
        printerDelayRunner.executeAll();
    }
}