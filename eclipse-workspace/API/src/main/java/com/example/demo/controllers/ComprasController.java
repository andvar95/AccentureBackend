package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Date;  
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
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
import com.example.demo.models.CarritoModel;
import com.example.demo.models.ProductosModel;
import com.example.demo.models.UsuarioModel;
import com.example.demo.services.ComprasService;
import com.example.demo.services.CarritoService;
import com.example.demo.services.UsuarioService;
import java.time.LocalDate;

@RestController

public class ComprasController {
	
	@Autowired
	ComprasService comprasservice;
	@Autowired
	UsuarioService usuarioservice;
	@Autowired
	CarritoService carritoservice;
	
	

		






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
	CarritoModel carrito = new CarritoModel();
	
	
	
	/* Este metodo es para adcionar productos o actualizar siempre y cuando  no se haya cumlplido el tiempo de 5 horas*/ 
	
	Optional<UsuarioModel> user  =  usuarioservice.obtenerUsuario(usrId);  //este metodo busca si existe el usuario
	if(user.isPresent()) {  //si existe  verifico sus caompras 
		
		if(user.get().getCarrito()!=null) {
			carrito = user.get().getCarrito();
		}
		
		System.out.println("carrito "+user.get().getCarrito());
		System.out.println("compras "+user.get().getCompras());
		System.out.println("usr "+user.get().getId());
		
		ArrayList<ComprasModel> compraAntigua = this.comprasservice.buscarPorUsuario(user.get());	
		System.out.println("comp "+compraAntigua.size());
	
	if(compraAntigua.size()>0) {  //si tieen compras debo verificar el tiempo
		//CarritoModel fechaCambiar = new CarritoModel();
		Date finEd = carrito.getFechaFinEditar();
		Date now = new Date();
		/*
		for(CarritoModel fe:this.fechaservice.obtenerFechas()) {
			fechaCambiar = fe;
			finEd = fe.getFechaFinEditar();		}*/
		
		//obtengo la diferencia de fechas en horas
		Long diferencia = (finEd.getTime() - now.getTime())/1000/60/60;
		if(diferencia>0) {
			
			Boolean existe = false;
			Long idCompra= 0L;
			
			for(ComprasModel cm:compraAntigua) {
				if (cm.getProductos().getId()==compra.getProductos().getId()) {
					idCompra= cm.getId();
					existe = true;	
				}
			}
			
			
			
			//solicitud de existencia del producto ingresado
			//Optional<ComprasModel> compraExiste = this.comprasservice.obtenerCompra(compra.getProductos());
			//System.out.println("existe"+compraExiste);
			//si existe lo actualizo en cantidad y precio
			if(existe) {
				
				
				ComprasModel compraExiste = this.comprasservice.obtenerId(idCompra).get();
				System.out.print("existe");
				compraExiste.setCantidad(compraExiste.getCantidad()+compra.getCantidad());
				compraExiste.setPrecioSubTotal(compraExiste.getCantidad()*compraExiste.getProductos().getPrecio());
				
				System.out.println(carrito.getTotalCompra()+"\n"+compraExiste.getCantidad()*compraExiste.getProductos().getPrecio());
				carrito.setTotalCompra(compraExiste.getCantidad()*compraExiste.getProductos().getPrecio());
				this.carritoservice.guardarCarrito(carrito);
				this.comprasservice.guardarCompra(compraExiste);
				
				salida = "Actualizado expira " +diferencia+ " horas";
				
			}
			//si no existe adiciono el producto
			else {
				compra.setPrecioSubTotal(compra.getCantidad()*compra.getProductos().getPrecio());
				carrito.setTotalCompra(carrito.getTotalCompra()+compra.getCantidad()*compra.getProductos().getPrecio());
				 this.comprasservice.guardarCompra(compra);
					this.carritoservice.guardarCarrito(carrito);
				 
				 salida = "Creado -Actualizar Carrito expira en "+diferencia +" horas";}
		}
		
		else {salida="Ya pasaron 5 horas";}
		}
	else {
		//si no hay productos debo crear el carrito
		System.out.print("Creo");
		compra.setPrecioSubTotal(compra.getCantidad()*compra.getProductos().getPrecio());
		carrito.setTotalCompra(compra.getCantidad()*compra.getProductos().getPrecio());
		
		Date ahora = new Date();  
		 carrito.setFechaIncio(ahora);//hora actual
		 Calendar editar = Calendar.getInstance();
	      editar.setTime(ahora); 
	      editar.add(Calendar.HOUR, 5);  //adiciono 5 horas para la fecha limite de editar
		 carrito.setFechaFinEditar(editar.getTime());//guardo la fecha
		 
		 Calendar eliminar = Calendar.getInstance();
		 eliminar.setTime(ahora); //
		 eliminar.add(Calendar.HOUR, 12);  //adiciono 5 horas para la fecha limite de editar
		 carrito.setFechaFinEliminar(eliminar.getTime());//guardo la fecha
		 this.carritoservice.guardarCarrito(carrito);
		 System.out.print(compra.getPrecioSubTotal());
		 comprasservice.guardarCompra(compra); //actualizo la bd
		 user.get().addCompras(compra);
		 System.out.print(user.get().getCompras());
	
		
		 salida = "Creado Nuevo";	
		 }
	user.get().setCarrito(carrito);
	this.usuarioservice.guardarUsuario(user.get());
	};
		 
		 
		
	
	HashMap<String, Object> map = new HashMap<>();
    map.put("Salida", salida);

    

	//return this.comprasservice.detalleCompra(user);
	return map;
	
}

@DeleteMapping("/editarCompra/{id_usr}")
public HashMap<String, Object> eliminarCompra(@PathVariable("id_usr") Long usrId) {
	
	String salida = "";
	CarritoModel carrito = new CarritoModel();
	
	/*Metodo eliminar para la bd */
	
	Optional<UsuarioModel> user  =  usuarioservice.obtenerUsuario(usrId);
	if(user.isPresent()) { // verifico si el usuario existe
		
		System.out.println(user.get().getCompras().size());
		ArrayList<ComprasModel> compraAntigua = this.comprasservice.buscarPorUsuario(user.get());
		
		if(compraAntigua.size()>0) {
			
			carrito = user.get().getCarrito();
			Date finEliminar = carrito.getFechaFinEliminar();
			Date now = new Date();
			/*
			for(CarritoModel fe:this.carrito.obtenerFechas()) {
				fechaActual = fe;
				finEliminar = fe.getFechaFinEliminar();}
				
				
			*/
			
			user.get().setCarrito(null);
			this.usuarioservice.guardarUsuario(user.get());
				Long diferencia = (finEliminar.getTime() - now.getTime())/1000/60/60;
				//verifico que se haya cumplido el tiempo de limite par aeliminar
				if(diferencia>0) {
				

				this.carritoservice.borrarCarrito(carrito.getId());
				salida = "Eliminado satisfactoriamente";
				//si hay tiempo limpio el carrito
				}
				else {
					carrito.getTotalCompra();
					salida = "Ya pasaron la 12 horas, debe cancelar "+carrito.getTotalCompra()*.10;
					
					this.carritoservice.borrarCarrito(carrito.getId());
					//sino hay tiempo limpio el carrito pero genero una factura del 10% del total
				}
			
			for(ComprasModel cc:compraAntigua) {
				this.comprasservice.eliminarCarrito(cc.getId());
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






