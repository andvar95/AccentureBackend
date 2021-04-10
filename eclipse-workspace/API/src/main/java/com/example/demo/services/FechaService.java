package com.example.demo.services;

import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.ComprasModel;
import com.example.demo.models.Fechas;
import com.example.demo.repositories.ComprasRepository;
import com.example.demo.repositories.FechasRepository;

@Service
public class FechaService {
	
	@Autowired
	FechasRepository fecharepository;
	
public Fechas guardarFecha(Fechas compra) {
		
		return fecharepository.save(compra);
	}


public ArrayList<Fechas> obtenerFechas() {
	return (ArrayList<Fechas>) fecharepository.findAll();
}

	
	
	
}
