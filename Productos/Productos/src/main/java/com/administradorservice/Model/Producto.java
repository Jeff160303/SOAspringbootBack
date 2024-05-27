package com.administradorservice.Model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    private DetalleProducto detalleProducto;

    @NotEmpty
    private int idproducto;

    @NotEmpty
    private String nombreProducto;

    @NotEmpty
    private String catProducto;

    @NotEmpty
    private Double precioProducto;

    private String detalleproducto;

    private List<DetalleProducto> detalles;

}
