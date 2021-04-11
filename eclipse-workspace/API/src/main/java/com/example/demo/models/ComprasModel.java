package com.example.demo.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Entity
@Table(name = "compras")
public class ComprasModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private UsuarioModel usuario;
	
	@ManyToOne
	@JoinColumn(name="productos_id")
	private ProductosModel productos;
	

	
	
	private Long cantidad;
	private double precioSubTotal;

	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

	
	
	public ProductosModel getProductos() {
		return productos;
	}

	public void setProductos(ProductosModel productos) {
		this.productos = productos;
	}
	
	




	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioSubTotal() {
		return precioSubTotal;
	}

	public void setPrecioSubTotal(double precioSubTotal) {
		this.precioSubTotal = precioSubTotal;
	}

	
	
	
	
	
	


}
