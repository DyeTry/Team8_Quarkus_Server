package org.team8.tickets;

import jakarta.validation.constraints.*;
import java.util.List;

public class OnboardingTicketBatchRequest {

    @NotBlank
    public String fullName;

    @NotBlank
    public String employeeId;

    @Email
    @NotBlank
    public String email;

    // make these optional if you want
    public String phone;
    public String address;

    @NotEmpty
    public List<@NotBlank String> equipments;

    // assetName is OPTIONAL now – no @NotBlank
    public String assetName;

    @Size(max = 2000)
    public String issueDescription;
}
