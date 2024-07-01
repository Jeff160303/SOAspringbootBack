package com.administradorservice.Repository;

import com.administradorservice.Model.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRepositoryProducto {
    List<Producto> ListarProducto();
    int Crear(Producto objeto, MultipartFile file);
    int ActualizarProducto(Producto objeto);
    int Eliminar(int id);
    Producto ListarProductoPorId(int id);
}

