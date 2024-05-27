package com.administradorservice.Repository;

import com.administradorservice.Model.DetalleProducto;
import com.administradorservice.Model.Producto;

import java.util.List;

public interface IRepositoryProducto {
    List<Producto> Listar();
    Producto listarDetalleProductoPorIdProducto(int id);
    int Crear(Producto objeto);
    int ActualizarProducto(Producto objeto);
    int ActualizarDetallesProducto(int idProducto, List<DetalleProducto> detalles);
    int Eliminar(int id);
    List<Producto> ListarProducto();
    List<DetalleProducto> ListarDetallesProducto();
    Producto ListarProductoPorId(int id);
    DetalleProducto ListarDetalleProductoPorId(int id);


}
