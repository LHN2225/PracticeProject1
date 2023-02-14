package com.example.PracticeProject1.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tutorials")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private Boolean published;

    @Override
    public String toString() {
        return "Tutorial [id="+ id + ", title=" + title + ", description="+ description + ", publish=" + published + "]";
    }
}
