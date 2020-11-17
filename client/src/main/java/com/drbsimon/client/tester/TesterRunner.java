package com.drbsimon.client.tester;

import com.drbsimon.client.caller.model.AppUser;
import com.drbsimon.client.caller.model.Theme;
import com.drbsimon.client.caller.model.dto.GroupCreatedDto;
import com.drbsimon.client.service.ServerCallerService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class TesterRunner {
    private final ServerCallerService serverCallerService;

    public void run() {
        stageOne();
    }

    private void stageOne() {
        String adminName = "Dummy Admin";
        String groupName = "Dummy Group";
        Theme chosenTheme = Theme.DAY;
        String backgroundName = "Dummy background";
        String iconName = "Dummy icon";

        String app1 = adminName + "'s Settings";
        String app2 = "IntelliJ IDEA";

        System.out.println("1. STAGE: register new admin");
        GroupCreatedDto newGroupCreated = serverCallerService.registerNewGroup(groupName, adminName);
        if (newGroupCreated.getIsCreationSuccessful()) {
            AppUser admin = newGroupCreated.getAppUser();
            System.out.println("User (admin) with name " + admin.getName() + " created.");
            System.out.println("Add applications to user main menu.");
            serverCallerService.addNewApplication(app1, admin.getId());
            serverCallerService.addNewApplication(app2, admin.getId());
            customizeUserMenu(admin, chosenTheme, backgroundName, iconName);
            serverCallerService.printAllUsers();
        }

    }

    /**
     * Customize user's menu with switching theme, adding and switching to new background and adding an icon.
     * @param user AppUser who's MainMenu should be customized
     * @param theme Theme to switch to
     * @param backgroundName Background name to add and swtich to
     * @param iconName Icon name to add
     */
    private void customizeUserMenu(AppUser user, Theme theme, String backgroundName, String iconName) {
        System.out.println("Customizing user " + user.getName() + "'s main menu.");
        serverCallerService.changeUserTheme(user.getName(), user.getId(), theme);
        serverCallerService.addNewBackground(user.getId(), backgroundName);
        serverCallerService.changeBackground(user.getId(), backgroundName);
        serverCallerService.addNewIcon(iconName, user.getId());
    }
}
