package laryssa.bino.products_api.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import laryssa.bino.products_api.model.Product;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "local", matchIfMissing = true)
public class LocalStorageService implements StorageService {

    private final Path baseDir = Paths.get("data");

    public LocalStorageService() throws IOException {
        Files.createDirectories(baseDir);
    }

    @Override
    public void save(String storeName, List<Product> products) throws IOException {
        String json = new ObjectMapper().writeValueAsString(products);
        Files.write(baseDir.resolve(storeName + ".json"), json.getBytes());
    }

    @Override
    public List<Product> load(String storeName) throws IOException {
        Path path = baseDir.resolve(storeName + ".json");
        if (!Files.exists(path)) throw new FileNotFoundException("Store not found");
        return new ObjectMapper().readValue(Files.newInputStream(path),
            new TypeReference<List<Product>>() {});
    }
}

