package com.example.demo.models;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private Long cedula;
 

	private String nombre;
    private String email;
    private String direccion;
    @OneToMany(mappedBy="productos")
    Set<ComprasModel> compras;
  
    
   



    public Long getId() {
 		return id;
 	}

 	public void setId(Long id) {
 		this.id = id;
 	}

    public Set<ComprasModel> getCompras() {
		return compras;
	}

	public void setCompras(Set<ComprasModel> compras) {
		this.compras = compras;
	}

	public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
    
}

