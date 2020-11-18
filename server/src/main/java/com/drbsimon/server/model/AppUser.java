package com.drbsimon.server.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AppUser {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private UserGroup userGroup;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private MainMenu mainMenu;

    @Enumerated(EnumType.STRING)
    private Theme theme;

    private String lastRunAppName;
}
