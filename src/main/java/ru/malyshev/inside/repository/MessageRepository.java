package ru.malyshev.inside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.malyshev.inside.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
