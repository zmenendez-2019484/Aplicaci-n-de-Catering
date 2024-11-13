/*
Nombre: Ottoniel Menéndez Estrada
Código Técnico: IN5BM
Carné: 2019484
Fecha de Creación: 28-03-2023
Fecha de Modificación: 09/04/2023
*/ 

drop database if exists DBTonysKinal2023_2019484;
create database DBTonysKinal2023_2019484;

use DBTonysKinal2023_2019484;

Create table Empresas(
	codigoEmpresa int auto_increment not null,
    nombreEmpresa varchar(150) not null,
    direccion varchar(150) not null,
    telefono varchar(10) not null,
    primary key PK_codigoEmpresa (codigoEmpresa)
);

Create table TipoEmpleado(
	codigoTipoEmpleado int not null auto_increment,
    descripcion varchar(50) not null,
    primary key PK_codigoTipoEmpleado (codigoTipoEmpleado)
);

Create table Empleados(
	codigoEmpleado int auto_increment not null,
    numeroEmpleado int not null,
    apellidosEmpleado varchar(150) not null,
    nombresEmpleado varchar(150) not null,
    direccionEmpleado varchar(150) not null,
    telefonoContacto varchar(10) not null,
    gradoCocinero varchar(50) not null,
    codigoTipoEmpleado int not null,
    primary key PK_codigoEmpleado (codigoEmpleado),
    constraint FK_Empleados_TipoEmpleado foreign key
		(codigoTipoEmpleado) references TipoEmpleado(codigoTipoEmpleado) on delete cascade
);

Create table TipoPlato(
	codigoTipoPlato int auto_increment not null,
    descripcionTipo varchar(100) not null,
    primary key PK_codigoTipoPlato (codigoTipoPlato)
);

Create table Productos (
	codigoProducto int auto_increment not null,
    nombreProducto varchar(150) not null,
    cantidad int not null,
    primary key PK_codigoProducto (codigoProducto)
);

Create table Servicios(
	codigoServicio int auto_increment not null,
    fechaServicio date not null,
    tipoServicio varchar(150) not null,
    horaServicio time not null,
    lugarServicio varchar(150) not null,
    telefonoContacto varchar(10) not null,
    codigoEmpresa int not null,
    primary key PK_codigoServicio (codigoServicio),
    constraint FK_Servicios_Empresas foreign key (codigoEmpresa)
		references Empresas (codigoEmpresa) on delete cascade
);

Create table Presupuestos (
	codigoPresupuesto int auto_increment not null,
    fechaSolicitud date not null,
    cantidadPresupuesto decimal(10,2) not null,
    codigoEmpresa int not null,
    primary key PK_codigoPresupuesto (codigoPresupuesto),
    constraint FK_Presupuestos_Empresas foreign key (codigoEmpresa)
		references Empresas (codigoEmpresa) on delete cascade
);

Create table Platos (
	codigoPlato int auto_increment not null,
    cantidad int not null,
    nombrePlato varchar(50),
    descripcionPlato varchar(150),
    precioPlato decimal(10,2),
    codigoTipoPlato int not null,
    -- TipoPlato codigoTipoPlato int not null,
    primary key PK_codigoPlato (codigoPlato),
    constraint FK_Platos_TipoPlato1 foreign key (codigoTipoPlato)
		references TipoPlato (codigoTipoPlato) on delete cascade 
);

Create table Productos_has_Platos(
	Productos_codigoProducto int not null,
    codigoPlato int not null,
    codigoProducto int not null,
    primary key PK_Productos_codigoProducto (Productos_codigoProducto),
	constraint FK_Productos_has_Platos_Producto1 foreign key (codigoProducto)
		references Productos (codigoProducto) on delete cascade,
	constraint FK_Productos_has_Platos_Platos1 foreign key (codigoPlato)
		references Platos (codigoPlato) on delete cascade
);

Create table Servicios_has_Platos(
	Servicios_codigoServicio int not null,
    codigoPlato int not null,
    codigoServicio int not null,
    primary key PK_Servicios_codigoServicio (Servicios_codigoServicio),
    constraint FK_Servicios_has_Platos_Servicios1 foreign key (codigoServicio)
		references Servicios (codigoServicio) on delete cascade,
	constraint FK_Servicios_has_Platos_Platos1 foreign key (codigoPlato)
		references Platos (codigoPlato) on delete cascade
);

