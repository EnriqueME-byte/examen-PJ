package saki.corp.demo.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblusuarios")
public class Usuario {

    @Id
    @Getter
    private long id_usuario;

    @Getter @Setter @Column(name ="nombre")
    private String nombre;

    @Getter @Setter @Column(name ="paterno")
    private String paterno;

    @Getter @Setter @Column(name ="materno")
    private String maternoe;

    @Getter @Setter @Column(name ="login")
    private String login;

    @Getter @Setter @Column(name ="password")
    private String password;

    @Getter @Setter @Column(name ="activo")
    private int activo;

    @ManyToOne
    @JoinColumn(name="cve_grupo_sistema")
    @Getter @Setter
    private GrupoSistema grupoSistema;

    @OneToMany(mappedBy = "usuario")
    @Getter @Setter
    private List<Solicitud> solicitudeS;

    @OneToMany(mappedBy = "usuario")
    @Getter @Setter
    private  List<ControlCarga> controlCargas;

    @OneToMany(mappedBy = "usuario")
    @Getter @Setter
    private List<Bitacora> bitacoras;

}
