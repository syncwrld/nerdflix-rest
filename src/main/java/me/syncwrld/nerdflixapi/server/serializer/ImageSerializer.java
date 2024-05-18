package me.syncwrld.nerdflixapi.server.serializer;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Base64;

public class ImageSerializer {

    @SneakyThrows
    public static String serializeImageFile(File file) {
        byte[] fileContent = FileUtils.readFileToByteArray(file);
        return Base64.getEncoder().encodeToString(fileContent);
    }

    @SneakyThrows
    public static File deserializeImageFile(String base64Image) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        File file = new File("image.png");
        FileUtils.writeByteArrayToFile(file, decodedBytes);
        return file;
    }

}