Create table Servicios_has_Empleados(
	Servicios_codigoServicio int not null,
    codigoEmpleado int not null,
    codigoServicio int not null,
    fechaEvento date not null,
    horaEvento time not null,
    lugarEvento varchar(150) not null,
    primary key PK_Servicios_codigoServicio (Servicios_codigoServicio),
    constraint FK_Servicios_has_Empleados_Servicios1 foreign key (codigoServicio)
		references Servicios (codigoServicio) on delete cascade,
	constraint FK_Servicios_has_Empleados_Empleados1 foreign key (codigoEmpleado)
		references Empleados (codigoEmpleado) on delete cascade
);


Create table Usuario(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar(100) not null,
    apellidoUsuario varchar(100) not null,
    usuarioLogin varchar(50) not null unique,
    clave varchar(50) not null,
    primary key PK_codigoUsuario(codigoUsuario)
);

Create table Login(
	usuarioMaster varchar(50) not null,
    passwordLogin varchar(50) not null,
    primary key PK_usuarioMaster(usuarioMaster)
);

-- ----------------------- Procedimientos almacenados Entidad Usuario ---------------
-- Agregar Usuario --
Delimiter $$
	create procedure sp_AgregarUsuario(in nombreUsuario varchar(100), in apellidoUsuario varchar(100), in usuarioLogin varchar(50), in clave varchar(50))
		Begin
			Insert Into Usuario(nombreUsuario,apellidoUsuario,usuarioLogin,clave)
				values(nombreUsuario,apellidoUsuario,usuarioLogin,clave);
		End$$
Delimiter;


-- Listar Usuarios --
Delimiter $$
	create procedure sp_ListarUsuarios()
		Begin
			Select u.codigoUsuario,
            u.nombreUsuario,
            u.apellidoUsuario,
            u.usuarioLogin,
            u.clave
            from Usuario u;
        End$$
Delimiter ;
/*
call sp_AgregarUsuario("Ottoniel", "Menéndez", "zmenendez", 'q1w2e3');
call sp_AgregarUsuario("Oscar", "Morales", "omorales", '9876');*/
call sp_ListarUsuarios();
-- Use DBRecuperacion;

-- ----------------------- Procedimientos almacenados Entidad Empresas ---------------

-- Agregar Empresa --

Delimiter $$
	create procedure sp_AgregarEmpresa (in nombreEmpresa varchar(150),in direccion varchar(150), in telefono varchar(10))
		Begin
			Insert Into Empresas (nombreEmpresa, direccion, telefono)
				values (nombreEmpresa, direccion, telefono);
        End$$
Delimiter ;

call sp_AgregarEmpresa('Campero', '7A Avenida 10, Cdad. de Guatemala', '1971');
call sp_AgregarEmpresa('Farmacia Galeno', 'San Cristóbal 17-01, Cdad. de Guatemala', '1702');
call sp_AgregarEmpresa('Burger King', 'San Cristóbal 18-95, Cdad. de Guatemala', '2200 0000');
call sp_AgregarEmpresa('Funeraria Señoriales', '6a. Avenida, Zona 9, Cdad. de Guatemala', '2229 4444');
call sp_AgregarEmpresa('Elektra', 'Calzada Roosvelt', '4430 7612');
-- Listar Empresas --

Delimiter $$
	create procedure sp_ListarEmpresas ()
		Begin
			Select codigoEmpresa ,
				nombreEmpresa ,
                direccion ,
                telefono
                from Empresas;
        End$$
Delimiter ;

-- call sp_ListarEmpresas();

-- Buscar Empresa --

Delimiter $$
	create procedure sp_BuscarEmpresa(in codigo int)
		Begin
			Select codigoEmpresa,
				nombreEmpresa,
                direccion,
                telefono
                from Empresas where codigoEmpresa=codigo;
        End$$
Delimiter ;

-- call sp_BuscarEmpresa(1);

-- Editar Empresa --

Delimiter $$
	create procedure sp_EditarEmpresa(in codigo int, in nombre varchar(150), in direccion varchar(150), in telefono varchar(10))
		Begin
			Update Empresas e
            set e.codigoEmpresa=codigo, e.nombreEmpresa=nombre, e.direccion=direccion, e.telefono=telefono
            where e.codigoEmpresa=codigo;
        End$$
Delimiter ;
-- call sp_EditarEmpresa(1,'Campero', 'Guatemala', 1414);

-- Eliminar Empresa --

Delimiter $$
	create procedure sp_EliminarEmpresa(in codigo int)
		Begin
			Delete from Empresas
            where Empresas.codigoEmpresa=codigo;
        End$$
