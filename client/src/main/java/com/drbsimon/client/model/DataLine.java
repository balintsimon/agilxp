package com.drbsimon.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataLine {
    private String userName;
    private List<String> appNames;
    private String lastRunApp;
    private String background;
    private String activeTheme;

    public void addAppName(String appName) {
        appNames.add(appName);
    }
}
