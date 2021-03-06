package com.drbsimon.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private AppUser appUser;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name="main_menu_sub_menus",
            joinColumns = @JoinColumn(name = "mainmenu_id"),
            inverseJoinColumns = @JoinColumn(name = "submenu_id")
    )
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<SubMenu> subMenus;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name="menu_icons",
            joinColumns = @JoinColumn(name = "mainmenu_id"),
            inverseJoinColumns = @JoinColumn(name = "icon_id")
    )
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<Icon> icons;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<Application> applications;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private Background background;

    @OneToMany(mappedBy = "mainMenu", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<Background> backgrounds;
}
