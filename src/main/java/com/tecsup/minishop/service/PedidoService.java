package com.tecsup.minishop.service;

import com.tecsup.minishop.model.Pedido;
import com.tecsup.minishop.model.Product;
import com.tecsup.minishop.repository.PedidoRepository;
import com.tecsup.minishop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductRepository productRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProductRepository productRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Pedido crearPedido(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente para procesar el pedido");
        }

        // Descontar del stock del producto
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        // Calcular el total y guardar el pedido
        Double total = product.getPrice() * quantity;
        Pedido pedido = new Pedido(null, product, quantity, total);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }
}