package saki.corp.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import saki.corp.demo.modelos.Usuario;
import saki.corp.demo.servicios.UsuarioServicio;

@RestController
@RequestMapping(value ="api")
@CrossOrigin(origins = "*")
public class ControladorUsuario {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String logear(@RequestBody Usuario u){
        return usuarioServicio.verificarCreedencial(u);
    }

}
