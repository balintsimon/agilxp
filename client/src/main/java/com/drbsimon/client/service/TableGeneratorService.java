package com.drbsimon.client.service;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@RequiredArgsConstructor
@Builder
public class TableGeneratorService {

    public void generateTable(List<String> headersList, List<List<String>> rowsList) {
        int padding = 1;
        String verticalDivisor = "|";
        String horizontalDivisor = "-";

        List<Integer> columnWidths = calculateColumnWidths(headersList, rowsList);
        String lineFormat = generateLeftAlignTableFormat(columnWidths, padding, verticalDivisor);
        int lineLength = calculateFullWidthOfTable(columnWidths, padding, verticalDivisor);

        printDataLine(lineFormat, headersList);
        printDivisorLine(horizontalDivisor, lineLength);
        for (List<String> line : rowsList) {
            printDataLine(lineFormat, line);
        }
    }

    private List<Integer> calculateColumnWidths(List<String> headersList, List<List<String>> rowsList) {
        List<Integer> columnSizes = new ArrayList<>();
        for (String item : headersList) {
            columnSizes.add(item.length());
        }

        for (List<String> item : rowsList) {
            for (int i = 0; i < item.size(); i++) {
                Integer width = item.get(i).length();
                if (columnSizes.get(i) < width) {
                    columnSizes.remove(i);
                    columnSizes.add(i, width);
                }
            }
        }
        return columnSizes;
    }

    private String generateLeftAlignTableFormat(List<Integer> columnWidths, int padding, String verticalDivisor) {
        String lineFormat = "";
        for (int i = 0; i < columnWidths.size(); i++) {
            lineFormat += verticalDivisor + " %" + (i+1) + "$-" + (columnWidths.get(i) + padding) + "s";
        }
        lineFormat += verticalDivisor + "\n";
        return lineFormat;
    }

    private int calculateFullWidthOfTable(List<Integer> columnWidths, int padding, String verticalDivisor) {
        return columnWidths.stream().mapToInt(integer -> integer).sum()
                + (columnWidths.size() * padding * 2)
                + (columnWidths.size() * verticalDivisor.length())
                + 1;
    }

    private void printDataLine(String lineFormat, List<String> row) {
        System.out.format(lineFormat, row.get(0), row.get(1), row.get(2), row.get(3), row.get(4));
    }

    private void printDivisorLine(String horizontalDivisor, int lineLength) {
        System.out.println(horizontalDivisor.repeat(lineLength));
    }
}
