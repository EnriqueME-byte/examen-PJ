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
        List<ControlCarga> menor= entityManager.createQuery(query).getResultList();
        int total;
        long id_user = 0;
        Usuario usu = new Usuario();
        for (int i = 0; i < menor.size(); i++) {
            String queryS = "FROM Usuario WHERE id_usuario = :id_usuario AND grupoSistema.cve_grupo_sistema = 1";
            List<Usuario> u = entityManager.createQuery(queryS)
                    .setParameter("id_usuario",menor.get(i).getUsuario().getId_usuario()).getResultList();
            if(!u.isEmpty()){// si no esta vacia significa que encontró un usuario para asignarle carga
                total = menor.get(i).getTotal() + 1;
                id_user = menor.get(i).getUsuario().getId_usuario();
                String qUpdate = "UPDATE ControlCarga SET total = :total WHERE usuario.id_usuario = :id_usuario";
                entityManager.createQuery(qUpdate)
                                .setParameter("total",total)
                                        .setParameter("id_usuario",id_user)
                                                .executeUpdate(); //se actualiza la carga
                s.setUsuario(menor.get(i).getUsuario());
                s.setFecha(new Date());
                entityManager.merge((s));//se inserta en solicitud
                usu = menor.get(i).getUsuario();
                break;
            }else{
                if(i == menor.size() - 1)
                    return false;
            }
        }

        Accion accion = new Accion();
        accion.setDescripcion("Se ingresó una solicitud asignada al vendedor con id " + id_user);
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
        bitacora.setMovimiento("Se ingresó solicitud del solicitante: " + s.getNombre_solicitante());
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

    @Override
    public boolean editarSolicitud(Solicitud s) {
        String query = "UPDATE Solicitud SET nombre_solicitante = :nombre_solicitante, " +
                "paterno_solicitante = :paterno_solicitante, " +
                "materno_solicitante = :materno_solicitante, activo = :activo " +
                "WHERE id_solicitud = :id_solicitud";
        entityManager.createQuery(query)
                .setParameter("id_solicitud",s.getId_solicitud())
                .setParameter("nombre_solicitante",s.getNombre_solicitante())
                .setParameter("paterno_solicitante", s.getPaterno_solicitante())
                .setParameter("materno_solicitante",s.getMaterno_solicitante())
                .setParameter("activo",s.getActivo())
                .executeUpdate();

        Accion accion = new Accion();
        accion.setDescripcion("Se editó la solicitud con id " + s.getId_solicitud());
        accion.setActivo(1);
        entityManager.merge(accion);//INSERTA EN ACCIONES

        String queryMax = "SELECT max(cve_accion) FROM Accion";
        Query maxQuery = entityManager.createQuery(queryMax);
        long cveAccion = (long)maxQuery.getResultList().get(0);

        Solicitud solicitud = entityManager.find(Solicitud.class, s.getId_solicitud());

        Accion a = entityManager.find(Accion.class,cveAccion);
        Bitacora bitacora = new Bitacora();
        bitacora.setAccion(a);
        bitacora.setUsuario(solicitud.getUsuario());
        bitacora.setFecha(new Date());
        bitacora.setMovimiento("Se editó la solicitud del solicitante " +
                s.getNombre_solicitante() + " " + s.getPaterno_solicitante());
        entityManager.merge(bitacora);
        return true;
    }
}
