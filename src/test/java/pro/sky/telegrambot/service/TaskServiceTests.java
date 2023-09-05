package pro.sky.telegrambot.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskServiceTests {
    @LocalServerPort
    int port;
    @Autowired
    private TaskRepository repository;
    @Autowired
    private TaskService service;
    private final String stringBeforeParsing = "22.02.2222 20:00 Сделать домашнюю работу";
    private final Long chatId = 1111L;
    private final Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");


    private final Task task = new Task(1L, chatId, "Сделать домашнюю работу",
            LocalDateTime.of(2222, 2, 22, 20, 0));


    @Test
        void addTaskTests() {

        //test1 positive
        Optional<Task> taskOptional = service.addTask(stringBeforeParsing, chatId);
        Task actual = taskOptional.get();
        Assertions.assertThat(actual.getTaskText().equals(task.getTaskText())
                              && actual.getChatId().equals(task.getChatId())
                              && actual.getPerformDate().equals(task.getPerformDate()))
                .isTrue();

        //test2 negative
        Optional<Task> taskOptionalUncorrect = service.addTask("Uncorrect string for Data", chatId);
        Assertions.assertThat(taskOptionalUncorrect.isEmpty()).isTrue();
    }

    @Test
    void schedulledAnswerTests() throws InterruptedException {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        CountDownLatch latch = new CountDownLatch(1);
        scheduler.schedule(() -> {
            service.schedulledAnswer();
            latch.countDown();
        }, new CronTrigger("0 * * * * *"));
        boolean executed = latch.await(2, TimeUnit.MINUTES);
        scheduler.shutdown();
        Assertions.assertThat(executed).isTrue();

    }

}
