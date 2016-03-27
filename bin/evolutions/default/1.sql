# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cliente (
  id                            bigint not null,
  codigo_cliente                varchar(255),
  apellido_familia              varchar(255),
  telefono                      varchar(255),
  tipo_pago                     varchar(255),
  constraint pk_cliente primary key (id)
);
create sequence cliente_seq;

create table enfermedad (
  id                            bigint not null,
  tipo                          varchar(255),
  nombre                        varchar(255),
  motivo                        varchar(255),
  datacion                      varchar(255),
  estado                        varchar(255),
  historial_id                  bigint,
  constraint pk_enfermedad primary key (id)
);
create sequence enfermedad_seq;

create table historial (
  id                            bigint not null,
  descripcion                   varchar(255),
  constraint pk_historial primary key (id)
);
create sequence historial_seq;

create table mascota (
  id                            bigint not null,
  codigo_mascota                varchar(255),
  apodo                         varchar(255),
  especie                       varchar(255),
  raza                          varchar(255),
  fecha_nacimiento              varchar(255),
  peso_medio                    varchar(255),
  peso_actual                   varchar(255),
  historial_id                  bigint,
  cliente_id                    bigint,
  constraint uq_mascota_historial_id unique (historial_id),
  constraint pk_mascota primary key (id)
);
create sequence mascota_seq;

create table persona (
  id                            bigint not null,
  nombre                        varchar(255),
  apellidos                     varchar(255),
  dni                           varchar(255),
  cliente_id                    bigint,
  constraint pk_persona primary key (id)
);
create sequence persona_seq;

create table vacuna (
  id                            bigint not null,
  tipo                          varchar(255),
  dosis                         varchar(255),
  detalle                       varchar(255),
  historial_id                  bigint,
  constraint pk_vacuna primary key (id)
);
create sequence vacuna_seq;

alter table enfermedad add constraint fk_enfermedad_historial_id foreign key (historial_id) references historial (id) on delete restrict on update restrict;
create index ix_enfermedad_historial_id on enfermedad (historial_id);

alter table mascota add constraint fk_mascota_historial_id foreign key (historial_id) references historial (id) on delete restrict on update restrict;

alter table mascota add constraint fk_mascota_cliente_id foreign key (cliente_id) references cliente (id) on delete restrict on update restrict;
create index ix_mascota_cliente_id on mascota (cliente_id);

alter table persona add constraint fk_persona_cliente_id foreign key (cliente_id) references cliente (id) on delete restrict on update restrict;
create index ix_persona_cliente_id on persona (cliente_id);

alter table vacuna add constraint fk_vacuna_historial_id foreign key (historial_id) references historial (id) on delete restrict on update restrict;
create index ix_vacuna_historial_id on vacuna (historial_id);


# --- !Downs

alter table enfermedad drop constraint if exists fk_enfermedad_historial_id;
drop index if exists ix_enfermedad_historial_id;

alter table mascota drop constraint if exists fk_mascota_historial_id;

alter table mascota drop constraint if exists fk_mascota_cliente_id;
drop index if exists ix_mascota_cliente_id;

alter table persona drop constraint if exists fk_persona_cliente_id;
drop index if exists ix_persona_cliente_id;

alter table vacuna drop constraint if exists fk_vacuna_historial_id;
drop index if exists ix_vacuna_historial_id;

drop table if exists cliente;
drop sequence if exists cliente_seq;

drop table if exists enfermedad;
drop sequence if exists enfermedad_seq;

drop table if exists historial;
drop sequence if exists historial_seq;

drop table if exists mascota;
drop sequence if exists mascota_seq;

drop table if exists persona;
drop sequence if exists persona_seq;

drop table if exists vacuna;
drop sequence if exists vacuna_seq;