Delimiter ;

-- call sp_EliminarEmpresa(1);

-- ----------------------- Procedimientos almacenados Entidad Tipo Empleado ---------------

-- Agregar TipoEmpleado --

Delimiter $$
	create procedure sp_AgregarTipoEmpleado(in descripcion varchar(50))
		Begin
			Insert Into tipoEmpleado(descripcion)
            values (descripcion);
        End$$
Delimiter ;
-- call sp_AgregarTipoEmpleado('nose');

-- Listar TipoEmpleado --

Delimiter $$
	create procedure sp_ListarTipoEmpleados()
		Begin
			Select t.codigoTipoEmpleado,
            t.descripcion
            from tipoEmpleado t;
        End$$
Delimiter ;
-- call sp_ListarTipoEmpleados();

-- Buscar TipoEmpleado --

Delimiter $$
	create procedure sp_BuscarTipoEmpleado(in codigo int)
		Begin
			Select t.codigoTipoEmpleado,
            t.descripcion
            from tipoEmpleado t where t.codigoTipoEmpleado=codigo;
        End$$
Delimiter ;
-- call sp_BuscarTipoEmpleado(1);

-- Editar TipoEmpleado --

Delimiter $$
	create procedure sp_EditarTipoEmpleado(in codigo int, in descripcion varchar(50))
		Begin
			Update tipoEmpleado t
            set t.codigoTipoEmpleado=codigo, t.descripcion=descripcion
            where t.codigoTipoEmpleado=codigo;
        End$$
Delimiter ;

-- Eliminar TipoEmpleado --

Delimiter $$
	create procedure sp_EliminarTipoEmpleado(in codigo int)
		Begin
			Delete from tipoEmpleado
            where codigoTipoEmpleado=codigo;
        End$$
Delimiter ;

-- ----------------------- Procedimientos almacenados Entidad Empleados ---------------

-- Agregar Empleados --

Delimiter $$
	create procedure sp_AgregarEmpleado(in numeroEmpleado int, in apellidosEmpleado varchar(150), 
		in nombresEmpleado varchar(150), in direccionEmpleado varchar(150), in telefonoContacto varchar(10), in gradoCocinero varchar(50),
			in codigoTipoEmpleado int)
				Begin
					Insert Into Empleados(numeroEmpleado, apellidosEmpleado, nombresEmpleado, direccionEmpleado, telefonoContacto,
						gradoCocinero, codigoTipoEmpleado)
					Values(numeroEmpleado, apellidosEmpleado, nombresEmpleado, direccionEmpleado, telefonoContacto,
						gradoCocinero, codigoTipoEmpleado);
                End$$    
Delimiter ;

-- Listar Empleados --

Delimiter $$
	create procedure sp_ListarEmpleados()
		Begin
			Select e.codigoEmpleado ,
            e.numeroEmpleado ,
            e.apellidosEmpleado ,
            e.nombresEmpleado ,
            e.direccionEmpleado ,
            e.telefonoContacto ,
            e.gradoCocinero ,
            e.codigoTipoEmpleado 
            from Empleados e;
        End$$
Delimiter ;

-- call sp_listarEmpleados();

-- Buscar Empleado --

Delimiter $$
	create procedure sp_BuscarEmpleado(in codigo int)
		Begin
			Select e.codigoEmpleado ,
            e.numeroEmpleado ,
            e.apellidosEmpleado ,
            e.nombresEmpleado ,
            e.direccionEmpleado ,
            e.telefonoContacto ,
            e.gradoCocinero ,
            e.codigoTipoEmpleado 
            from Empleados e where e.codigoEmpleado=codigo;
        End$$
Delimiter ;

-- Editar Empleado --

Delimiter $$
	create procedure sp_EditarEmpleado (in codigo int, in numero int, in apellidos varchar(150),  in nombres varchar(150),
		in direccion varchar(150), in telefono varchar(10), in grado varchar(50))
		Begin
			Update Empleados e
            set e.codigoEmpleado=codigo, e.numeroEmpleado=numero, e.apellidosEmpleado=apellidos, e.nombresEmpleado=nombres,
				e.direccionEmpleado=direccion, e.telefonoContacto=telefono, e.gradoCocinero=grado
            where e.codigoEmpleado=codigo;    
        End$$
Delimiter ;

-- Eliminar Empleado --

Delimiter $$
	create procedure sp_EliminarEmpleado(in codigo int)
		Begin
			Delete from Empleados 
            where codigoEmpleado=codigo;
        End$$
