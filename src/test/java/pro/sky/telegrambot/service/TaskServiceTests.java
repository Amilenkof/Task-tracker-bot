package pro.sky.telegrambot.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.util.Assert;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class TaskServiceTests {
    @Mock
    private TaskRepository repository;
    @InjectMocks
    private TaskService service;
    private final String stringBeforeParsing = "22.02.2222 20:00 Сделать домашнюю работу";
    private final Long chatId = 1111L;

    private final Task task = new Task(1L, chatId, "Сделать домашнюю работу",
            LocalDateTime.of(2222, 2, 22, 20, 0));


    @Test
    public void addTaskTests() {
        //test1
//        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));
//
//        Assertions.assertThat(service.addTask(stringBeforeParsing, chatId)).isTrue();
    }
//    public boolean addTask(String command, Long chatId) {
////        Matcher matcher = pattern.matcher(command);
////        if (matcher.matches()) {
////            String group1 = matcher.group(1);
////            String group3 = matcher.group(3);
////            LocalDateTime dateTime = LocalDateTime.parse(group1, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
////            Task task = new Task(chatId, group3, dateTime);
////            Task savedTask = repository.save(task);
////            Long idAddedTask = savedTask.getId();
////            return repository.findById(idAddedTask).isPresent();
////        }
////        logger.info("Не удалось добавить задачу");
////        return false;
////    }
//
}
//@Service
//public class TaskService {
//    private final TaskRepository repository;
//    private final Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
//    private final Logger logger = LoggerFactory.getLogger(TaskService.class);
//
//
//    public TaskService(TaskRepository repository) {
//        this.repository = repository;
//    }
//
//    public boolean addTask(String command, Long chatId) {
//        Matcher matcher = pattern.matcher(command);
//        if (matcher.matches()) {
//            String group1 = matcher.group(1);
//            String group3 = matcher.group(3);
//            LocalDateTime dateTime = LocalDateTime.parse(group1, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
//            Task task = new Task(chatId, group3, dateTime);
//            Task savedTask = repository.save(task);
//            Long idAddedTask = savedTask.getId();
//            return repository.findById(idAddedTask).isPresent();
//        }
//        logger.info("Не удалось добавить задачу");
//        return false;
//    }
//    @Scheduled(cron = "0 0/1 * * * *")
//    public List<Task> schedulledAnswer(){
//        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
//        List<Task> actualTasks = repository.findByPerformDate(now);
//        logger.debug("Актуальные задачи : {}",actualTasks);
//        return actualTasks;
//
//    }
//
//
//}