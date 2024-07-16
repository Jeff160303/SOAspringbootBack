USE [VENTA]
GO
/****** Object:  Table [dbo].[DetalleVenta]    Script Date: 11/07/2024 1:18:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DetalleVenta](
	[idDetalleVenta] [int] IDENTITY(1,1) NOT NULL,
	[idVenta] [int] NULL,
	[nombreProducto] [nvarchar](100) NOT NULL,
	[cantProducto] [int] NOT NULL,
	[tallaProducto] [nvarchar](2) NOT NULL,
	[precioProducto] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idDetalleVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Venta]    Script Date: 11/07/2024 1:18:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Venta](
	[idVenta] [int] IDENTITY(1,1) NOT NULL,
	[fechaEmision] [datetime] NOT NULL,
	[dni] [varchar](8) NOT NULL,
	[nombres] [nvarchar](100) NOT NULL,
	[apellidos] [nvarchar](100) NOT NULL,
	[tipoVenta] [nvarchar](50) NOT NULL,
	[direccion] [nvarchar](200) NOT NULL,
	[total] [decimal](10, 2) NOT NULL,
	[estado] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Venta] ADD  DEFAULT (getdate()) FOR [fechaEmision]
GO
ALTER TABLE [dbo].[DetalleVenta]  WITH CHECK ADD FOREIGN KEY([idVenta])
REFERENCES [dbo].[Venta] ([idVenta])
GO
ALTER TABLE [dbo].[DetalleVenta]  WITH CHECK ADD CHECK  (([cantProducto]>=(1) AND [cantProducto]<=(99)))
GO
ALTER TABLE [dbo].[DetalleVenta]  WITH CHECK ADD CHECK  (([tallaProducto]='L' OR [tallaProducto]='M' OR [tallaProducto]='S'))
GO
ALTER TABLE [dbo].[Venta]  WITH CHECK ADD CHECK  (([estado]='entregado' OR [estado]='enviado' OR [estado]='pendiente'))
GO
ALTER TABLE [dbo].[Venta]  WITH CHECK ADD CHECK  (([tipoVenta]='recojo en tienda' OR [tipoVenta]='pedido'))
GO
