package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Orden.class)
public abstract class Orden_ {

	public static volatile SingularAttribute<Orden, String> accion;
	public static volatile SingularAttribute<Orden, Integer> cliente;
	public static volatile SingularAttribute<Orden, Float> precio;
	public static volatile SingularAttribute<Orden, String> estado;
	public static volatile SingularAttribute<Orden, Integer> accionId;
	public static volatile SingularAttribute<Orden, Long> id;
	public static volatile SingularAttribute<Orden, String> operacion;
	public static volatile SingularAttribute<Orden, Integer> cantidad;
	public static volatile SingularAttribute<Orden, String> modo;
	public static volatile SingularAttribute<Orden, String> fechaOperacion;

	public static final String ACCION = "accion";
	public static final String CLIENTE = "cliente";
	public static final String PRECIO = "precio";
	public static final String ESTADO = "estado";
	public static final String ACCION_ID = "accionId";
	public static final String ID = "id";
	public static final String OPERACION = "operacion";
	public static final String CANTIDAD = "cantidad";
	public static final String MODO = "modo";
	public static final String FECHA_OPERACION = "fechaOperacion";

}

