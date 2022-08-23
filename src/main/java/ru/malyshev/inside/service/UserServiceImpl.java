package ru.malyshev.inside.service;


import ru.malyshev.inside.dto.MessageDto;
import ru.malyshev.inside.model.Message;
import ru.malyshev.inside.model.Role;
import ru.malyshev.inside.model.User;
import ru.malyshev.inside.repository.MessageRepository;
import ru.malyshev.inside.repository.RoleRepository;
import ru.malyshev.inside.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static String PREFIX = "history";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MessageRepository messageRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users founded", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - {} username founded", result.getUsername());
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if(result == null) {
            log.warn("IN findById - no one find by id: {}", id);
            return null;
        }
        log.info("IN findById - {} username founded", result.getUsername());
        return result;
    }

    @Override
    public void addMessage(MessageDto messageDto) {

        User user = userRepository.findByUsername(messageDto.getUsername());
        Message message = new Message();
        message.setMessage(messageDto.getMessage());
        messageRepository.save(message);
        List<Message> messages = user.getMessages();
        messages.add(message);
        user.setMessages(messages);
    }

    @Override
    public List<Message> getMessages(MessageDto messageDto) {
        List<Message> userList = userRepository.findByUsername(messageDto.getUsername()).getMessages();
        if (messageDto.getMessage().toLowerCase().contains(PREFIX)) {
            String[] strings = messageDto.getMessage().split(" ");
            return userList.subList(userList.size() - Integer.parseInt(strings[1]), userList.size());
        }
        return userList;
    }
}