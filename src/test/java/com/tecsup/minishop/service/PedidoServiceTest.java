package com.tecsup.minishop.service;

import com.tecsup.minishop.model.Pedido;
import com.tecsup.minishop.model.Product;
import com.tecsup.minishop.repository.PedidoRepository;
import com.tecsup.minishop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    private PedidoRepository pedidoRepository;
    private ProductRepository productRepository;
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        pedidoRepository = mock(PedidoRepository.class);
        productRepository = mock(ProductRepository.class);
        pedidoService = new PedidoService(pedidoRepository, productRepository);
    }

    @Test
    void testCrearPedidoExitoso() {
        Product producto = new Product();
        producto.setId(1L);
        producto.setPrice(10.0);
        producto.setStock(5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido resultado = pedidoService.crearPedido(1L, 2);

        assertNotNull(resultado);
        assertEquals(2, resultado.getQuantity());
        assertEquals(20.0, resultado.getTotal());
        assertEquals(3, producto.getStock()); // Verifica que se restó el stock
    }

    @Test
    void testCrearPedidoStockInsuficiente() {
        Product producto = new Product();
        producto.setId(1L);
        producto.setStock(1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(producto));

        assertThrows(RuntimeException.class, () -> pedidoService.crearPedido(1L, 5));
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void testObtenerTodos() {
        when(pedidoRepository.findAll()).thenReturn(Collections.emptyList());
        List<Pedido> lista = pedidoService.obtenerTodos();
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }
}