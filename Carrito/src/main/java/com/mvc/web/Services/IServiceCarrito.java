package com.mvc.web.Services;

import com.mvc.web.Model.Carrito;

import java.util.List;

public interface IServiceCarrito {
    List<Carrito> ListarPorDni(String dni);
    Carrito ListarPorId(int idCarrito);
    int Crear(Carrito carrito);
    int Modificar(Carrito carrito);
    int Eliminar(int idCarrito);
}

