package ru.malyshev.inside.service;

import ru.malyshev.inside.dto.MessageDto;
import ru.malyshev.inside.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> addMessage(MessageDto messageDto);
}
