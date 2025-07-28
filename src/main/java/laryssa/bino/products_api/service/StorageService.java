package laryssa.bino.products_api.service;

import java.io.IOException;
import java.util.List;

import laryssa.bino.products_api.model.Product;

public interface StorageService {
    void save(String storeName, List<Product> products) throws IOException;
    List<Product> load(String storeName) throws IOException;
}