Delimiter ;

-- ----------------------- Procedimientos almacenados Entidad Presupuestos ---------------

-- Agregar Presupuesto --

Delimiter $$
	create procedure sp_AgregarPresupuesto(in fechaSolicitud date, in cantidadPresupuesto decimal(10,2), in codigoEmpresa int)
		Begin
			Insert Into Presupuestos(fechaSolicitud, cantidadPresupuesto, codigoEmpresa)
            Values(fechaSolicitud, cantidadPresupuesto, codigoEmpresa);
        End$$
Delimiter ;

-- Listar Presupuestos --

Delimiter $$
	create procedure sp_ListarPresupuestos()
		Begin
			Select p.codigoPresupuesto,
            p.fechaSolicitud,
            p.cantidadPresupuesto,
            p.codigoEmpresa
            from Presupuestos p;
        End$$
Delimiter ;
call sp_ListarPresupuestos();
-- Buscar Presupuesto --

Delimiter $$
	create procedure sp_BuscarPresupuesto(in codigo int)
		Begin
			Select p.codigoPresupuesto AS 'Código Presupuesto',
            p.fechaSolicitud AS 'Fecha Solicitud',
            p.cantidadPresupuesto AS 'Cantidad de Presupuesto',
            p.codigoEmpresa AS 'Código Empresa'
            from Presupuestos p where  p.codigoPresupuesto=codigo;
        End$$
Delimiter ;

-- Editar Presupuesto --

Delimiter $$
	create procedure sp_EditarPresupuesto(in codigo int, in fechaS date, in cantidadP decimal(10,2), in codigoEmpresa int)
		Begin
			Update Presupuestos p 
            set p.codigoPresupuesto=codigo, p.fechaSolicitud=fechaS, p.cantidadPresupuesto=cantidadP, p.codigoEmpresa=codigoEmpresa
            where p.codigoPresupuesto=codigo;
        End$$
Delimiter ;

-- Eliminar Presupuesto --

Delimiter $$
	create procedure sp_EliminarPresupuesto(in codigo int)
		Begin
			Delete from Presupuestos
            where codigoPresupuesto=codigo;
        End$$
Delimiter ;

-- ----------------------- Procedimientos almacenados Entidad Servicios ---------------

-- Agregar Servicio --

Delimiter $$
	create procedure sp_AgregarServicio(in fechaServicio date, in tipoServicio varchar(150), in horaServicio time, in lugarServicio varchar(150),
		in telefonoContacto varchar(10), in codigoEmpresa int)
        Begin
			Insert Into Servicios(codigoServicio, fechaServicio, tipoServicio, horaServicio, lugarServicio, telefonoContacto, codigoEmpresa)
            values(codigoServicio, fechaServicio, tipoServicio, horaServicio, lugarServicio, telefonoContacto, codigoEmpresa);
        End$$
Delimiter ;

-- Listar Servicios --

Delimiter $$
	create procedure sp_ListarServicios()
		Begin
			Select s.codigoServicio,
            s.fechaServicio,
            s.tipoServicio,
            s.horaServicio,
            s.lugarServicio,
            s.telefonoContacto,
            s.codigoEmpresa
            from Servicios s;
        End$$
Delimiter ;

-- Buscar Servicio --

Delimiter $$
	create procedure sp_BuscarServicio(in codigo int)
		Begin
			Select s.codigoServicio,
            s.fechaServicio,
            s.tipoServicio,
            s.horaServicio,
            s.lugarServicio,
            s.telefonoContacto,
            s.codigoEmpresa
            from Servicios s where s.codigoServicio=codigo;
        End$$	
Delimiter ;

-- Editar Servicio --

Delimiter $$
	create procedure sp_EditarServicio(in codigo int, in fecha date, in tipoS varchar(150), in horaS time, in lugarS varchar(150), 
		in telefonoC varchar(10), in codigoE int)
        Begin
			Update Servicios s 
            set s.codigoServicio=codigo, s.fechaServicio=fecha, s.tipoServicio=tipoS, s.horaServicio=horaS, s.lugarServicio=lugarS,
				s.telefonoContacto=telefonoC, s.codigoEmpresa=codigoE
            where  s.codigoServicio=codigo;   
        End$$
Delimiter ;

-- Eliminar Servicio --

Delimiter $$
	create procedure sp_EliminarServicio(in codigo int)
		Begin
			Delete from Servicios
            where codigoServicio=codigo;
        End$$
Delimiter ;

-- ----------------------- Procedimientos almacenados Entidad TipoPlato ---------------

