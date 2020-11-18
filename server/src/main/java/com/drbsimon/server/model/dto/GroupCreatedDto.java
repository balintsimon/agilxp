package com.drbsimon.server.model.dto;

import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreatedDto {
    private AppUser appUser;
    private UserGroup userGroup;
    private Boolean isCreationSuccessful;
}
