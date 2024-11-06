package org.com.model;

import org.jboss.resteasy.reactive.multipart.FileUpload;

public record FileUploadForm(
    //@NotNull
//    @RestForm
    //@PartType(MediaType.APPLICATION_OCTET_STREAM)
    //@MultipartForm
    FileUpload file,
    //@NotBlank
//    @RestForm
    String fileName
) {}