-- Agregar TipoPlato -- 

Delimiter $$
	create procedure sp_AgregarTipoPlato(in descripcionTipo varchar(100))
		Begin
			Insert Into TipoPlato(descripcionTipo)
            values(descripcionTipo);
        End$$
Delimiter ;

-- Listar TipoPlatos --

Delimiter $$
	create procedure sp_ListarTipoPlatos()
		Begin
			Select t.codigoTipoPlato,
            t.descripcionTipo
            from TipoPlato t;
        End$$
Delimiter ;
call sp_ListarTipoPlatos();

-- Buscar TipoPlato --

Delimiter $$
	create procedure sp_BuscarTipoPlato(in codigo int)
		Begin
			Select t.codigoTipoPlato,
            t.descripcionTipo
            from TipoPlato t where t.codigoTipoPlato=codigo;
        End$$
Delimiter ;

-- Editar TipoPlato --

Delimiter $$
	create procedure sp_EditarTipoPlato(in codigo int, in descripcion varchar(100))
		Begin
			Update TipoPlato t
            set t.codigoTipoPlato=codigo, t.descripcionTipo=descripcion
            where t.codigoTipoPlato=codigo;
        End$$
Delimiter ;

-- Eliminar TipoPlato --

Delimiter $$
	create procedure sp_EliminarTipoPlato(in codigo int)
		Begin
			Delete from TipoPlato
            where codigoTipoPlato=codigo;
        End$$
Delimiter ;

-- ----------------------- Procedimientos almacenados Entidad Platos ---------------

-- Agregar Plato --

Delimiter $$
	create procedure sp_AgregarPlato(in cantidad int, in nombrePlato varchar(50), in descripcionPlato varchar(150), in precioPlato decimal(10,2),
		in codigoTipoPlato int)
        Begin
			Insert into Platos(cantidad, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato)
            values(cantidad, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato);
        End$$
Delimiter ;

-- Listar Platos --

Delimiter $$
	create procedure sp_ListarPlatos()
		Begin
			Select p.codigoPlato,
            p.cantidad,
            p.nombrePlato,
            p.descripcionPlato,
            p.precioPlato,
            p.codigoTipoPlato
            from Platos p;
        End$$
Delimiter ;

-- Buscar Plato --

Delimiter $$
	create procedure sp_BuscarPlato(in codigo int)
		Begin
			Select p.codigoPlato,
            p.cantidad,
            p.nombrePlato,
            p.descripcionPlato,
            p.precioPlato,
            p.codigoTipoPlato
            from Platos p where p.codigoPlato=codigo;
        End$$
Delimiter ; 

-- Editar Plato --

Delimiter $$
	create procedure sp_EditarPlato(in codigo int, in cantidad int, in nombre varchar(50), in descripcion varchar(150), in precio decimal(10,2),
		in codigoT int)
		Begin
			Update Platos p
            set p.codigoPlato=codigo, p.cantidad=cantidad, p.nombrePlato=nombre, p.descripcionPlato=descripcion, p.precioPlato=precio, 
				p.codigoTipoPlato=codigoT
            where  p.codigoPlato=codigo ;
        End$$	
Delimiter ;

-- Eliminar Plato --

Delimiter $$
	create procedure sp_EliminarPlato(in codigo int)
		Begin
			Delete from Platos
            where codigoPlato=codigo;
        End$$
Delimiter ;

-- ----------------------- Procedimientos almacenados Entidad Productos ---------------

-- Agregar Producto --

Delimiter $$
	create procedure sp_AgregarProducto(in codigoProducto int, in nombreProducto varchar(150), in cantidad int )
		Begin
			Insert into Productos (codigoProducto, nombreProducto, cantidad)
            values (codigoProducto, nombreProducto, cantidad);
        End$$
Delimiter ;

-- Listar Productos --

Delimiter $$
	create procedure sp_ListarProductos()
		Begin
			Select p.codigoProducto,
            p.nombreProducto ,
            p.cantidad 
            from Productos p;
        End$$
Delimiter ;
call sp_ListarProductos();

-- Buscar Producto --

Delimiter $$
	create procedure sp_BuscarProducto(in codigo int)
		Begin
			Select p.codigoProducto ,
            p.nombreProducto ,
            p.cantidad
            from Productos p where p.codigoProducto=codigo;
        End$$
Delimiter ;

-- Editar Producto --

