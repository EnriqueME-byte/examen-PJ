package saki.corp.demo.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tblcontrolcarga")
public class ControlCarga {
    @Id
    @Getter @Setter @Column(name = "id_control_carga")
    private long id_control_carga;

    @ManyToOne
    @JoinColumn(name ="id_usuario")
    @Getter @Setter
    private Usuario usuario;

    @Getter @Setter @Column(name = "anio")
    private int anio;

    @Getter @Setter @Column(name = "total")
    private int total;
}
