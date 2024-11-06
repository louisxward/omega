package org.com.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class FileUploadForm {
    @RestForm
    @NotNull
    public FileUpload file;
    @RestForm
    @NotBlank
    public String fileName;
}