package ru.malyshev.inside.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
}
