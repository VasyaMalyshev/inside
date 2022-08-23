package ru.malyshev.inside.service;

import ru.malyshev.inside.dto.MessageDto;
import ru.malyshev.inside.model.Message;
import ru.malyshev.inside.model.User;

import java.util.List;

public interface UserService {

    User register(User user);
    List<User> getAll();
    User findByUsername(String username);
    User findById(Long id);
    void addMessage(MessageDto messageDto);

    List<Message> getMessages(MessageDto messageDto);
}
