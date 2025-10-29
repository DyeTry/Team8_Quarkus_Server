package org.team8.tickets;

import jakarta.validation.constraints.*;

public class OnboardingTicketRequest {
    @NotBlank public String fullName;
    @NotBlank public String employeeId;
    @Email @NotBlank public String email;
    @NotBlank public String phone;
    @NotBlank public String address;
    @NotBlank public String equipment;
    @NotBlank public String assetName;   // <-- make it required
    @Size(max = 2000) public String issueDescription;
}
