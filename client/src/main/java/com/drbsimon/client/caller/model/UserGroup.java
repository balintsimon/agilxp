package com.drbsimon.client.caller.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroup {
    private Long id;
    private String name;
    private List<AppUser> appUsers;
}
