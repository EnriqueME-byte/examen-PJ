package saki.corp.demo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saki.corp.demo.dao.CargaDAO;
import saki.corp.demo.modelos.Configuracion;
import saki.corp.demo.modelos.Solicitud;

import java.util.List;
import java.util.Map;

@Service
public class CargaServicio {

    @Autowired
    private CargaDAO cargaDAO;

    public String cargarConfiguracion(Configuracion c){
        Map<Integer,String> mensajes;
        if(c.getProporcion() == 0)
            return"proporcion no puede ser cero";
        if(c.getDiferencia() == 0)
            return "Diferencia no puede ser 0";
        cargaDAO.cargarConfiguracion(c);
        return "ok";
    }

    public String cargarSolicitud(Solicitud s){
        if(s.getNombre_solicitante().isEmpty())
            return "Falta rellenar campo de nombre";
        if(s.getPaterno_solicitante().isEmpty())
            return "Falta rellenar campo de apellido paterno";
        if(s.getMaterno_solicitante().isEmpty())
            return "Falta rellenar el campo de apellido materno";

        return (cargaDAO.ingresarSolicitud(s)) ? "solicitud ingresada" : "error";
    }

    public List<Solicitud> solicitudes(){
        return cargaDAO.consultarSolicitudes();
    }

    public String eliminaSolicitud(long id){
        return (cargaDAO.eliminarSolicitud(id)) ? "Eliminado" : "Error";
    }

    public String editaSolicitud(Solicitud s){
        return cargaDAO.editarSolicitud(s) ? "Editado con exito" : "Error durate la edición";
    }
}
