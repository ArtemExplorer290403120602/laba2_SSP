package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JDBCConfig jdbcConfig = new JDBCConfig();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1: Показать количество фотографий");
            System.out.println("2: Загрузить фотографии в базу данных");
            System.out.println("0: Выйти");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    try {
                        List<Photo> photos = jdbcConfig.getPhotosFromDirectory();
                        System.out.println("Количество фотографий: " + photos.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        List<Photo> photosToUpload = jdbcConfig.getPhotosFromDirectory();
                        jdbcConfig.uploadPhotosToDatabase(photosToUpload);
                        System.out.println("Фотографии успешно загружены в базу данных.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return; // Выход из метода main
                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }
}
