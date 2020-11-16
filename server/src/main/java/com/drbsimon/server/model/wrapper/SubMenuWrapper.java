package com.drbsimon.server.model.wrapper;

import com.drbsimon.server.model.SubMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubMenuWrapper {
    List<SubMenu> subMenus;
}
