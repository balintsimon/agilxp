package com.drbsimon.client.service;

import com.drbsimon.client.caller.model.AppUser;
import com.drbsimon.client.caller.model.Application;
import com.drbsimon.client.model.DataLine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class Util {

    public static List<DataLine> convertAppUsersToDataLine(List<AppUser> allUsers) {
        List<DataLine> allData = new ArrayList<>();

        for (AppUser actualUser : allUsers) {
            DataLine newLine = new DataLine();
            newLine.setUserName(actualUser.getName());
            List<Application> actualUserApps = actualUser.getMainMenu().getApplications();
            List<String> userAppNames = new ArrayList<>();
            for (Application app : actualUserApps) {
                userAppNames.add(app.getName());
            }
            newLine.setAppNames(userAppNames);
            newLine.setLastRunApp(actualUser.getLastRunAppName());
            newLine.setBackground(actualUser.getMainMenu().getBackground().getName());
            newLine.setActiveTheme(actualUser.getTheme().name());

            allData.add(newLine);
        }
        return allData;
    }

    public static List<String> randomizeList(List<String> list) {
        Collections.shuffle(list);
        return list;
    }
}
