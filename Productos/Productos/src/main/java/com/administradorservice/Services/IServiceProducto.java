package com.administradorservice.Services;

import com.administradorservice.Model.DetalleProducto;
import com.administradorservice.Model.Producto;

import java.util.List;

public interface IServiceProducto {

    int Crear(Producto producto);
    List<Producto> Listar();
    Producto listarDetalleProductoPorIdProducto(int id);
    int ActualizarProducto(Producto producto);
    int ActualizarDetallesProducto(int idProducto, List<DetalleProducto> detalles);
    int Eliminar(int id);
    List<Producto> ListarProducto();
    List<DetalleProducto> ListarDetallesProducto();
    Producto ListarProductoPorId(int id);
    DetalleProducto ListarDetalleProductoPorId(int id);

}
