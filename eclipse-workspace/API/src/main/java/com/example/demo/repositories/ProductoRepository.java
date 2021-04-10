package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.ProductosModel;

@Repository
public interface ProductoRepository extends CrudRepository<ProductosModel,Long>{

}
