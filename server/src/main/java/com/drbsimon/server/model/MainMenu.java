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
public class MainMenu {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AppUser appUser;

    @ManyToMany
    @JoinTable(
            name="main_menu_sub_menus",
            joinColumns = @JoinColumn(name = "mainmenu_id"),
            inverseJoinColumns = @JoinColumn(name = "submenu_id")
    )
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private List<SubMenu> subMenus;

    @ManyToMany
    @JoinTable(
            name="menu_icons",
            joinColumns = @JoinColumn(name = "mainmenu_id"),
            inverseJoinColumns = @JoinColumn(name = "icon_id")
    )
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private List<Icon> icons;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    @JsonBackReference
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
