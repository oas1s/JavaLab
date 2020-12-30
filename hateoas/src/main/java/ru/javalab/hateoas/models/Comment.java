package ru.javalab.hateoas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private Integer likes;

    @ManyToOne
    @JoinColumn(name = "shoper_id")
    private Shoper shoper;

}
