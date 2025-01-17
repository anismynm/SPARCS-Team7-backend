package com.sparcs.Team7.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparcs.Team7.Entity.Book;
import com.sparcs.Team7.Repository.BookRepository;
import com.sparcs.Team7.Repository.ReactionPaperRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class NcpService {

    private final ReactionPaperRepository reactionPaperRepository;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${cloud.aws.credentials.bucket}")
    private String bucketName;

    public Integer saveImageFromUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        Random random = new Random();
        int image_key = 1000 + random.nextInt(9000);
        try (InputStream inputStream = url.openStream()) {
            String fileName = "image_" + image_key + ".png";
            Path tempFile = Files.createTempFile(fileName, ".png");
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, tempFile.toFile());
            amazonS3Client.putObject(putObjectRequest
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            log.info("image uploaded to DB");
            Files.delete(tempFile);

            return image_key;
        }
    }

    public void saveMyImg(byte[] imageData, int id) throws IOException {
        String fileName = "image_" + id + ".png";
        Path tempFile = Files.createTempFile(fileName, ".png");
        try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
            fos.write(imageData);
        } catch (IOException e) {
            throw new IOException("Failed to write image data to file", e);
        }

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, tempFile.toFile());
        amazonS3Client.putObject(putObjectRequest
                .withCannedAcl(CannedAccessControlList.PublicRead));

        log.info("image uploaded to DB");
        Files.delete(tempFile);
    }

    public void deleteImageFromBucket(int image_id) {
        String image_name = "image_" + image_id + ".png";
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, image_name));
        System.out.println("Deleted Successfully");
    }
}
