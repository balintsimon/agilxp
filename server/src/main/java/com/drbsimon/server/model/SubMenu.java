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
public class SubMenu {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    @ManyToMany(mappedBy = "subMenus")
    private List<MainMenu> mainMenus;
}
