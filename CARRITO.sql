CREATE DATABASE [CARRITO]

USE [CARRITO]
GO
/****** Object:  Table [dbo].[Carrito]    Script Date: 01/07/2024 0:52:02 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Carrito](
	[idCarrito] [int] IDENTITY(1,1) NOT NULL,
	[idProducto] [int] NULL,
	[dni] [varchar](8) NOT NULL,
	[talla] [char](1) NULL,
	[cantidadProducto] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idCarrito] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Carrito]  WITH CHECK ADD CHECK  ((len([dni])=(8)))
GO
ALTER TABLE [dbo].[Carrito]  WITH CHECK ADD CHECK  (([talla]='l' OR [talla]='m' OR [talla]='s'))
GO
