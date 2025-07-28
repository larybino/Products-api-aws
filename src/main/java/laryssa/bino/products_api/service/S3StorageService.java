package laryssa.bino.products_api.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import laryssa.bino.products_api.model.Product;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "s3")
public class S3StorageService implements StorageService {

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 s3;

    public S3StorageService() {
        this.s3 = AmazonS3ClientBuilder.defaultClient();
    }

    @Override
    public void save(String storeName, List<Product> products) throws IOException {
        String json = new ObjectMapper().writeValueAsString(products);
        s3.putObject(bucket, storeName + ".json", json);
    }

    @Override
    public List<Product> load(String storeName) throws IOException {
        try {
            S3Object object = s3.getObject(bucket, storeName + ".json");
            return new ObjectMapper().readValue(object.getObjectContent(),
                new TypeReference<List<Product>>() {});
        } catch (AmazonS3Exception e) {
            throw new FileNotFoundException("Stroe not found");
        }
    }
}

