package org.team8.tickets;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity @Table(name="Ticket", schema="dbo")
public class Ticket extends PanacheEntityBase {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="TicketId") public Long id;

    @Column(name="TicketCode") public String ticketCode;
    @Column(name="AssetName") public String assetName;
    @Column(name="Status") public String status;
    @Column(name="DateSubmitted") public OffsetDateTime dateSubmitted;
    @Column(name="FullName") public String fullName;
    @Column(name="EmployeeId") public String employeeId;
    @Column(name="Email") public String email;
    @Column(name="Phone") public String phone;
    @Column(name="Address") public String address;
    @Column(name="Equipment") public String equipment;
    @Column(name="IssueDescription") public String issueDescription;
}
