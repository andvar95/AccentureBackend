package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.models.ComprasModel;
import com.example.demo.models.ProductosModel;
import com.example.demo.models.UsuarioModel;

@Repository
public interface ComprasRepository extends CrudRepository<ComprasModel,Long>{

	ArrayList<ComprasModel> findByUsuario(UsuarioModel usuarioModel);
	Optional<ComprasModel> findByProductos(ProductosModel productosModel);
}
