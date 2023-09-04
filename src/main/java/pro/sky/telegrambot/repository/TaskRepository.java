package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**Метода возвращает ID последнего добавленного в БД Task*/
    @Query(nativeQuery = true,value = "SELECT id FROM notification_task Order By id desc LIMIT 1;")
    Long getLastId();
/** Метод возвращает List актуальных задач*/
    List<Task> findByPerformDate(LocalDateTime now);
}
