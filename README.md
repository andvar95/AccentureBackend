# AccentureBackend


Prueba Backend Accenture donde se implemento una API usando SpringBoot y mysql, existe el archivo #API_instruciones que posee imágenes
de funcionamiento del desarrollo y la carpeta #springDB posee la base dato usada la cual tiene algunos datos de prueba

Acontinuación se muestra un instructivo de como usar la API


\n
#Crear Usuario
URL = http://localhost:8080/usuario - METODO POST 

Json_body = {  "cedula": 1102866527,"nombre": "Andres","email": "Andres@gmail.com","direccion": "calle 27" }

\n
#Crear un producto 

URL=  http://localhost:8080/productos - METODO POST

Json_Body = {"nombre": "Pollo","precio":2000}

\n
#Crear/Editar Carrito 
URL = http://localhost:8080/editarCompra/{id_usuario} - METODO POST 
Json_Body = {
    "usuario": {
       "id":1,
        "cedula": 1102866527,
        "nombre": "Andres",
        "email": "Andres@gmail.com",
        "direccion": "calle 27"
    },
    "productos": {
    "id": 1,
    "nombre": "Carne",
    "precio": 3000.0
},
    "idCompra": 111,
    "cantidad": 2
}


\n
#Obtener Factura
URL=  http://localhost:8080/editarCompra/{id_usuario}  - METODO GET



\n
#Eliminar Producto 
URL = http://localhost:8080/editarCompra/{id_usuario} - METODO DELETE 
Json_Body = {
    "usuario": {
       "id":1,
        "cedula": 1102866527,
        "nombre": "Andres",
        "email": "Andres@gmail.com",
        "direccion": "calle 27"
    },
    "productos": {
    "id": 1,
    "nombre": "Carne",
    "precio": 3000.0
},
    "idCompra": 111,
    "cantidad": 2
}



\n
La API contiene 

\n
#Dos usuarios 
->  {
       "id":1,
        "cedula": 1102866527,
        "nombre": "Andres",
        "email": "Andres@gmail.com",
        "direccion": "calle 27"
    }
-> {
       "id":2,
        "cedula": 64555045,
        "nombre": "Cenia",
        "email": "Cenia@gmail.com",
        "direccion": "calle 25"
    }
    
  
\n
  #Dos productos 
 ->{
    "id": 1,
    "nombre": "Carne",
    "precio": 3000.0
}

->{
    "id": 2,
    "nombre": "Pollo",
    "precio": 2000.0
},
    
    
 Y EL Usuario con id-> 1 no tiene ningun carrito asignado, el segundo tiene uno vencido 
