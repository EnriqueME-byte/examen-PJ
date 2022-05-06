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
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ControladorCargas {

    @Autowired
    private CargaServicio cargaServicio;

    @RequestMapping(value = "cargar", method = RequestMethod.POST)
    public String cargarConfiguracionCarga(@RequestBody Configuracion c){
        if(cargaServicio.cargarConfiguracion(c).compareTo("ok") == 0)
            return "Carga insertado correctamente";
        return "Error en la carga";
    }

    @PostMapping(value = "solicitud")
    public String ingresarSolicitud(@RequestBody Solicitud s){
        return cargaServicio.cargarSolicitud(s);
    }

    @RequestMapping(value = "solicitud", method = RequestMethod.GET)
    public List<Solicitud> obtenerSolicitudes(){
        return cargaServicio.solicitudes();
    }

    @DeleteMapping(value = "solicitud/{id}")
    public String eliminaSolicitud(@PathVariable long id){
        return cargaServicio.eliminaSolicitud(id);
    }

    @PutMapping(value = "solicitud")
    public String editarSolicitud(@RequestBody Solicitud s) {
        return cargaServicio.editaSolicitud(s);
    }

}
