package com.example.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;


import com.example.demo.models.CarritoModel;
import com.example.demo.models.UsuarioModel;


public interface CarritoRepository extends CrudRepository<CarritoModel,Long>{
	


}
