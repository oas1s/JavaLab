package ru.javalab.hateoas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shoper {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String surname;

    @OneToMany(mappedBy = "shoper")
    private List<Comment> comments;

    private String status;

    public void ban() {
        if (this.status.equals("Active")) {
            this.status = "Banned";
        } else if (this.status.equals("Deleted")) {
            throw new IllegalStateException();
        }
    }
}
