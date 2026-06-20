package com.tecsup.minishop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    // --- CONSTRUCTORES MANUALES ---
    public Product() {}

    public Product(Long id, String name, Double price, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // --- GETTERS Y SETTERS MANUALES (Inmunes a errores de Lombok) ---
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return this.price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return this.stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    // --- PATRÓN BUILDER MANUAL (Para que no fallen tus clases de test) ---
    public static class ProductBuilder {
        private Long id;
        private String name;
        private Double price;
        private Integer stock;

        public ProductBuilder id(Long id) { this.id = id; return this; }
        public ProductBuilder name(String name) { this.name = name; return this; }
        public ProductBuilder price(Double price) { this.price = price; return this; }
        public ProductBuilder stock(Integer stock) { this.stock = stock; return this; }

        public Product build() {
            return new Product(id, name, price, stock);
        }
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }
}