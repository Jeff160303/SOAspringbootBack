USE [REGISTRO]
GO
/****** Object:  Table [dbo].[detalleUsuarios]    Script Date: 11/07/2024 1:21:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[detalleUsuarios](
	[idDetalleUsuarios] [int] IDENTITY(1,1) NOT NULL,
	[dni] [varchar](8) NOT NULL,
	[direccion] [varchar](255) NOT NULL,
	[codigoPostal] [varchar](5) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idDetalleUsuarios] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuarios]    Script Date: 11/07/2024 1:21:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuarios](
	[dni] [varchar](8) NOT NULL,
	[nombres] [varchar](50) NULL,
	[apellidos] [varchar](50) NULL,
	[numTelefono] [varchar](15) NULL,
	[correo] [varchar](100) NULL,
	[rol] [varchar](50) NULL,
	[contrasena] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[dni] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[detalleUsuarios]  WITH CHECK ADD FOREIGN KEY([dni])
REFERENCES [dbo].[Usuarios] ([dni])
GO
