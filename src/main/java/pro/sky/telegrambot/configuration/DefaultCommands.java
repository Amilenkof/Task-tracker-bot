package pro.sky.telegrambot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**Класс реализует логику хранения стандартных команд, каждая из которых параметризуется в Application.properties (Value),
 * Команды хранятся в MAP, где ключ тело команды, значение ответ Бота на нее */
@Component
public class DefaultCommands {
    @Value("${start.command.greetings}")
    private String start;
    private final Map<String,String> allCommands = new HashMap<>();

    private DefaultCommands(){
    }
    public Map<String,String> getAllCommands(){
        allCommands.put("/start", start);
        return allCommands;
    }
}
