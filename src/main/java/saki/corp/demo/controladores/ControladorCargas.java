package saki.corp.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import saki.corp.demo.modelos.Configuracion;
import saki.corp.demo.modelos.Solicitud;
import saki.corp.demo.servicios.CargaServicio;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api")
@CrossOrigin(origins = "*")
public class ControladorCargas {

    @Autowired
    private CargaServicio cargaServicio;

    @RequestMapping(value = "cargar", method = RequestMethod.POST)
    public String cargarConfiguracionCarga(@RequestBody Configuracion c){
        if(cargaServicio.cargarConfiguracion(c).compareTo("ok") == 0)
            return "Carga insertado correctamente";
        return "Error en la carga";
    }

    @RequestMapping(value = "solicitud", method = RequestMethod.POST)
    public String ingresarSolicitud(@RequestBody Solicitud s){
        return cargaServicio.cargarSolicitud(s);
    }

    @RequestMapping(value = "solicitud", method = RequestMethod.GET)
    public List<Solicitud> obtenerSolicitudes(){
        return cargaServicio.solicitudes();
    }

    @RequestMapping(value = "solicitud/{id}", method = RequestMethod.DELETE)
    public String eliminaSolicitud(@PathVariable long id){
        return cargaServicio.eliminaSolicitud(id);
    }

}
