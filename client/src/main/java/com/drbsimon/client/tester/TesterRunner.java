package com.drbsimon.client.tester;

import com.drbsimon.client.caller.model.AppUser;
import com.drbsimon.client.caller.model.Application;
import com.drbsimon.client.caller.model.Theme;
import com.drbsimon.client.caller.model.dto.GroupCreatedDto;
import com.drbsimon.client.service.ServerCallerService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class TesterRunner {
    private final ServerCallerService serverCallerService;
    private Random random = new Random();

    /**
     * Run all stages of the homework exercise automatically.
     */
    public void run() {
        GroupCreatedDto adminGroup = stageOne();
        if (adminGroup != null) {
            stageTwo(adminGroup);
        }
        runRandomUserApps();
        serverCallerService.printAllUsers();
    }

    /**
     * Create dummy admin and group with custom menu.
     *
     * @return Created admin user and group that it admins.
     */
    private GroupCreatedDto stageOne() {
        String adminName = "Group Admin";
        String groupName = "Household";
        Theme chosenTheme = Theme.DAY;
        String backgroundName = "Office";
        String iconName = "Chair";

        String app1 = adminName + "'s Settings";
        String app2 = "IntelliJ IDEA";

        log.info("1st STAGE: register new admin");
        GroupCreatedDto newGroupCreated = serverCallerService.registerNewGroup(groupName, adminName);
        if (newGroupCreated.getIsCreationSuccessful()) {
            AppUser admin = newGroupCreated.getAppUser();
            System.out.println("User (admin) with name " + admin.getName() + " created.");
            System.out.println("Add applications to user main menu.");
            serverCallerService.addNewApplication(app1, admin.getId());
            serverCallerService.addNewApplication(app2, admin.getId());
            admin = customizeUserMenu(admin, chosenTheme, backgroundName, iconName);
            newGroupCreated.setAppUser(admin);
            return newGroupCreated;
        }
        return null;
    }

    /**
     * Create random users with random custom menus and apps. Only an admin can create users.
     *
     * @param groupCreatedDto Needs admin and group where users are members.
     */
    private void stageTwo(GroupCreatedDto groupCreatedDto) {
        log.info("2nd STAGE: register new users");
        AppUser admin = groupCreatedDto.getAppUser();

        List<String> userNames = generateRandomUserNames();
        List<String> applicationNames = generateRandomApplicationNames();
        List<String> backgroundNames = generateRandomBackgroundNames();
        List<String> iconNames = generateRandomIconNames();

        for (int i = 0; i < userNames.size(); i++) {
            String userName = userNames.get(i);
            Theme theme = generateRandomTheme();
            String backgroundName = backgroundNames.get(i);
            String iconName = iconNames.get(i);
            AppUser newUser = createAndCustomizeNewUser(admin, userName, theme, backgroundName, iconName);
            if (newUser.getId() != null) {
                serverCallerService.addNewApplication(applicationNames.get(i * 2), newUser.getId());
                serverCallerService.addNewApplication(applicationNames.get(i * 2 + 1), newUser.getId());
            }
        }
    }

    /**
     * Get all users from server and run a random app of each user.
     */
    private void runRandomUserApps() {
        log.info("RUN RANDOM APP of each user");
        List<AppUser> allUsers = serverCallerService.getAllUsers();
        for (AppUser user : allUsers) {
            runRandomApplication(user);
        }
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
     *
     * @param user           AppUser who's MainMenu should be customized
     * @param theme          Theme to switch to
     * @param backgroundName Background name to add and swtich to
     * @param iconName       Icon name to add
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

    private Theme generateRandomTheme() {
        return Theme.values()[random.nextInt(Theme.values().length)];
    }

    private List<String> generateRandomUserNames() {
        List<String> userNames = Arrays.asList(
                "Smith", "Kevin", "Tracy", "Jenny", "Tom", "Gabi", "Sofia", "Katy", "Zoe", "Jodie", "Adam", "Greg"
        );
        return randomizeList(userNames);
    }

    private List<String> generateRandomApplicationNames() {
        List<String> appNames = Arrays.asList(
                "IDEA", "Chrome", "Word", "Firefox", "IrfanView", "Photoshop", "InDesign", "Mail", "Zoom", "Steam",
                "VR viewer", "WordFinder", "Photos", "Excel", "Calculator", "Settings", "App Killer", "Glances",
                "Terminal", "DockerHub", "e-Szigno", "git", "Visual Studio Code", "Discord", "Libre Office",
                "PyCharm", "WebStorm", "Android Studio", "GNU", "Calibre", "YouTube", "Camera", "Ski Tracks"
        );
        return randomizeList(appNames);
    }

    private List<String> generateRandomBackgroundNames() {
        List<String> backgroundNames = Arrays.asList(
                "Hike", "Hills", "Snowboard", "Snow", "Scuba Diver", "Space", "intel inside", "random cat", "my_bike",
                "stray dog", "paraglider", "KFC", "no trespassers", "WARNING", "CV", "Basil pot", "Window", "Apple"
        );
        return randomizeList(backgroundNames);
    }

    private List<String> generateRandomIconNames() {
        List<String> iconNames = Arrays.asList(
                "Stripes", "Window", "Apple", "Guitar", "Dot", "Red dot", "Hamburger", "Exclamation mark", "Square",
                "Question mark", "Eyes", "Ring", "Black Point", "White square", "Noise", "Leaf"
        );
        return randomizeList(iconNames);
    }

    private List<String> randomizeList(List<String> list) {
        Collections.shuffle(list);
        return list;
    }
}
