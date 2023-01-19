/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import nikos.tecniko.dto.PropertyDto;
import nikos.tecniko.dto.PropertyRepairDto;
import nikos.tecniko.dto.UserDto;
import nikos.tecniko.enums.RepairType;
import nikos.tecniko.enums.StatusType;
import nikos.tecniko.exceptions.PropertyException;
import nikos.tecniko.exceptions.PropertyOwnerException;
import nikos.tecniko.exceptions.PropertyRepairException;
import nikos.tecniko.repositories.PropertyOwnerRepository;
import nikos.tecniko.repositories.PropertyRepairRepository;
import nikos.tecniko.repositories.PropertyRepository;
import nikos.tecniko.services.RegisterService;
import nikos.tecniko.services.UserService;

/**
 *
 * @author legeo
 */
@Path("customer")
public class UserResource {

    @Inject
    private UserService userService;
    @Inject
    private RegisterService registerService;
    @Inject
    private PropertyOwnerRepository propertyOwnerRepo;
    @Inject
    private PropertyRepository propertyRepo;
    @Inject
    private PropertyRepairRepository propertyRepairRepo;

    @GET
    @Path("getProperties/{userId}")
    @RolesAllowed({"PROPERTY_OWNER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllProperties(@PathParam("userId") int userId) {
        List<PropertyDto> propertyList = getProperties(userId);
        StringBuilder builder = new StringBuilder();
        for (PropertyDto property : propertyList) {
            builder.append(property.toString());
            builder.append("\n");
        }
        return Response
                .ok(builder.toString())
                .build();
    }

    private List<PropertyDto> getProperties(int id) {
        return propertyRepo.read().stream().filter(p -> p.getOwner().getId() == id)
                .map(p -> new PropertyDto(p))
                .collect(Collectors.toList());
    }

    @GET
    @Path("getPropertyRepairs/{userId}")
    @RolesAllowed({"PROPERTY_OWNER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllPropertyRepairs(@PathParam("userId") int userId) {
        List<PropertyRepairDto> propertyRepairList = userService.readAlldPropertyRepairs(userId);
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
    @Path("getPendingRepairOffers/{userId}")
    @RolesAllowed({"PROPERTY_OWNER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPendingRepairOffers(@PathParam("userId") int userId) {
        List<PropertyRepairDto> pendingList = userService.readPendingPropertyRepairs(userId);
        StringBuilder builder = new StringBuilder();
        for (PropertyRepairDto pending : pendingList) {
            builder.append(pending.toString());
            builder.append("\n");
        }
        return Response
                .ok(builder.toString())
                .build();
    }

    @PUT
    @Path("answerPendingRepairOffer/{propertyRepairId}/{answer}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response answerPendingRepairOffers(@PathParam("propertyRepairId") int propertyRepairId, @PathParam("answer") String answer) {
        PropertyRepairDto propertyRepair = new PropertyRepairDto(propertyRepairRepo.read()
                .stream()
                .filter(pr -> pr.getId() == propertyRepairId)
                .findAny()
                .orElse(null));
        if (answer.equals("yes") || answer.equals("YES")) {
            propertyRepair.setStatus(StatusType.ACCEPTED);
            propertyRepair.setOwnerAcceptance(true);
        }
        propertyRepairRepo.update(propertyRepair.asPropertyRepair(), propertyRepair.getId());
        return Response
                .ok("Answer Posted")
                .build();
    }

    @POST
    @Path("searchProperty/{e9}")
    @RolesAllowed({"PROPERTY_OWNER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchProperty(@PathParam("e9") String e9) {
        PropertyDto property = new PropertyDto(propertyRepo.readByPropertyIdentificationNumber(e9).get());
        return Response
                .ok(property)
                .build();
    }
    @POST
    @Path("PropertyOwner")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserDto createUser(UserDto user) throws PropertyOwnerException {
        
        return registerService.registerPropertyOwner(user);
    }

    @POST
    @Path("searchPropertyRepair/{type}")
    @RolesAllowed({"PROPERTY_OWNER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchPropertyRepair(@PathParam("type") String type) {
        RepairType repairType = RepairType.valueOf(type);
        PropertyRepairDto propertyRepair = new PropertyRepairDto(propertyRepairRepo.read()
                .stream()
                .filter(pr -> pr.getRepairType() == repairType)
                .findAny()
                .orElse(null));
        return Response
                .ok(propertyRepair)
                .build();
    }

    @POST
    @Path("registerProperty/{userId}")
    @RolesAllowed({"PROPERTY_OWNER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerProperty(@PathParam("userId") int userId, PropertyDto property) {
        UserDto user = new UserDto(propertyOwnerRepo.read(userId).get());
        PropertyDto newProperty = createProperty(user, property);
        try {
            registerService.registerProperty(newProperty.asProperty());
        } catch (PropertyException ex) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response
                .ok("Ok property registered")
                .build();
    }

    public PropertyDto createProperty(UserDto user, PropertyDto property) {
        PropertyDto newProperty = new PropertyDto();
        newProperty.setOwner(user.asUser());
        newProperty.setPropertyIdentificationNumber(property.getPropertyIdentificationNumber());
        newProperty.setAddress(property.getAddress());
        newProperty.setYearOfConstruction(property.getYearOfConstruction());
        newProperty.setPropertyType(property.getPropertyType());
        return newProperty;
    }

    @POST
    @Path("registerPropertyRepair/{userId}/{propertyId}")
    @RolesAllowed({"PROPERTY_OWNER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerPropertyRepair(@PathParam("userId") int userId, @PathParam("propertyId") int propertyId, PropertyRepairDto propertyRepair) {
        UserDto user = new UserDto(propertyOwnerRepo.read(userId).get());
        if (ownerOfProperty(user, propertyId)) {
            try {
                PropertyRepairDto newPropertyRepair = buildRepair(propertyRepair, propertyId);
                registerService.registerPropertyRepair(newPropertyRepair.asPropertyRepair());
            } catch (PropertyRepairException ex) {
                Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            return Response
                    .ok("Ok propertyRepair registered")
                    .build();
        } else {
            return Response
                    .ok("You are not the owner of that propertyRepair")
                    .build();
        }
    }

    private PropertyRepairDto buildRepair(PropertyRepairDto propertyRepair, int propertyId) {
        PropertyDto property = new PropertyDto(propertyRepo.read(propertyId).get());
        PropertyRepairDto repair = new PropertyRepairDto();
        repair.setProperty(property.asProperty());
        repair.setRepairType(propertyRepair.getRepairType());
        repair.setDescription(propertyRepair.getDescription());
        //repair.setDateOfSubmission(Instant.now());
        repair.setWorkToBeDone(propertyRepair.getWorkToBeDone());
        repair.setStatus(StatusType.PENDING);
        return repair;
    }

    @PUT
    @Path("updateUser/{userId}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserProfile(@PathParam("userId") int userId,UserDto userInfo) {
        UserDto user = new UserDto(propertyOwnerRepo.read(userId).get());
        UserDto newUser = updateUser(userInfo, user);
        propertyOwnerRepo.update(newUser.asUser(), user.getId());
        return Response
                .ok("Ok profile updated")
                .build();
    }

    private UserDto updateUser(UserDto user, UserDto existingUser) {
        existingUser.setAddress(user.getAddress());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return existingUser;
    }

    @PUT
    @Path("updateProperty/{userId}/{propertyId}")
    @RolesAllowed({"PROPERTY_OWNER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProperty(@PathParam("userId") int userId,@PathParam("propertyId") int propertyId, PropertyDto property) {
        UserDto user = new UserDto(propertyOwnerRepo.read(userId).get());
        if (ownerOfProperty(user, propertyId)) {
            userService.updateProperty(property, propertyId);
            return Response
                    .ok("Ok property updated")
                    .build();
        } else {
            return Response
                    .ok("You are not the owner of that property")
                    .build();
        }
    }

    @PUT
    @Path("updatePropertyRepair/{userId}/{propertyRepairId}")
    @RolesAllowed({"PROPERTY_OWNER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePropertyRepair(@PathParam("userId") int userId,@PathParam("propertyRepairId") int propertyRepairId, PropertyRepairDto propertyRepair) {
        UserDto user = new UserDto(propertyOwnerRepo.read(userId).get());
        if (ownerOfPropertyRepair(user, propertyRepairId)) {
            PropertyRepairDto newPropertyRepair = patchPropertyRepair(propertyRepairId, propertyRepair);
            userService.updatePropertyRepair(newPropertyRepair, propertyRepairId);
            return Response
                    .ok("Ok propertyRepair updated")
                    .build();
        } else {
            return Response
                    .ok("You are not the owner of that propertyRepair")
                    .build();
        }
    }

    public PropertyRepairDto patchPropertyRepair(int propertyRepairId, PropertyRepairDto propertyRepair) {
        PropertyRepairDto newPropertyRepair = new PropertyRepairDto(propertyRepairRepo.read(propertyRepairId).get());
        newPropertyRepair.setRepairType(propertyRepair.getRepairType());
        newPropertyRepair.setDescription(propertyRepair.getDescription());
        newPropertyRepair.setWorkToBeDone(propertyRepair.getWorkToBeDone());
        //newPropertyRepair.setDateOfSubmission(propertyRepair.getDateOfSubmission());
        return newPropertyRepair;
    }

    @DELETE
    @Path("deleteProperty/{userId}/{propertyId}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProperty(@PathParam("userId") int userId,@PathParam("propertyId") int propertyId) {
        UserDto user = new UserDto(propertyOwnerRepo.read(userId).get());
        if (ownerOfProperty(user, propertyId)) {
            userService.deleteProperty(propertyId);
            return Response
                    .ok("Ok property deleted")
                    .build();
        } else {
            return Response
                    .ok("You are not the owner of that property")
                    .build();
        }
    }

    @DELETE
    @Path("deletePropertyRepair/{userId}/{propertyRepairId}")
    @RolesAllowed({"PROPERTY_OWNER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePropertyRepair(@PathParam("userId") int userId,@PathParam("propertyRepairId") int propertyRepairId) {
        UserDto user = new UserDto(propertyOwnerRepo.read(userId).get());
        if (ownerOfPropertyRepair(user, propertyRepairId)) {
            userService.deletePropertyRepair(propertyRepairId);
            return Response
                    .ok("Ok deleted")
                    .build();
        } else {
            return Response
                    .ok("You are not the owner of that propertyRepair")
                    .build();
        }
    }

    @DELETE
    @Path("deleteAccount/{userId}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAccount(@PathParam("userId") int userId) {
        UserDto user = new UserDto(propertyOwnerRepo.read(userId).get());
        userService.deleteAccount(user.getId());
        return Response
                .ok("Ok deleted")
                .build();
    }

    private boolean ownerOfProperty(UserDto user, int propertyId) {
        PropertyDto property = propertyRepo.read()
                .stream()
                .filter(p -> p.getOwner().getId() == user.getId() && p.getId() == propertyId)
                .map(p -> new PropertyDto(p))
                .findAny()
                .orElse(null);
        return property != null;
    }

    private boolean ownerOfPropertyRepair(UserDto user, int propertyRepairId) {
        PropertyRepairDto propertyRepair = propertyRepairRepo.read()
                .stream()
                .filter(pr -> pr.getProperty().getOwner().getId() == user.getId() && pr.getId() == propertyRepairId)
                .map(pr -> new PropertyRepairDto(pr))
                .findAny()
                .orElse(null);
        return propertyRepair != null;
    }
}
