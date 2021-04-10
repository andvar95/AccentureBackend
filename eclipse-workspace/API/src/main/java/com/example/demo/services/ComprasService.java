package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.models.ComprasModel;
import com.example.demo.models.ProductosModel;
import com.example.demo.models.UsuarioModel;
import com.example.demo.repositories.ComprasRepository;

@Service
public class ComprasService {
	
	@Autowired
	ComprasRepository comprasrepository;
	
public ComprasModel guardarCompra(ComprasModel compra) {
		
		return comprasrepository.save(compra);
	}


public ArrayList<ComprasModel> detalleCompra(UsuarioModel usuarioModel) {
	
	return comprasrepository.findByUsuario(usuarioModel);
	
}

public Optional<ComprasModel> obtenerCompra(ProductosModel producto) {
	
	return comprasrepository.findByProductos(producto);
	
}

public String eliminarCarrito() {
	comprasrepository.deleteAll();
	return "Carrito Vacio";
}

}
