package com.administradorservice.Services;


import com.administradorservice.Model.DetalleProducto;
import com.administradorservice.Model.Producto;
import com.administradorservice.Repository.IRepositoryProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProductoImpl implements IServiceProducto{

    @Autowired
    private IRepositoryProducto repositoryProducto;

    @Override
    public int Crear(Producto producto) { return repositoryProducto.Crear(producto); }

    @Override
    public List<Producto> Listar() { return repositoryProducto.Listar(); }

    @Override
    public Producto listarDetalleProductoPorIdProducto(int id) { return repositoryProducto.listarDetalleProductoPorIdProducto(id); }

    @Override
    public Producto ListarProductoPorId(int id) { return repositoryProducto.ListarProductoPorId(id); }

    @Override
    public DetalleProducto ListarDetalleProductoPorId(int id) { return repositoryProducto.ListarDetalleProductoPorId(id); }

    @Override
    public int ActualizarProducto(Producto producto) { return repositoryProducto.ActualizarProducto(producto); }

    @Override
    public int ActualizarDetallesProducto(int idProducto, List<DetalleProducto> detalles) {return repositoryProducto.ActualizarDetallesProducto(idProducto, detalles);}

    @Override
    public int Eliminar(int id) { return repositoryProducto.Eliminar(id);}

    @Override
    public List<Producto> ListarProducto() { return repositoryProducto.ListarProducto(); }

    @Override
    public List<DetalleProducto> ListarDetallesProducto() { return repositoryProducto.ListarDetallesProducto();}


}
