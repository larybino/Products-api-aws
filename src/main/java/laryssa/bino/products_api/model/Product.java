package laryssa.bino.products_api.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Product {
    @NotBlank
    private String name;

    @DecimalMin(value = "0.01", inclusive = true)
    private double price;

    @Min(0)
    @Max(100)
    private int discount_percent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getdiscountPercent() {
        return discount_percent;
    }

    public void setdiscountPercent(int discount_percent) {
        this.discount_percent = discount_percent;
    }

    
}
