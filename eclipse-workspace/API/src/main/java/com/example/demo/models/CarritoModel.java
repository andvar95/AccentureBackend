package com.example.demo.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "carrito")
public class CarritoModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
	


	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private Date fechaIncio;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date fechaFinEditar;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date fechaFinEliminar;
	private Double totalCompra;
	
	@OneToOne(mappedBy = "carrito")
    private UsuarioModel usuario;
	
	
	
	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

	public Date getFechaFinEliminar() {
		return fechaFinEliminar;
	}

	public void setFechaFinEliminar(Date fechaFinEliminar) {
		this.fechaFinEliminar = fechaFinEliminar;
	}

	public Date getFechaIncio() {
		return fechaIncio;
	}

	public void setFechaIncio(Date fechaIncio) {
		this.fechaIncio = fechaIncio;
	}

	public Date getFechaFinEditar() {
		return fechaFinEditar;
	}

	public void setFechaFinEditar(Date fechaFinEditar) {
		this.fechaFinEditar = fechaFinEditar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(Double totalCompra) {
		this.totalCompra = totalCompra;
	}

	
	

}
