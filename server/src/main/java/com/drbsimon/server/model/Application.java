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
public class Application {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @ManyToMany
    private List<AppUser> appUsers;
}
