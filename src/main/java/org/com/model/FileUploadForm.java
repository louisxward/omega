package org.com.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.io.InputStream;

public record FileUploadForm(
    @NotNull
    @RestForm
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    InputStream file,
    @NotBlank
    @RestForm
    String fileName
) {}