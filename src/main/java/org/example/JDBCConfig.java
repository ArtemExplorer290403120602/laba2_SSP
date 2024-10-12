package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCConfig {
    private Connection connection = null;
    private final static String GET_PHOTO_BY_ID = "SELECT date FROM photo WHERE id = ?";
    private final static String SELECT_ALL_PHOTO = null;
    private final static String PHOTO_ABOUT_FILE = "G:\\photo";//путь где лежат файлы
    private final static String INSERT_PHOTO_SQL = "INSERT INTO photo (date) VALUES (?)"; // Запрос на вставку

    public JDBCConfig() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Laba_SSP2", "postgres", "root");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Photo> getPhotosFromDirectory() throws IOException {
        List<Photo> photos = new ArrayList<>();
        Files.list(Paths.get(PHOTO_ABOUT_FILE))
                .filter(path -> path.toString().endsWith(".jpg") || path.toString().endsWith(".png")) // проверка на расширения
                .forEach(path -> {
                    try {
                        byte[] data = Files.readAllBytes(path);
                        photos.add(new Photo(null, data));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return photos;
    }

    public void uploadPhotosToDatabase(List<Photo> photos) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PHOTO_SQL)) {
            for (Photo photo : photos) {
                preparedStatement.setBytes(1, photo.getDate());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Photo getPhotoById(Long id) {
        Photo photo = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PHOTO_BY_ID)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                byte[] data = resultSet.getBytes("date");
                photo = new Photo(id, data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photo;
    }
}
