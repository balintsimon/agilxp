package com.drbsimon.client.service;

import com.drbsimon.client.model.DataLine;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class TablePrinterService {
    private final TableGeneratorService tableGeneratorService;

    public void createTable(List<DataLine> lines) {
        List<String> headersList = Arrays.asList("Username", "Apps", "App Last Run", "Background", "Active Theme");

        List<List<String>> rowsList = new ArrayList<>();
        for (DataLine line : lines) {
            List<String> row = new ArrayList<>();
            row.add(line.getUserName());
            row.add(String.join(", ", line.getAppNames()));
            row.add(line.getLastRunApp());
            row.add(line.getBackground());
            row.add(line.getActiveTheme());
            rowsList.add(row);
        }

        tableGeneratorService.generateTable(headersList, rowsList);
    }

}
