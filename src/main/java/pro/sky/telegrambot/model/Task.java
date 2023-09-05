package pro.sky.telegrambot.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Getter
    private Long id;
    @Getter
    private Long chatId;
    @Getter
    private String taskText;
    @Getter
    private LocalDateTime performDate;

    public Task(Long chatId, String taskText, LocalDateTime performDate) {
        this.chatId = chatId;
        this.taskText = taskText;
        this.performDate = performDate;
    }
}