Delimiter $$
	create procedure sp_EditarProducto(in codigo int, in nombre varchar(150), in cantidad int)
		Begin
			Update Productos p 
            set p.codigoProducto=codigo, p.nombreProducto=nombre, p.cantidad=cantidad
            where p.codigoProducto=codigo;
        End$$
Delimiter ;

-- Eliminar Producto --

Delimiter $$
	create procedure sp_EliminarProducto(in codigo int)
		Begin
			Delete from Productos
            where codigoProducto=codigo;
        End$$
Delimiter ;

-- ----------------------- Procedimientos almacenados Entidad Productos_has_Platos ---------------

-- Agregar Producto_has_Plato --

Delimiter $$
	create procedure sp_AgregarProducto_has_Plato(in Productos_codigoProducto int, in codigoPlato int, in codigoProducto int)
		Begin
			Insert Into Productos_has_Platos(Productos_codigoProducto, codigoPlato, codigoProducto)
            values(Productos_codigoProducto, codigoPlato, codigoProducto);
        End$$
Delimiter ;

-- Listar Productos_has_Platos --

Delimiter $$
	create procedure sp_ListarProductos_has_Platos()
		Begin
			Select h.Productos_codigoProducto,
            h.codigoPlato,
            h.codigoProducto
			from Productos_has_Platos h;
        End$$
Delimiter ;

-- Buscar Producto_has_Plato --

Delimiter $$
	create procedure sp_BuscarProducto_has_Plato(in codigo int)
		Begin
			Select h.Productos_codigoProducto AS 'Productos_códigoProducto',
            h.codigoPlato AS 'Código Plato',
            h.codigoProducto AS 'Código Producto'
			from Productos_has_Platos h where  h.Productos_codigoProducto=codigo;
        End$$
Delimiter ;

-- Editar Producto_has_Plato --

Delimiter $$
	create procedure sp_EditarProducto_has_Plato(in pCp int, in codigoPl int, in codigoPr int)
		Begin
			Update Producto_has_Plato h
            set h.Productos_codigoProducto=pCp, h.codigoPlato=codigoPl, h.codigoProducto=codigoPr
            where h.Productos_codigoProducto=pCp;
        End$$
Delimiter ;

-- Eliminar Producto_has_Plato --

Delimiter $$
	create procedure sp_EliminarProducto_has_Plato(in codigo int)
		begin
			Delete from Producto_has_Plato
            where Productos_codigoProductos=codigo;
        End$$
Delimiter ;

-- ----------------------- Procedimientos almacenados Entidad Servicios_has_Empleados ---------------

-- Agregar Servicios_has_Empleados --

Delimiter $$
	create procedure sp_AgregarServicio_has_Empleado(in Servicios_codigoServicio int, in codigoServicio int, in codigoEmpleado int, 
		in fechaEvento date, in horaEvento time, in lugarEvento varchar(150))
			Begin
				Insert Into Servicios_has_Empleados(Servicios_codigoServicio, codigoServicio, codigoEmpleado, fechaEvento, horaEvento, 
					lugarEvento)
                values (Servicios_codigoServicio, codigoServicio, codigoEmpleado, fechaEvento, horaEvento, lugarEvento) ;
            End$$
Delimiter ;

-- Listar Servicios_has_Empleados --

Delimiter $$
	create procedure sp_ListarServicios_has_Empleados()
		Begin
			Select s.Servicios_codigoServicio,
            s.codigoServicio,
            s.codigoEmpleado,
            s.fechaEvento,
            s.horaEvento,
            s.lugarEvento
            from Servicios_has_Empleados s;
        End$$
Delimiter ;

-- Buscar Servicios_has_Empleados --

Delimiter $$
	create procedure sp_BuscarServicio_has_Empleado(in codigo int)
		Begin 
        Select s.Servicios_codigoServicio,
            s.codigoServicio,
            s.codigoEmpleado,
            s.fechaEvento,
            s.horaEvento,
            s.lugarEvento
            from Servicios_has_Empleados s where s.Servicios_codigoServicio=codigo;
        End$$
Delimiter ;

-- Editar Servicios_has_Empleados --

Delimiter $$
	create procedure sp_EditarServicio_has_Empleado(in ScS int, in codigoS int, in codigoE int, in fecha date, in hora time, 
		in lugar varchar(150))
        Begin
			Update Servicios_has_Empleados s 
            set s.Servicios_codigoServicio=ScS, s.codigoServicio=codigoS, s.codigoEmpleado=codigoE, s.fechaEvento=fecha, s.horaEvento=hora
				, s.lugarEvento=lugar
             where  s.Servicios_codigoServicio=ScS;  
        End$$
