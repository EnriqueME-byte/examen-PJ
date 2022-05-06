package saki.corp.demo.dao;

import org.springframework.web.bind.annotation.RequestBody;
import saki.corp.demo.modelos.Configuracion;
import saki.corp.demo.modelos.Solicitud;

import java.util.List;

public interface CargaDAO {

    public void cargarConfiguracion(Configuracion c);
    public boolean ingresarSolicitud(Solicitud s);
    public List<Solicitud> consultarSolicitudes();
    public boolean eliminarSolicitud(long id_solicitud);
    public boolean editarSolicitud(Solicitud s);
}
