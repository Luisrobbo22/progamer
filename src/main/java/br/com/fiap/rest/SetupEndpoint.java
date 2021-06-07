package br.com.fiap.rest;

import br.com.fiap.dao.SetupDao;
import br.com.fiap.model.Setup;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/setups")
public class SetupEndpoint {

    private SetupDao dao = new SetupDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Setup> index() {
        return dao.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Setup setup) {
        if (setup == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            dao.save(setup);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).entity(setup).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id") Long id) {
        Setup setup = null;

        try {
            setup = dao.findById(id);
            if (setup == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(setup).build();

    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") Long id, Setup setup) {
        try {
            if (dao.findById(id) == null || setup == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        setup.setId(id);
        dao.update(setup);

        return Response.status(Response.Status.OK).build();
    }
}
