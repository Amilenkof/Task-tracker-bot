-- liquebase formatted sql


--changeset aMilenkov:1
CREATE TABLE notification_task (
                                   id SERIAL NOT NULL,
                                   chat_id SERIAL NOT NULL,
                                   task_text TEXT NOT NULL,
                                   perform_date TIMESTAMP NOT NULL
);