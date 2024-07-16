USE [INVENTARIO]
GO
/****** Object:  Table [dbo].[Producto]    Script Date: 11/07/2024 1:22:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Producto](
	[idProducto] [int] IDENTITY(1,1) NOT NULL,
	[nombreProducto] [varchar](200) NOT NULL,
	[catProducto] [varchar](50) NULL,
	[precioProducto] [float] NULL,
	[tallaS] [int] NULL,
	[tallaM] [int] NULL,
	[tallaL] [int] NULL,
	[imgProducto] [varchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[idProducto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
