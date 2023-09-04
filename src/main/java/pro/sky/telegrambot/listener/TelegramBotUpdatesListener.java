package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import liquibase.pro.packaged.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exceptions.TaskNotAddedException;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.service.TaskService;
import pro.sky.telegrambot.configuration.DefaultCommands;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    @Autowired
    private DefaultCommands defaultCommands;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    @Autowired
    private TaskService taskService;

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }
/**Метод получает ответ из Телеграмм и вызывает либо стандартные команды ,либо добавляет напоминания */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Выполняется Update {}", update);
            String incomingCommand = update.message().text();
            Long chatId = update.message().chat().id();
            if (defaultCommands.getAllCommands().containsKey(incomingCommand)){
                executeStringCommand(incomingCommand,chatId);
                logger.info("Получена стандартная команда");
            }else executeTaskAddCommand(incomingCommand, chatId);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
/**Метод  получает задание добавить в указанный чат задачу (напоминание)    */
    private void executeTaskAddCommand(String incomingCommand, Long chatId) {
        boolean CommandIsOk = taskService.addTask(incomingCommand, chatId);
        logger.info("Получена задача");
        if (CommandIsOk) {
            logger.info("Задача успешно добавлена");
            SendMessage message = new SendMessage(chatId, "Напоминание добавлено");
            SendResponse response = telegramBot.execute(message);
        }
    }
    /**Метод по расписанию (раз в минуту )запрашивает актуальные задачи в БД и возвращает их пользователю в виде LIST<Task>
     *  после чего по каждому Task выводит напоминание клиенту */
    @Scheduled(cron = "0 0/1 * * * *")
    private void executeTaskAddCommand() {
        List<Task> actualTasks = taskService.schedulledAnswer();
            if (!actualTasks.isEmpty()){
            actualTasks.forEach(task -> {
                Long chatId = task.getChatId();
                String answer = task.getTaskText();
                SendMessage message = new SendMessage(chatId, answer);
                SendResponse response = telegramBot.execute(message);
            });
        }
    }

    /**Метод возвращает ответ на стандартную команду которая содержится в DefaultCommands*/
    private void executeStringCommand(String incomingCommand, Long chatId ) {
        String answer = defaultCommands.getAllCommands().get(incomingCommand);
        SendMessage message = new SendMessage(chatId, answer);
        SendResponse response = telegramBot.execute(message);
        if (response.isOk()) {
            logger.info("Ответ отправлен");
        } else {
            logger.info("Возникла ошибка {}", response.errorCode());
        }
    }



}
