package userInfoUpdate;

import gui.start.login.JdbcUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserInfoExcelManager {
    private String filePath;

    public UserInfoExcelManager(String filePath) {
        this.filePath = filePath;
    }

    // 创建并填充Excel表格
    public void createAndFillExcelWithUserInfo(List<User> users) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("UserInfo");

        // 添加标题行
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("ID");
        titleRow.createCell(1).setCellValue("Account");
        titleRow.createCell(2).setCellValue("Password");

        int rowNum = 1; // 从第一行开始填充数据
        for (User user : users) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getAccount());
            row.createCell(2).setCellValue(user.getPassword());
        }

        // 写入文件
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    // 从数据库中获取用户信息并填充到Excel
    public void fillExcelFromDatabase() throws IOException, SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, Account, Password FROM userinfo";

        try (Connection connection = JdbcUtils.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String account = rs.getString("Account");
                String password = rs.getString("Password");
                users.add(new User(id, account, password));
            }
        }

        createAndFillExcelWithUserInfo(users); // 使用上面定义的方法
    }

    // User 类，用于存储用户信息
    public static class User {
        private int id;
        private String account;
        private String password;

        public User(int id, String account, String password) {
            this.id = id;
            this.account = account;
            this.password = password;
        }

        // Getter 和 Setter 方法
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getAccount() { return account; }
        public void setAccount(String account) { this.account = account; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    // 主方法，用于测试
    public static void main(String[] args) {
        String excelFilePath = "UserInfo.xlsx";
        UserInfoExcelManager userInfoExcelManager = new UserInfoExcelManager(excelFilePath);

        try {
            userInfoExcelManager.fillExcelFromDatabase();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}