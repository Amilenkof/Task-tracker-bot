package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskService {
    private final TaskRepository repository;
    /**
     * Паттерн предстваляет собой выражение вида "01.01.2022 20:00 Сделать домашнюю работу"
     */
    private final Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
    private final Logger logger = LoggerFactory.getLogger(TaskService.class);


    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }


    /**
     * Метод реализует логику добавления в БД TASK - Полученного в виде строки и разделенного на группы с помощью Matcher
     */
    public Optional<Task> addTask(String command, Long chatId) {
        Matcher matcher = pattern.matcher(command);
        if (matcher.matches()) {
            String group1 = matcher.group(1);
            String group3 = matcher.group(3);
            LocalDateTime dateTime = LocalDateTime.parse(group1, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            Task task = new Task(1L,chatId, group3, dateTime);
            return Optional.of(repository.save(task));
        }
        logger.info("Не удалось добавить задачу");

        return Optional.empty();
    }

/**Метод получает список актуальных задач
 * cron = "0 0/1 * * * *" - расписание выполнения раз в минуту
 * */
    @Scheduled(cron = "0 0/1 * * * *")
    public List<Task> schedulledAnswer() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Task> actualTasks = repository.findByPerformDate(now);
        logger.debug("Актуальные задачи : {}", actualTasks);
        return actualTasks;

    }


}

