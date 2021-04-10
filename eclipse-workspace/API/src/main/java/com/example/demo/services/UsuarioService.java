package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.UsuarioModel;
import com.example.demo.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository usuariorepository;
	
	public Optional<UsuarioModel> obtenerUsuario(Long id) {
		return usuariorepository.findById(id);
	}
	
	public ArrayList<UsuarioModel> ObtenerUsuarios(){
		
		return (ArrayList<UsuarioModel>)usuariorepository.findAll();
		
	}
	
	public UsuarioModel guardarUsuario(UsuarioModel usuario) {
		return usuariorepository.save(usuario);
	}

}
