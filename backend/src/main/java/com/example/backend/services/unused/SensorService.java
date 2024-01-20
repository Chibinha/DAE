package com.example.backend.services.unused;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.dtos.SensorDTO;
import com.example.backend.ejbs.SensorBean;
import jakarta.ejb.EJB;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/sensors")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SensorService {

    @EJB
    private SensorBean sensorBean;
    private final DTOConverter dtoConverter = new DTOConverter();

    @GET
    @Path("/all")
    @Transactional
    public List<SensorDTO> getAllSensors() {
        // Assuming you have a method named 'getAllSensors' in your bean
        return dtoConverter.sensorToDTOList(sensorBean.getAll());
    }
}
