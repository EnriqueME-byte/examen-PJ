package saki.corp.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import saki.corp.demo.modelos.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CargaDAOImpl implements CargaDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void cargarConfiguracion(Configuracion c) {
        entityManager.merge(c);
    }

    @Override
    public boolean ingresarSolicitud(Solicitud s) {
        String query = "FROM ControlCarga ORDER BY total";
        List<ControlCarga> mayor = entityManager.createQuery(query).getResultList();
        int total;
        long id_user;
        Usuario usu = new Usuario();
        for (int i = 0; i < mayor.size(); i++) {
            String queryS = "FROM Usuario WHERE id_usuario = :id_usuario AND grupoSistema.cve_grupo_sistema = 1";
            List<Usuario> u = entityManager.createQuery(queryS)
                    .setParameter("id_usuario",mayor.get(i).getUsuario().getId_usuario()).getResultList();
            if(!u.isEmpty()){// si no esta vacia significa que encontró un usuario para asignarle carga
                total = mayor.get(i).getTotal() + 1;
                id_user = mayor.get(i).getUsuario().getId_usuario();
                String qUpdate = "UPDATE ControlCarga SET total = :total WHERE usuario.id_usuario = :id_usuario";
                entityManager.createQuery(qUpdate)
                                .setParameter("total",total)
                                        .setParameter("id_usuario",id_user)
                                                .executeUpdate(); //se actualiza la carga
                s.setUsuario(mayor.get(i).getUsuario());
                s.setFecha(new Date());
                entityManager.merge((s));//se inserta en solicitud
                usu = mayor.get(i).getUsuario();
                break;
            }else{
                return false;
            }
        }

        Accion accion = new Accion();
        accion.setDescripcion("Se ingresó una solicitud ");
        accion.setActivo(1);
        entityManager.merge(accion);//INSERTA EN ACCIONES

        String queryMax = "SELECT max(cve_accion) FROM Accion";
        Query maxQuery = entityManager.createQuery(queryMax);
        long cveAccion = (long)maxQuery.getResultList().get(0);

        Accion a = entityManager.find(Accion.class,cveAccion);
        Bitacora bitacora = new Bitacora();
        bitacora.setAccion(a);
        bitacora.setUsuario(usu);
        bitacora.setFecha(new Date());
        bitacora.setMovimiento("Se ingresó solicitud");
        entityManager.merge(bitacora);

        return true;
    }

    @Override
    public List<Solicitud> consultarSolicitudes() {
        String queryS = "FROM Solicitud";
        List<Solicitud> solicitudes = entityManager.createQuery(queryS).getResultList();
        return solicitudes;
    }

    @Override
    public boolean eliminarSolicitud(long id_solicitud) {
        Solicitud s = entityManager.find(Solicitud.class,id_solicitud);
        if(s != null){
            entityManager.remove(s);
            Accion accion = new Accion();
            accion.setDescripcion("Se eliminó la solicitud con id " + s.getId_solicitud());
            accion.setActivo(1);
            entityManager.merge(accion);//INSERTA EN ACCIONES

            String queryMax = "SELECT max(cve_accion) FROM Accion";
            Query maxQuery = entityManager.createQuery(queryMax);
            long cveAccion = (long)maxQuery.getResultList().get(0);

            Accion a = entityManager.find(Accion.class,cveAccion);
            Bitacora bitacora = new Bitacora();
            bitacora.setAccion(a);
            bitacora.setUsuario(s.getUsuario());
            bitacora.setFecha(new Date());
            bitacora.setMovimiento("Se eliminó la solicitud del solicitante " +
                    s.getNombre_solicitante() + " " + s.getPaterno_solicitante());
            entityManager.merge(bitacora);
            return true;
        }
        return false;
    }
}
