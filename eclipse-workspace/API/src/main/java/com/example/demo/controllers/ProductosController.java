package com.example.demo.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ProductosModel;
import com.example.demo.models.UsuarioModel;
import com.example.demo.services.ProductoService;

@RestController
@RequestMapping("/productos")
public class ProductosController {
	
	@Autowired
	ProductoService productoservice;
	
	@GetMapping()
	public ArrayList<ProductosModel> obtenerProdutos(){
		return productoservice.obtenerProductos();
	}
	
	
	@PostMapping()
	public ProductosModel crearProductos(@RequestBody ProductosModel producto) {
		return this.productoservice.guardarproducto(producto);
		
	}
	
	
	

}
