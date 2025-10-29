package org.team8.tickets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;

@ApplicationScoped
public class TicketService {
    @Transactional
    public Ticket createOnboardingTicket(OnboardingTicketRequest req) {
        Ticket t = new Ticket();
        t.ticketCode = "ONB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        t.assetName = req.assetName;
        t.status = "SUBMITTED";
        t.dateSubmitted = OffsetDateTime.now();

        t.fullName = req.fullName;
        t.employeeId = req.employeeId;
        t.email = req.email;
        t.phone = req.phone;
        t.address = req.address;
        t.equipment = req.equipment;
        t.issueDescription = req.issueDescription;

        t.persist();
        return t;
    }

    @Transactional
    public List<Ticket> getAllTickets() {
        return Ticket.listAll();  // Panache ORM method
    }
}
