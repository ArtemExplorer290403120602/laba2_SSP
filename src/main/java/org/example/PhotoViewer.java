package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class PhotoViewer extends JFrame {
    public PhotoViewer(Photo photo) {
        setTitle("Photo Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Центрирование окна

        try {
            // Конвертация byte[] в BufferedImage
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(photo.getDate()));
            JLabel label = new JLabel(new ImageIcon(img));
            add(label, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка загрузки изображения: " + e.getMessage());
        }

        setVisible(true);
    }
}
