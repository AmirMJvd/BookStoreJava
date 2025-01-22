package model;

import java.io.*;
import java.util.HashMap;

public class UserDataManager {
    private static final String FILE_PATH = "users.txt";  // مسیر فایل کاربران
    private HashMap<String, User> userDatabase = new HashMap<>();

    // سازنده برای بارگذاری داده‌های کاربران از فایل
    public UserDataManager() {
        loadUserData();
    }

    // متد برای بارگذاری داده‌های کاربران از فایل
    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String role = parts[2];
                    userDatabase.put(username, new User(username, password, role));
                } else {
                    System.out.println("خطا: خط نادرست در فایل داده‌ها: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("خطا در بارگذاری داده‌های کاربران: " + e.getMessage());
        }
    }

    // متد برای اضافه کردن کاربر جدید به پایگاه داده
    public void addUser(String username, String password, String role) throws IOException {
        if (userDatabase.containsKey(username)) {
            throw new IllegalArgumentException("کاربر از قبل وجود دارد.");
        }

        User newUser = new User(username, password, role);
        userDatabase.put(username, newUser);

        // بررسی وجود فایل و ایجاد آن در صورت عدم وجود
        File file = new File(FILE_PATH);

        // اگر فایل وجود ندارد، مسیر والد را می‌سازد و فایل را ایجاد می‌کند
        if (!file.exists()) {
            File parentDir = file.getParentFile();  // گرفتن پوشه والد
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();  // ساخت پوشه‌های والد در صورت عدم وجود
            }
            file.createNewFile();  // ایجاد فایل اگر وجود ندارد
        }

        // نوشتن اطلاعات کاربر جدید در فایل
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(username + ":" + password + ":" + role + "\n");
        }
    }

    // متد برای بررسی اعتبار کاربر
    public boolean isValidUser(String username, String password, String role) {
        User user = userDatabase.get(username);
        return user != null && user.getPassword().equals(password) && user.getRole().equals(role);
    }
}
