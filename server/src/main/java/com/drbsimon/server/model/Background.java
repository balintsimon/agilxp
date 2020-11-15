package com.drbsimon.server.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Background {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "background", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<AppUser> appUsers;
}
