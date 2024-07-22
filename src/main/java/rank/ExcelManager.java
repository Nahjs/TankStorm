package rank;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelManager {
    private String filePath;

    public ExcelManager(String filePath) {
        this.filePath = filePath;
    }

    public void exportToExcel(List<Player> players) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Scoreboard");

        int rowNum = 0;
        for (Player player : players) {
            Row row = sheet.createRow(rowNum++);
            int cellNum = 0;
            Cell cell = row.createCell(cellNum++);
            cell.setCellValue(player.getUsername());
            cell = row.createCell(cellNum++);
            cell.setCellValue(player.getScore());
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    public List<Player> importFromExcel() throws IOException {
        List<Player> players = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
        Sheet sheet = workbook.getSheet("Scoreboard");

        for (Row row : sheet) {
            if (row.getRowNum() > 0) { // Skip header
                String username = row.getCell(0).getStringCellValue();
                int score = (int) row.getCell(1).getNumericCellValue();
                Player player = new Player(username);
               players.add(player);
            }
        }
        workbook.close();
        return players;
    }
}