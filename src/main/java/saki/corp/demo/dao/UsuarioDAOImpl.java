package saki.corp.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import saki.corp.demo.modelos.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDAOImpl implements UsuarioDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Usuario verificarCredenciales(Usuario u) {
        String query = "FROM Usuario WHERE nombre = :nombre AND password = :password";
        List<Usuario> listaU = entityManager.createQuery(query).setParameter("nombre",u.getNombre())
                .setParameter("password",u.getPassword())
                .getResultList();
        if(!listaU.isEmpty())
            return listaU.get(0);
        return null;
    }
}
