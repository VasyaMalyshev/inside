package ru.malyshev.inside.service;

import ru.malyshev.inside.dto.MessageDto;
import ru.malyshev.inside.model.Message;
import ru.malyshev.inside.model.User;

import java.util.List;

public interface UserService {

    User register(User user);
    User findByUsername(String username);

}
