package saki.corp.demo.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tblsolicitudes")
public class Solicitud {

    @Id
    @Getter @Setter @Column(name ="id_solicitud")
    private long id_solicitud;

    @ManyToOne
    @JoinColumn(name="id_usuario")
    @JsonIgnore
    @Getter @Setter
    private Usuario usuario;

    @Getter @Setter @Column(name="nombre_solicitante")
    private String nombre_solicitante;

    @Getter @Setter @Column(name="paterno_solicitante")
    private String paterno_solicitante;

    @Getter @Setter @Column(name="materno_solicitate")
    private String materno_solicitante;

    @Getter @Setter @Column(name = "activo")
    private int activo;

    @Getter @Setter @Column(name = "fecha_solicitud")
    private Date fecha;
}
