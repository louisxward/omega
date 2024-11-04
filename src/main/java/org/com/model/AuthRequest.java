package org.com.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
    @NotBlank @Size(min = 3, max = 33) String username,
    @NotBlank @Size(min = 5, max = 99) String password
) {}