package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Date;  
import java.util.HashMap;
import java.util.Optional;
import java.util.Calendar;
import java.time.*; // Este paquete contiene LocalDate, LocalTime y LocalDateTime.
import java.time.format.*;  // Este paquete contiene DateTimeFormatter.
import java.text.SimpleDateFormat; 

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ComprasModel;
import com.example.demo.models.Fechas;
import com.example.demo.models.ProductosModel;
import com.example.demo.models.UsuarioModel;
import com.example.demo.services.ComprasService;
import com.example.demo.services.FechaService;
import com.example.demo.services.UsuarioService;
import java.time.LocalDate;

@RestController

public class ComprasController {
	
	@Autowired
	ComprasService comprasservice;
	@Autowired
	UsuarioService usuarioservice;
	@Autowired
	FechaService fechaservice;
	
	

		






//@RequestMapping("/compras/{id_usr}")

@GetMapping("/editarCompra/{id_usr}")
public HashMap<String, Object>  detalleCompra(@PathVariable("id_usr") Long usrId) {
	double  total  = 0;
	double domicilio = 0;
	
	
	/* este metodo sirve para obtener el precio de la factura, debe ser activado con la petiición get*/
	Optional<UsuarioModel> user  =  usuarioservice.obtenerUsuario(usrId);
	if(user.isPresent()) {
		
		
		for(ComprasModel compra:user.get().getCompras()){
			total += compra.getPrecioSubTotal();
		}
	};

		//70000
		if (total>70000) {
			total += total*0.19;
			domicilio = 150000;
		}
		//100000
		if (total>100000) {
			domicilio = 0;
			
		}
	

	HashMap<String, Object> map = new HashMap<>();
    map.put("Total Factura", total);
    map.put("domicilio", domicilio);
    

	//return this.comprasservice.detalleCompra(user);
	return map;
		
}
		

//@RequestMapping()
@PostMapping("/editarCompra/{id_usr}")
public HashMap<String, Object> actualizarCompra(@PathVariable("id_usr") Long usrId, @RequestBody ComprasModel compra ) {

	String salida = "";
	Fechas fechas = new Fechas();
	
	/* Este metodo es para adcionar productos o actualizar siempre y cuando  no se haya cumlplido el tiempo de 5 horas*/ 
	
	Optional<UsuarioModel> user  =  usuarioservice.obtenerUsuario(usrId);  //este metodo busca si existe el usuario
	if(user.isPresent()) {  //si existe  verifico sus caompras 
		
	
	if(user.get().getCompras().size()>0) {  //si tieen compras debo verificar el tiempo
		Fechas fechaCambiar = new Fechas();
		Date finEd = new Date();
		Date now = new Date();
		for(Fechas fe:this.fechaservice.obtenerFechas()) {
			fechaCambiar = fe;
			finEd = fe.getFechaFinEditar();		}
		
		//obtengo la diferencia de fechas en horas
		Long diferencia = (finEd.getTime() - now.getTime())/1000/60/60;
		if(diferencia>0) {
			
			
			//solicitud de existencia del producto ingresado
			Optional<ComprasModel> compraExiste = this.comprasservice.obtenerCompra(compra.getProductos());
			
			//si existe lo actualizo en cantidad y precio
			if(compraExiste.isPresent()) {
				System.out.print("existe");
				compraExiste.get().setCantidad(compraExiste.get().getCantidad()+compra.getCantidad());
				compraExiste.get().setPrecioSubTotal(compraExiste.get().getCantidad()*compraExiste.get().getProductos().getPrecio());
				System.out.println(fechaCambiar.getTotalCompra()+compraExiste.get().getCantidad()*compraExiste.get().getProductos().getPrecio());
				fechaCambiar.setTotalCompra(fechaCambiar.getTotalCompra()+compraExiste.get().getCantidad()*compraExiste.get().getProductos().getPrecio());
				this.fechaservice.guardarFecha(fechaCambiar);
				comprasservice.guardarCompra(compraExiste.get());
				salida = "Actualizado expira " +diferencia+ " horas";
				
			}
			//si no existe adiciono el producto
			else {
				compra.setPrecioSubTotal(compra.getCantidad()*compra.getProductos().getPrecio());
				 this.comprasservice.guardarCompra(compra);
				 salida = "Creado -Actualizar Carrito expira en "+diferencia +" horas";}
		}
		
		else {salida="Ya pasaron 5 horas";}
		}
	else {
		//si no hay productos debo crear el carrito
		System.out.print("Creo");
		compra.setPrecioSubTotal(compra.getCantidad()*compra.getProductos().getPrecio());
		fechas.setTotalCompra(compra.getCantidad()*compra.getProductos().getPrecio());
		Date ahora = new Date();  
		 fechas.setFechaIncio(ahora);//hora actual
		 Calendar editar = Calendar.getInstance();
	      editar.setTime(ahora); 
	      editar.add(Calendar.HOUR, 5);  //adiciono 5 horas para la fecha limite de editar
		 fechas.setFechaFinEditar(editar.getTime());//guardo la fecha
		 
		 Calendar eliminar = Calendar.getInstance();
		 eliminar.setTime(ahora); //
		 eliminar.add(Calendar.HOUR, 12);  //adiciono 5 horas para la fecha limite de editar
		 fechas.setFechaFinEliminar(eliminar.getTime());//guardo la fecha
		 
		 this.comprasservice.guardarCompra(compra); //actualizo la bd
		 this.fechaservice.guardarFecha(fechas);
		 salida = "Creado Nuevo";	}	};
	
	HashMap<String, Object> map = new HashMap<>();
    map.put("Salida", salida);

    

	//return this.comprasservice.detalleCompra(user);
	return map;
	
}

@DeleteMapping("/editarCompra/{id_usr}")
public HashMap<String, Object> eliminarCompra(@PathVariable("id_usr") Long usrId) {
	
	String salida = "";
	Fechas fechaActual = new Fechas();
	
	/*Metodo eliminar para la bd */
	
	Optional<UsuarioModel> user  =  usuarioservice.obtenerUsuario(usrId);
	if(user.isPresent()) { // verifico si el usuario existe
		if(user.get().getCompras().size()>0) {
			Date finEliminar = new Date();
			Date now = new Date();
			for(Fechas fe:this.fechaservice.obtenerFechas()) {
				fechaActual = fe;
				finEliminar = fe.getFechaFinEliminar();}
			
				Long diferencia = (finEliminar.getTime() - now.getTime())/1000/60/60;
				//verifico que se haya cumplido el tiempo de limite par aeliminar
				if(diferencia>0) {
				salida = this.comprasservice.eliminarCarrito();
				this.fechaservice.borrarFechas();
				//si hay tiempo limpio el carrito
				}
				else {
					fechaActual.getTotalCompra();
					salida = "Ya pasaron la 12 horas, debe cancelar "+fechaActual.getTotalCompra()*.10;
					String s = this.comprasservice.eliminarCarrito();
					this.fechaservice.borrarFechas();
					//sino hay tiempo limpio el carrito pero genero una factura del 10% del total
				}
			
			
		}
		else { //si el usuario no existe no hay productos ni pedido hecho
			salida = "No hay proudctos";
		}
	}
	
	HashMap<String, Object> map = new HashMap<>();
	map.put("Salida", salida);



	//return this.comprasservice.detalleCompra(user);
	return map;

	
	
}





}






