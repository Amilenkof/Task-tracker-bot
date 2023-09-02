package pro.sky.telegrambot;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Runner {
    public static void main(String[] args) {


        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        System.out.println("now = " + now);
    }
}