Delimiter ;

-- Eliminar Servicios_has_Empleados --

Delimiter $$
	create procedure sp_EliminarServicio_has_Empleado(in codigo int)
		Begin
			Delete from Servicios_has_Empleados 
            where Servicios_codigoServicio=codigo;
        End$$
Delimiter ;

-- ----------------------- Procedimientos almacenados Entidad Servicios_has_Platos ---------------

-- Agregar Servicio_has_Plato --

Delimiter $$
	create procedure sp_AgregarServicio_has_Plato(in Servicios_codigoServicio int, in codigoPlato int, in codigoServicio int)
		Begin
			Insert Into Servicios_has_Platos(Servicios_codigoServicio, codigoPlato, codigoServicio)
            values (Servicios_codigoServicio, codigoPlato, codigoServicio);
        End$$
Delimiter ;

-- Listar Servicios_has_Platos --

Delimiter $$
	create procedure sp_ListarServicios_has_Platos()
		Begin
			Select s.Servicios_codigoServicio,
            s.codigoPlato,
            s.codigoServicio
            from Servicios_has_Platos s;
        End$$
Delimiter ;

-- Buscar Servicio_has_Plato --

Delimiter $$
	create procedure sp_BuscarServicio_has_Plato(in codigo int)
		Begin
			Select s.Servicios_codigoServicio AS 'Servicios_códigoServicio',
            s.codigoPlato AS 'Código Plato',
            s.codigoServicio AS 'Código Servicio'
            from Servicios_has_Platos s where s.Servicios_codigoServicio=codigo;
        End$$
Delimiter ;

-- Editar Servicio_has_Plato --

Delimiter $$
	create procedure sp_EditarServicio_has_Plato(in ScS int, in codigoP int, in codigoS int)
		Begin
			Update Servicios_has_Platos s
            set s.Servicios_codigoServicio=ScS, s.codigoPlato=codigoP, s.codigoServicio=codigoS
            where s.Servicios_codigoServicio=ScS;
        End$$
Delimiter ; 

-- Eliminar Servicio_has_Plato --

Delimiter $$
	create procedure sp_EliminarServicio_has_Plato(in codigo int)
		Begin
			Delete from Servicios_has_Platos
            where Servicios_codigoServicio=codigo;
        End$$
Delimiter ;


Call sp_AgregarTipoEmpleado("Mesero");
Call sp_AgregarTipoEmpleado("Chef");
Call sp_AgregarTipoEmpleado("Personal de Limpieza");
Call sp_AgregarTipoEmpleado("Músicos");
Call sp_AgregarTipoEmpleado("Asistente de chef");


Call sp_AgregarEmpleado(001, "Mendez Calderon", "Juan Alberto", "12av. 1-78 Zona 5", "2145-8987", "Mesero", 1);
Call sp_AgregarEmpleado(002, "Morales Velásquez", "Oscar José", "13av. 18-15 Zona 10", "8965-4512", "Chef Ejecutivo", 2);
Call sp_AgregarEmpleado(003, "Maeda Estrada", "Santiago Didiee", "8av. 46-80 Zona 22", "2468-2465", "Limpieza", 3);
Call sp_AgregarEmpleado(004, "Monzón Pineda", "Jaquelyn Nicole", "11 av. 89-15 Zona 7", "6246-4752", "Cocinera", 2);
Call sp_AgregarEmpleado(005, "Matias Morales", "Jose David", "5 av. 23-10 Zona 18", "1155-4301", "Músico", 4);


Call sp_AgregarTipoPlato("Entrada Caliente");
Call sp_AgregarTipoPlato("Plato Fuerte");
Call sp_AgregarTipoPlato("Postres");
Call sp_AgregarTipoPlato("Guarniciones");
Call sp_AgregarTipoPlato("Entrada Fría");



Call sp_AgregarProducto(1, "Caja de Leche", 10);
Call sp_AgregarProducto(2, "Libras del Sal", 75);
Call sp_AgregarProducto(3, "Sartenes", 35);
Call sp_AgregarProducto(4, "Cebolla", 88);
Call sp_AgregarProducto(5, "Tomate", 110);

Call sp_AgregarServicio('2023-10-22', "Servicio de Postres", now(), "5av. 11-16 Zona 3, Guatemala", "2504 8623", 1);
Call sp_AgregarServicio('2022-10-22', "Servicio de Comida", now(), "3av. 38-65 Zona 8, Guatemala", "2200 7865", 2);
Call sp_AgregarServicio('2023-11-22', "Servicio de Comida", now(), "San Cristóbal 18-95, Cdad. de Guatemala",
 "2200 7896", 3);
