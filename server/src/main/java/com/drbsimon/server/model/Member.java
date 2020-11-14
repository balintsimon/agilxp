package com.drbsimon.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Group group;

    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @ManyToMany
    private List<Application> applications;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Background background;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Theme theme;
}
