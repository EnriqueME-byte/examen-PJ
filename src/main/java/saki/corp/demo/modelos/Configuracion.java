package saki.corp.demo.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblconfiguraciones")
public class Configuracion {

    @Id
    @Getter @Column(name ="id_configuracion_carga")
    private long id_configuracion_carga;

    @Getter @Setter @Column(name ="proporcion")
    private int proporcion;

    @Getter @Setter @Column(name = "diferencia")
    private int diferencia;

    @Getter @Setter @Column(name ="anio")
    private int anio;

    @Getter @Setter @Column(name ="activo")
    private int activo;
}
