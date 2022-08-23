package ru.malyshev.inside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.malyshev.inside.dto.MessageDto;
import ru.malyshev.inside.model.Message;
import ru.malyshev.inside.service.MessageService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping()
    public ResponseEntity<List<Message>> addMessage(@RequestBody MessageDto messageDto) {
        return new ResponseEntity<>(messageService.addMessage(messageDto), HttpStatus.OK);
    }
}
