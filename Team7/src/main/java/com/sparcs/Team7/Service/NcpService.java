package com.sparcs.Team7.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparcs.Team7.Entity.Book;
import com.sparcs.Team7.Repository.BookRepository;
import com.sparcs.Team7.Repository.ReactionPaperRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class NcpService {
    private final ReactionPaperRepository reactionPaperRepository;

    public NcpService(ReactionPaperRepository reactionPaperRepository) {
        this.reactionPaperRepository = reactionPaperRepository;
    }

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${cloud.aws.credentials.bucket}")
    private String bucketName;

    public Integer saveImageFromUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        int image_key = reactionPaperRepository.RPcount();
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
}
