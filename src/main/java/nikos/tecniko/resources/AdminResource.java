/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.Instant;
import java.util.List;
import nikos.tecniko.dto.PropertyDto;
import nikos.tecniko.dto.PropertyRepairDto;
import nikos.tecniko.dto.UserDto;
import nikos.tecniko.repositories.PropertyOwnerRepository;
import nikos.tecniko.repositories.PropertyRepairRepository;
import nikos.tecniko.repositories.PropertyRepository;
import nikos.tecniko.services.AdminService;

/**
 *
 * @author legeo
 */
@Path("admin")
public class AdminResource {

    @Inject
    private AdminService adminService;
    @Inject
    private PropertyOwnerRepository propertyOwnerRepo;
    @Inject
    private PropertyRepository propertyRepo;
    @Inject
    private PropertyRepairRepository propertyRepairRepo;

    @GET
    @Path("getOwners")
    //@RolesAllowed({"ADMIN"})
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<UserDto>  getAllOwners() {
        List<UserDto> userList = adminService.readOwners();
        StringBuilder builder = new StringBuilder();
        for (UserDto user : userList) {
            builder.append(user.toString());
            builder.append("\n");
        }
        
        return userList;
    }

    @GET
    @Path("getProperties")
    //@RolesAllowed({"ADMIN"})
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<PropertyDto> getAllProperties() {
        List<PropertyDto> propertyList = adminService.readProperties();
        StringBuilder builder = new StringBuilder();
        for (PropertyDto property : propertyList) {
            builder.append(property.toString());
            builder.append("\n");
        }
        return propertyList;
    }

    @GET
    @Path("getPropertyRepairs")
    //@RolesAllowed({"ADMIN"})
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<PropertyRepairDto> getAllPropertyRepairs() {
            List<PropertyRepairDto> propertyRepairList = adminService.readAllPropertyRepairs();
            StringBuilder builder = new StringBuilder();
            for (PropertyRepairDto propertyRepair : propertyRepairList) {
                builder.append(propertyRepair.toString());
                builder.append("\n");
            }
            return propertyRepairList;
    }

    @GET
    @Path("getPendingRepairs")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllPendingRepairs() {
            List<PropertyRepairDto> propertyRepairList = adminService.readPendingPropertyRepairs();
            StringBuilder builder = new StringBuilder();
            for (PropertyRepairDto propertyRepair : propertyRepairList) {
                builder.append(propertyRepair.toString());
                builder.append("\n");
            }
            return Response
                    .ok(builder.toString())
                    .build();
    }

    @GET
    @Path("getInProgressRepairs")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getInProgressRepairs() {
            List<PropertyRepairDto> propertyRepairList = adminService.readInProgressPropertyRepairs();
            StringBuilder builder = new StringBuilder();
            for (PropertyRepairDto propertyRepair : propertyRepairList) {
                builder.append(propertyRepair.toString());
                builder.append("\n");
            }
            return Response
                    .ok(builder.toString())
                    .build();
    }

    @GET
    @Path("getAcceptedRepairs")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAcceptedRepairs() {
            List<PropertyRepairDto> propertyRepairList = adminService.readAcceptedPropertyRepairs();
            StringBuilder builder = new StringBuilder();
            for (PropertyRepairDto propertyRepair : propertyRepairList) {
                builder.append(propertyRepair.toString());
                builder.append("\n");
            }
            return Response
                    .ok(builder.toString())
                    .build();
    }

    @GET
    @Path("getDeclinedRepairs")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getDeclinedRepairs() {
            List<PropertyRepairDto> propertyRepairList = adminService.readDeclinedPropertyRepairs();
            StringBuilder builder = new StringBuilder();
            for (PropertyRepairDto propertyRepair : propertyRepairList) {
                builder.append(propertyRepair.toString());
                builder.append("\n");
            }
            return Response
                    .ok(builder.toString())
                    .build();
    }

    @GET
    @Path("getCompletedRepairs")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCompletedRepairs() {
            List<PropertyRepairDto> propertyRepairList = adminService.readCompletedPropertyRepairs();
            StringBuilder builder = new StringBuilder();
            for (PropertyRepairDto propertyRepair : propertyRepairList) {
                builder.append(propertyRepair.toString());
                builder.append("\n");
            }
            return Response
                    .ok(builder.toString())
                    .build();

    }

    @POST
    @Path("searchPropertyOwner/{vat}")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchPropertyOwner(@PathParam("vat") String vat) {
            UserDto user = adminService.searchOwnerByVat(vat);
            return Response
                    .ok(user)
                    .build();
    }

    @POST
    @Path("searchProperty/{e9}")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchProperty(@PathParam("e9") String e9) {
            PropertyDto property = adminService.searchPropertyByPropertyIdentificationNumber(e9);
            return Response
                    .ok(property)
                    .build();
    }

    @PUT
    @Path("proposeOffer/{repairId}")
    //@RolesAllowed({"ADMIN"})
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response proposeOffer(PropertyRepairDto offer, @PathParam("repairId") int repairId) {
            PropertyRepairDto propertyRepair = createPropertyRepair(offer, repairId);
            if (propertyRepair == null) {
                return Response
                        .ok("The inserted dates are in wrong order or previous dates")
                        .build();
            } else {
                adminService.proposedRepair(propertyRepair, repairId);
                return Response
                        .ok("Offer is proposed and submitted to the id:" + propertyRepair.getId())
                        .build();
            }
    }

    public PropertyRepairDto createPropertyRepair(PropertyRepairDto propertyRepair, int repairId) {
       if (propertyRepair.getProposedStartDate().isAfter(propertyRepair.getProposedEndDate())) {
            return null;
        } else if (propertyRepair.getProposedStartDate().isBefore(Instant.now())) {
           return null;
       } else if (propertyRepair.getProposedEndDate().isBefore(Instant.now())) {
           return null;
       } else {
            PropertyRepairDto newPropertyRepair = new PropertyRepairDto(propertyRepairRepo.read(repairId).get());
            newPropertyRepair.setCost(propertyRepair.getCost());
            newPropertyRepair.setProposedStartDate(propertyRepair.getProposedStartDate());
            newPropertyRepair.setProposedEndDate(propertyRepair.getProposedEndDate());
            return newPropertyRepair;
        }

    }

    @PUT
    @Path("checkStart/{repairId}")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkStart(@PathParam("repairId") int repairId) {
            boolean checked = adminService.checkStart(repairId);
            if (checked) {
                return Response
                        .ok("The repair is checked as started")
                        .build();
            } else {
                return Response
                        .ok("The repair is not yet started ")
                        .build();
            }
    }

    @PUT
    @Path("checkEnd/{repairId}")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkEnd(@PathParam("repairId") int repairId) {
            boolean checked = adminService.checkEnd(repairId);
            if (checked) {
                return Response
                        .ok("The repair is checked as ended")
                        .build();
            } else {
                return Response
                        .ok("The repair is not yet ended")
                        .build();
            }
    }

}
