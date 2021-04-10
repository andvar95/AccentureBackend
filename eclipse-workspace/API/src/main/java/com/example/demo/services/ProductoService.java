package com.example.demo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.models.ProductosModel;
import com.example.demo.repositories.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	ProductoRepository productorepository;
	
	public ArrayList<ProductosModel> obtenerProductos(){
		return (ArrayList<ProductosModel>) productorepository.findAll();
		
	}
	

	public ProductosModel guardarproducto(ProductosModel producto) {
		
		return productorepository.save(producto);
	}
	
	
}
