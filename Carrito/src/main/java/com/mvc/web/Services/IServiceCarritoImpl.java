package com.mvc.web.Services;

import com.mvc.web.Model.Carrito;
import com.mvc.web.Repository.IRepositoryCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IServiceCarritoImpl implements IServiceCarrito {

    @Autowired
    private IRepositoryCarrito repositoryCarrito;

    @Override
    public int Crear(Carrito carrito) {
        return repositoryCarrito.Crear(carrito);
    }

    @Override
    public List<Carrito> ListarPorDni(String dni) {
        return repositoryCarrito.ListarPorDni(dni);
    }

    @Override
    public int Modificar(Carrito carrito) {
        return repositoryCarrito.Modificar(carrito);
    }

    @Override
    public int Eliminar(int idCarrito) {
        return repositoryCarrito.Eliminar(idCarrito);
    }

    @Override
    public Carrito ListarPorId(int idCarrito) {
        return repositoryCarrito.ListarPorId(idCarrito);
    }
}
