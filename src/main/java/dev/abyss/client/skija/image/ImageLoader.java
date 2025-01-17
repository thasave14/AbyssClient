package dev.abyss.client.skija.image;

import io.github.humbleui.skija.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageLoader {

    public static Image getImage(String path) {

        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(path));

            return Image.makeDeferredFromEncodedBytes(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
