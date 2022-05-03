package saki.corp.demo.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tblbitacoras")
public class Bitacora {

    @Id
    @Getter @Setter @Column(name = "id_bitacora")
    private long id_bitacora;

    @Getter @Setter @Column(name = "fecha")
    private Date fecha;

    @Getter @Setter @Column(name = "movimiento")
    private String movimiento;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @Getter @Setter
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cve_accion")
    @Getter @Setter
    private Accion accion;

}
