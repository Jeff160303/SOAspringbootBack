-- Base de Datos de Usuarios --
CREATE DATABASE REGISTRO

CREATE TABLE Usuarios (
    dni VARCHAR(8) PRIMARY KEY,
    nombres VARCHAR(50),
    apellidos VARCHAR(50),
    numTelefono VARCHAR(15),
    correo VARCHAR(100),
    rol VARCHAR(50),
    contrasena VARCHAR(50)
);

CREATE TABLE detalleUsuarios (
    idDetalleUsuarios VARCHAR(8) PRIMARY KEY,
	dni VARCHAR (8) FOREIGN KEY REFERENCES Usuarios(dni),
    direccion VARCHAR(100)
);







-- Base de Datos de Productos --
CREATE DATABASE INVENTARIO

CREATE TABLE Producto (
    idproducto INT IDENTITY(1,1) PRIMARY KEY,
    nombreProducto VARCHAR(100),
    catProducto VARCHAR(50),
    precioProducto VARCHAR (10)
)

CREATE TABLE DetalleProducto (
    idDetallePro INT IDENTITY(1,1) PRIMARY KEY,
    idproducto INT NOT NULL,
    tallas VARCHAR(10),
    stock VARCHAR(10),
	imagenProducto VARCHAR(200)
	FOREIGN KEY (idproducto) REFERENCES Producto(idproducto)
);