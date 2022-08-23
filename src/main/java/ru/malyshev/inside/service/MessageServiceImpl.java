package ru.malyshev.inside.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.malyshev.inside.dto.MessageDto;
import ru.malyshev.inside.model.Message;
import ru.malyshev.inside.model.User;
import ru.malyshev.inside.repository.MessageRepository;
import ru.malyshev.inside.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@Data
public class MessageServiceImpl implements MessageService {
    private final static String PREFIX = "history";
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public List<Message> addMessage(MessageDto messageDto) {

        if (messageDto.getMessage().toLowerCase().contains(PREFIX)) {
            List<Message> userList = userRepository.findByUsername(messageDto.getUsername()).getMessages();
            String[] strings = messageDto.getMessage().split(" ");

            return userList.subList(userList.size() - Integer.parseInt(strings[1]), userList.size());

        } else {
            return List.of(saveMessage(messageDto));
        }
    }

    private Message saveMessage(MessageDto messageDto) {
        User user = userRepository.findByUsername(messageDto.getUsername());
        Message message = new Message();
        message.setMessage(messageDto.getMessage());
        messageRepository.save(message);
        List<Message> messages = user.getMessages();
        messages.add(message);
        user.setMessages(messages);
        return message;
    }
}