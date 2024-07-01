package com.administradorservice.Services;

import com.administradorservice.Model.Producto;
import com.administradorservice.Repository.IRepositoryProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ServiceProductoImpl implements IServiceProducto {

    @Autowired
    private IRepositoryProducto repositoryProducto;

    @Override
    public int Crear(Producto producto, MultipartFile file) {
        return repositoryProducto.Crear(producto, file);
    }

    public int ActualizarProducto(Producto producto) {
        return repositoryProducto.ActualizarProducto(producto);
    }

    @Override
    public int Eliminar(int id) {
        return repositoryProducto.Eliminar(id);
    }

    @Override
    public List<Producto> ListarProducto() {
        return repositoryProducto.ListarProducto();
    }

    @Override
    public Producto ListarProductoPorId(int id) {
        return repositoryProducto.ListarProductoPorId(id);
    }

}
