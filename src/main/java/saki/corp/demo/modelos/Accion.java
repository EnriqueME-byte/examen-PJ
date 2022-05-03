package saki.corp.demo.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="tblacciones")
public class Accion {

    @Id
    @Getter @Setter @Column(name = "cve_accion")
    private long cve_accion;

    @Getter @Setter @Column(name = "descripcion")
    private String descripcion;

    @Getter @Setter @Column(name = "activo")
    private int activo;

    @OneToMany(mappedBy = "accion")
    @Getter @Setter
    private List<Bitacora> bitacoras;
}