Call sp_AgregarServicio('2023-10-28', "Servicio de Comida", now(),"6a. Avenida, Zona 9, Cdad. de Guatemala", "2229 7823", 4);
Call sp_AgregarServicio('2023-12-25', "Servicio de Bebida", now(),"Calzada Roosvelt", "4430 7715", 5);


Call sp_AgregarPresupuesto(now(), 7800.90, 1);
Call sp_AgregarPresupuesto(now(), 7000.60, 2);
Call sp_AgregarPresupuesto(now(), 8900.40, 3);
Call sp_AgregarPresupuesto(now(), 4219.00, 4);
Call sp_AgregarPresupuesto(now(), 8950.00, 5);

Call sp_AgregarPlato(45, "Helado de fresa", "Helado con sabor a fresa", 19.00, 3);
Call sp_AgregarPlato(100, "Spaghetti a la bolognesa", "Una deliciosa pasta italiana que se sirve con una salsa boloñesa,
hecha de carne picada de res, tomate, cebolla, zanahoria y apio.", 119.00, 2);
Call sp_AgregarPlato(80, "Tacos al Pastor", "Un plato mexicano que consiste en tortillas de maíz rellenas de carne de cerdo marinada
 en una mezcla de especias", 98.00, 2);
Call sp_AgregarPlato(110, "Arroz blanco", "Arroz blanco precocido", 45.00, 4);
Call sp_AgregarPlato(140, "Refresco de limonada de fresa", "Es un jugo que incluye limón y fresa y su combinación termina siendo excelente", 45.00, 5);

call sp_ListarPlatos();
call sp_ListarProductos();
call sp_ListarProductos_has_Platos();
Call sp_AgregarProducto_has_Plato(1, 1, 1);
Call sp_AgregarProducto_has_Plato(2, 2, 2);
Call sp_AgregarProducto_has_Plato(3, 3, 3);
Call sp_AgregarProducto_has_Plato(4, 4, 4);
Call sp_AgregarProducto_has_Plato(5, 5, 5);
call sp_ListarServicios_has_Platos();
Call sp_AgregarServicio_has_Plato(1, 1, 1);
Call sp_AgregarServicio_has_Plato(2, 2, 2);
Call sp_AgregarServicio_has_Plato(3, 3, 3);
Call sp_AgregarServicio_has_Plato(4, 4, 4);
Call sp_AgregarServicio_has_Plato(5, 5, 5);

Call sp_AgregarServicio_has_Empleado(1, 1, 1, '2023-02-10',now(), "San Cristobal" );
Call sp_AgregarServicio_has_Empleado(2, 2, 2, '2017-08-15', now(), "San Lucas");
Call sp_AgregarServicio_has_Empleado(3, 3, 3, '2022-08-20', now(), "Ciudad de Guatemala");
Call sp_AgregarServicio_has_Empleado(4, 4, 4, '2023-10-28', now(), "Petén");
Call sp_AgregarServicio_has_Empleado(5, 5, 5, '2023-11-01', now(), "Sololá");


Delimiter $$
    Create procedure sp_ReporteGeneral()
        Begin
            Select * from Empresas E Inner Join Presupuestos P on E.codigoEmpresa = P.codigoEmpresa
                Inner join Servicios S on E.codigoEmpresa = S.codigoEmpresa
                    Inner join  Servicios_has_Empleados SHE on S.codigoServicio = SHE.codigoServicio
                        Inner join Empleados Em on SHE.codigoEmpleado = Em.codigoEmpleado
                            Inner join TipoEmpleado TE on Em.codigoTipoEmpleado = TE.codigoTipoEmpleado
                                Inner join Servicios_has_Platos SHP on S.codigoServicio = SHP.codigoServicio
                                    Inner join Platos Pl on SHP.codigoPlato = Pl.codigoPlato
                                        Inner join TipoPlato TP on Pl.codigoTipoPlato = TP.codigoTipoPlato
                                            Inner join Productos_has_Platos PHP on Pl.codigoPlato = PHP.codigoPlato
                                                Inner join Productos Pr on PHP.codigoProducto = Pr.codigoProducto;
        End $$
Delimiter ;
call sp_ReporteGeneral();
call sp_ListarPlatos();
-- use DBTonysKinal2023_2019484;

/*ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin';
flush privileges;*/




                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          