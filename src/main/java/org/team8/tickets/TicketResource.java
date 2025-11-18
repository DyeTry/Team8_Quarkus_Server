package org.team8.tickets;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Path("/api/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {

    @Inject
    TicketService ticketService;

    @Inject
    JsonWebToken jwt;

    @GET
    @RolesAllowed({ "ADMIN", "MANAGER", "TECH" })
    public List<Ticket> listAll() {
        return ticketService.getAllTickets();
    }

    // -------------------------------------------------------------------------
    // GET SINGLE TICKET (optional but handy)
    // -------------------------------------------------------------------------
    @GET
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "MANAGER", "TECH"  })
    public Ticket getOne(@PathParam("id") Long id) {
        Ticket t = Ticket.findById(id);
        if (t == null) {
            throw new NotFoundException("Ticket " + id + " not found");
        }
        return t;
    }

    // -------------------------------------------------------------------------
    // EXISTING: single onboarding ticket
    // -------------------------------------------------------------------------
    @POST
    @Path("/onboarding")
    @RolesAllowed({ "ADMIN", "MANAGER", "TECH"  })
    public Response createOnboarding(@Valid OnboardingTicketRequest req,
                                     @Context UriInfo uriInfo) {

        Ticket created = ticketService.createOnboardingTicket(req);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(created.id.toString());
        return Response.created(builder.build()).entity(created).build();
    }

    // -------------------------------------------------------------------------
    // NEW: batch onboarding tickets (multiple equipments)
    // -------------------------------------------------------------------------
    @POST
    @Path("/onboarding/batch")
    @RolesAllowed({ "ADMIN", "MANAGER", "TECH"  })
    public Response createOnboardingBatch(@Valid OnboardingTicketBatchRequest req) {
        List<Ticket> created = ticketService.createOnboardingTickets(req);
        // Return "201 Created" with the list of created tickets
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // -------------------------------------------------------------------------
    // GENERIC CREATE (if you still use it elsewhere)
    // -------------------------------------------------------------------------
    @POST
    @Transactional
    @RolesAllowed({ "ADMIN", "MANAGER", "TECH"  })
    public Response create(Ticket incoming, @Context UriInfo uriInfo) {
        // server-managed fields
        incoming.id = null;
        if (incoming.ticketCode == null) {
            incoming.ticketCode = "ONB-" + UUID.randomUUID()
                    .toString().substring(0, 8).toUpperCase();
        }
        if (incoming.status == null) {
            incoming.status = "SUBMITTED";
        }
        if (incoming.dateSubmitted == null) {
            incoming.dateSubmitted = OffsetDateTime.now();
        }

        incoming.persist();
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(incoming.id.toString());
        return Response.created(builder.build()).entity(incoming).build();
    }
}
