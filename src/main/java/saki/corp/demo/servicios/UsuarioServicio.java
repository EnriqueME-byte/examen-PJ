package saki.corp.demo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saki.corp.demo.dao.UsuarioDAO;
import saki.corp.demo.modelos.Usuario;
import saki.corp.demo.util.JWTUtils;

import java.util.List;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private JWTUtils jwt;

    public String verificarCreedencial(Usuario u){

        if(u.getNombre().isEmpty())
            return "Campo de nombre vacío";
        if(u.getPassword().isEmpty())
            return "Campo de password vacio";
        Usuario usu = usuarioDAO.verificarCredenciales(u);
        if(usu != null)
            return jwt.crear(String.valueOf(usu.getId_usuario()),usu.getNombre(),usu.getLogin());
        return "No se encontro el usuario";
    }
}
