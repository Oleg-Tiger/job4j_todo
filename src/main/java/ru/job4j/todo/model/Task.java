package ru.job4j.todo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private final LocalDateTime created = LocalDateTime.now();
    private boolean done = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
