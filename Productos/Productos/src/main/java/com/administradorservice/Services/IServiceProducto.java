package com.administradorservice.Services;

import com.administradorservice.Model.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IServiceProducto {
    int Crear(Producto objeto, MultipartFile file);
    int ActualizarProducto(Producto producto);
    int Eliminar(int id);
    List<Producto> ListarProducto();
    Producto ListarProductoPorId(int id);
}
