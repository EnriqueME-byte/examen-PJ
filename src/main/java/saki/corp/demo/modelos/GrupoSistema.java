package saki.corp.demo.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="tblgrupos_sistema")
public class GrupoSistema {

    @Id
    @Getter @Column(name ="cve_grupo_sistema")
    private long cve_grupo_sistema;

    @Getter @Setter @Column(name = "descripcion_grupo")
    private String descripcion_grupo;

    @OneToMany(mappedBy = "grupoSistema")
    @Getter @Setter
    private List<Usuario> usuarios;

}
