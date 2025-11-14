package org.team8.tickets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TicketService {

    /**
     * Existing single-onboarding ticket creation (used by /api/tickets/onboarding).
     */
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

    /**
     * NEW: Create one onboarding ticket per equipment item.
     */
    @Transactional
    public List<Ticket> createOnboardingTickets(OnboardingTicketBatchRequest req) {
        List<Ticket> created = new ArrayList<>();

        for (String equipment : req.equipments) {
            Ticket t = new Ticket();
            t.ticketCode = "ONB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            // If assetName wasn't provided, use the equipment name as the asset
            String assetNameToUse =
                    (req.assetName != null && !req.assetName.isBlank())
                            ? req.assetName
                            : equipment;

            t.assetName = assetNameToUse;
            t.status = "SUBMITTED";
            t.dateSubmitted = OffsetDateTime.now();
            t.fullName = req.fullName;
            t.employeeId = req.employeeId;
            t.email = req.email;
            t.phone = req.phone;
            t.address = req.address;
            t.equipment = equipment;
            t.issueDescription = req.issueDescription;

            t.persist();
            created.add(t);
        }

        return created;
    }


    @Transactional
    public List<Ticket> getAllTickets() {
        return Ticket.listAll();  // Panache ORM method
    }
}
