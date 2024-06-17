create table pacientes (
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    telefono varchar(100) not null,
    email varchar(100) not null,
    documento_identidad varchar(8) not null,
    calle varchar(100) not null,
    distrito varchar(100) not null,
    complemento varchar(100) not null,
    numero varchar(10) not null,
    ciudad varchar(100) not null,
    primary key(id)
);