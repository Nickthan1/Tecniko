/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import nikos.tecniko.dto.UserDto;
import nikos.tecniko.services.UserService;

/**
 *
 * @author legeo
 */
@Path("/")
public class LoginResource {

    @Inject
    private UserService userService;

    

    @POST
    @Path("register")
    @PermitAll
    @Produces("application/json")
    @Consumes("application/json")
    public Response register(UserDto user) {
        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        System.out.println("Hello im here");
        UserDto registeredUser = userService.createNewAccount(user);
        if (registeredUser != null) {
            return Response
                    .ok("You are now Registered!" + registeredUser.getName())
                    .build();
        } else {
            return Response
                    .ok("Wrong Inputed Data")
                    .build();
        }
    }
}
