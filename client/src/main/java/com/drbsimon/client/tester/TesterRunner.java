package com.drbsimon.client.tester;

import com.drbsimon.client.caller.model.AppUser;
import com.drbsimon.client.caller.model.Application;
import com.drbsimon.client.caller.model.Theme;
import com.drbsimon.client.caller.model.UserGroup;
import com.drbsimon.client.caller.model.dto.GroupCreatedDto;
import com.drbsimon.client.caller.model.dto.NewGroupDto;
import com.drbsimon.client.service.ServerCallerService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class TesterRunner {
    private final ServerCallerService serverCallerService;
    private Random random = new Random();

    public void run() {
        GroupCreatedDto adminGroup = stageOne();
        if (adminGroup != null) {
            stageTwo(adminGroup);
        }
    }

    private GroupCreatedDto stageOne() {
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
            admin = customizeUserMenu(admin, chosenTheme, backgroundName, iconName);
            serverCallerService.printAllUsers();
            newGroupCreated.setAppUser(admin);
            return newGroupCreated;
        }
        return null;
    }

    private void stageTwo(GroupCreatedDto groupCreatedDto) {
        System.out.println("2. STAGE: register new users");
        AppUser admin = groupCreatedDto.getAppUser();
        UserGroup userGroup = groupCreatedDto.getUserGroup();

        String userName = "New user";
        Theme theme = Theme.values()[random.nextInt(Theme.values().length)];
        String backgroundName = "User background";
        String iconName = "User icon";
        AppUser newUser = createAndCustomizeNewUser(admin, userName, theme, backgroundName, iconName);
        serverCallerService.addNewApplication("User app1", newUser.getId());
        serverCallerService.addNewApplication("User app2", newUser.getId());
        serverCallerService.addNewApplication("User app3", newUser.getId());
        newUser = serverCallerService.getUserBy(newUser.getId());
        runRandomApplication(newUser);
    }

    private AppUser createAndCustomizeNewUser(AppUser admin, String userName, Theme theme, String backgroundName, String iconName) {
        AppUser newUserCreated = serverCallerService.registerNewUser(admin.getName(), userName, admin.getUserGroup().getId());
        if (newUserCreated.getId() != null) {
            return customizeUserMenu(newUserCreated, theme, backgroundName, iconName);
        }
        return null;
    }

    /**
     * Customize user's menu with switching theme, adding and switching to new background and adding an icon.
     * @param user AppUser who's MainMenu should be customized
     * @param theme Theme to switch to
     * @param backgroundName Background name to add and swtich to
     * @param iconName Icon name to add
     */
    private AppUser customizeUserMenu(AppUser user, Theme theme, String backgroundName, String iconName) {
        System.out.println("Customizing user " + user.getName() + "'s main menu.");
        serverCallerService.changeUserTheme(user.getName(), user.getId(), theme);
        serverCallerService.addNewBackground(user.getId(), backgroundName);
        serverCallerService.changeBackground(user.getId(), backgroundName);
        serverCallerService.addNewIcon(iconName, user.getId());
        return serverCallerService.getUserBy(user.getId());
    }


    private void runRandomApplication(AppUser user) {
        List<Application> apps = user.getMainMenu().getApplications();
        if (apps.size() > 0) {
            int randomAppIndex = random.nextInt(apps.size());
            Application application = apps.get(randomAppIndex);
            String appName = serverCallerService.runApplication(user.getId(), application.getName(), application.getId());
            System.out.println(String.format("%s ran app titled '%s'", user.getName(), appName));
        }
    }
}
