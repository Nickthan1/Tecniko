/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nikos.tecniko.exceptions.PropertyException;
import nikos.tecniko.exceptions.PropertyOwnerException;
import nikos.tecniko.exceptions.PropertyRepairException;
import nikos.tecniko.services.LoadDataService;

/**
 *
 * @author legeo
 */
@Path("loadData")
public class LoadDataResource {

    @Inject
    private LoadDataService loadDataService;
    @Inject
    private ServletContext servletContext;

    private final String OWNERS = "/WEB-INF/data/owners.csv";
    private final String PROPERTIES = "/WEB-INF/data/properties.csv";
    private final String PROPERTIES_REPAIRS = "/WEB-INF/data/propertyRepairs.csv";

    @Path("/")
    @PermitAll
    @GET
    public Response load() throws IOException {
        loadData();
        return Response
                .ok("{\"message\":\"Data loaded from csv to sql\"}")
                .build();
    }

    private void loadData() {
        String owners = servletContext.getRealPath(OWNERS);
        String properties = servletContext.getRealPath(PROPERTIES);
        String propertyRepairs = servletContext.getRealPath(PROPERTIES_REPAIRS);
       
        try {
            loadDataService.loadOwners(owners);
        } catch (PropertyOwnerException ex) {
            Logger.getLogger(LoadDataResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            loadDataService.loadProperties(properties);
        } catch (PropertyException ex) {
            Logger.getLogger(LoadDataResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            loadDataService.loadPropertyRepairs(propertyRepairs);
        } catch (PropertyRepairException ex) {
            Logger.getLogger(LoadDataResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
