package com.example.demo.models;

import java.util.Set;

import javax.persistence.*;



@Entity
@Table(name="Productos")
public class ProductosModel {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	
	private long id;
	private String nombre;
	private double precio;
	@OneToMany(mappedBy="usuario")
	Set<ComprasModel> compras;

	

	public long getId() {
		return id;
	}
	public Set<ComprasModel> getCompras() {
		return compras;
	}
	public void setCompras(Set<ComprasModel> compras) {
		this.compras = compras;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	

}
