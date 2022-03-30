package com.liamhugo.wardrobe.clothes.resource;


import com.liamhugo.wardrobe.clothes.bdd.Clothe;
import com.liamhugo.wardrobe.clothes.bdd.ClotheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("clothes")
public class ClotheResource {
    @Autowired
    private ClotheRepository clotheRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Clothe> listClothe() {
        List<Clothe> clothes = new ArrayList<>();
        clotheRepository.findAll().forEach(clothes::add);
        return clothes;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Clothe createClothe(Clothe c) {
        return clotheRepository.save(c);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClothe(@PathParam("id") Long id) {
        if (clotheRepository.findById(id).isPresent()) {
            try {
                clotheRepository.deleteById(id);
            } catch (Exception e) {
                return Response.serverError().build();
            }
        }
        return Response.noContent().build();
    }
}
