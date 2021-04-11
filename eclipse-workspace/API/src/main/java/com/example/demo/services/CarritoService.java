package com.example.demo.services;

import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.ComprasModel;
import com.example.demo.models.CarritoModel;
import com.example.demo.repositories.ComprasRepository;
import com.example.demo.repositories.CarritoRepository;

@Service
public class CarritoService {
	
	@Autowired
	CarritoRepository carritorepository;
	
public CarritoModel guardarCarrito(CarritoModel carrito) {
		
		return carritorepository.save(carrito);
	}

public String borrarCarrito(Long id) {
	carritorepository.deleteById(id);;
	 return "Carrito Borradas";
}

public ArrayList<CarritoModel> obtenerFechas() {
	return (ArrayList<CarritoModel>) carritorepository.findAll();
}

	
	
	
}
