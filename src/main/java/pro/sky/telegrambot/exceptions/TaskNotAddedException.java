package pro.sky.telegrambot.exceptions;

public class TaskNotAddedException extends RuntimeException {
    public TaskNotAddedException(String message) {
        super(message);
    }
}
