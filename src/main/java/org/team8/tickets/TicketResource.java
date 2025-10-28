package org.team8.tickets;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.annotation.security.RolesAllowed;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {

    @Inject JsonWebToken jwt;  // Access token info (username, roles)

    @Inject
    TicketService ticketService;   // the service layer that actually saves tickets

    // 👇 This is where your method goes
    @POST
    @Path("/onboarding")
    public Response createOnboarding(@Valid OnboardingTicketRequest req, @Context UriInfo uri) {
        Ticket created = ticketService.createOnboardingTicket(req);
        return Response
                .created(uri.getAbsolutePathBuilder().path(created.id.toString()).build())
                .entity(created)
                .build();
    }

    @GET
    @Path("/secured")
    @RolesAllowed({"db_admin","db_manager","it_tech"})
    public List<Ticket> listAll() {
        String currentUser = jwt.getName(); // get username from token
        return Ticket.listAll();
    }

    @GET
    @RolesAllowed({"db_admin","db_manager","it_tech"})
    @Path("/public")
    public List<Ticket> list() {
        return Ticket.listAll();
    }

    // GET all tickets
    @GET
    @RolesAllowed({"db_admin","db_manager","it_tech"})
    public List<Ticket> listAllTickets() {
        return ticketService.getAllTickets();
    }

    @POST
    @Transactional
    public Response create(Ticket incoming, @Context UriInfo uri) {
        // set server-managed fields
        incoming.id = null;
        if (incoming.ticketCode == null)
            incoming.ticketCode = "ONB-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        if (incoming.status == null) incoming.status = "SUBMITTED";
        if (incoming.dateSubmitted == null) incoming.dateSubmitted = OffsetDateTime.now();

        incoming.persist();
        return Response.created(uri.getAbsolutePathBuilder().path(incoming.id.toString()).build())
                .entity(incoming).build();
    }
}
