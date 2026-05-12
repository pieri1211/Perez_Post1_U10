package com.universidad.productosservices.service;

import com.universidad.productosservices.domain.Producto;
import com.universidad.productosservices.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    // Code Smell: inyección por campo en lugar de constructor (no final)
    @Autowired
    private ProductoRepository repo; // Code Smell: nombre genérico

    // Code Smell: método largo, múltiples responsabilidades, CC alta
    public Producto procesarProducto(String n, Double p, Integer s,
                                     String cat, boolean activo, String proveedor) {
        Producto producto = new Producto();

        if (n == null || n.equals("")) { // Code Smell: debería usar isBlank()
            throw new IllegalArgumentException("nombre requerido");
        }
        if (p == null) {
            throw new IllegalArgumentException("precio requerido");
        } else if (p <= 0) {
            throw new IllegalArgumentException("precio invalido");
        } else if (p > 999999) {
            throw new IllegalArgumentException("precio excesivo");
        }
        if (s == null || s < 0) {
            throw new IllegalArgumentException("stock invalido");
        }

        producto.setNombre(n);
        producto.setPrecio(p);
        producto.setStock(s);
        // TODO: implementar lógica de categoría y proveedor
        return repo.save(producto);
    }

    public List<Producto> listar() {
        return repo.findAll();
    }

    // Bug: retorna null si el producto no existe en lugar de lanzar excepción
    public Producto buscar(Long id) {
        return repo.findById(id).orElse(null);
    }
}