package com.liamhugo.wardrobe.users.resource;


import com.liamhugo.wardrobe.clothes.bdd.Clothe;
import com.liamhugo.wardrobe.clothes.bdd.ClotheRepository;
import com.liamhugo.wardrobe.users.bdd.User;
import com.liamhugo.wardrobe.users.bdd.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("users")
public class UserResource {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClotheRepository clotheRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listUser() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User u) {
        return userRepository.save(u);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id) {
        if (userRepository.findById(id).isPresent()) {
            try {
                userRepository.deleteById(id);
            } catch (Exception e) {
                return Response.serverError().build();
            }
        }
        return Response.noContent().build();
    }

    @POST
    @Path("{idUser}/clothes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addClotheToUser(@PathParam("idUser") Long idUser, ClotheInput clothes) {
        Optional<User> uOpt = userRepository.findById(idUser);
        Optional<Clothe> cOpt = clotheRepository.findById(clothes.getIdClothe());

        if (!uOpt.isPresent() || !cOpt.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        User u = uOpt.get();
        Clothe c = cOpt.get();
        u.getClothes().add(c);
        userRepository.save(u);
        return Response.ok(u).build();
    }
}
