package ru.malyshev.inside;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.malyshev.inside.model.Message;
import ru.malyshev.inside.model.User;
import ru.malyshev.inside.service.UserService;


import java.util.ArrayList;
import java.util.List;

public class InsideTests {

    Message message = new Message();
    User user = new User();
    public List<Message> messageList = new ArrayList<>(10);

    UserService userService;

    @BeforeEach
    void setUp() throws Exception {
        message.setId(1L);
        message.setMessage("Сообщение");
        messageList.add(message);
        user.setUsername("test");
        user.setPassword("test");
        user.setMessages(messageList);
    }

    @Test
    void getMessageNonNull() throws Exception {
        Assertions.assertNotNull(message);
        Assertions.assertNotNull(user.getMessages());
    }

    @Test
    void getMessage() throws Exception {
        Assertions.assertEquals("Сообщение", user.getMessages().get(0).getMessage());
    }

    @Test
    void getUser() {
        Assertions.assertEquals("test", user.getUsername());
        Assertions.assertEquals("test", user.getPassword());
    }
}
