package org.com.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.model.FileUploadForm;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
public class FileUploadResource {
    private static final String UPLOAD_DIR = "/uploads/";
    
    @Inject
    Logger logger;
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    //@NotNull @Valid
    public Response uploadFile(FileUploadForm form) {
        logger.info("uploadFile");
        try (InputStream inputStream = form.file()) {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            File file = new File(UPLOAD_DIR, form.fileName());
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                inputStream.transferTo(outputStream);
            }
            return Response.ok("File uploaded successfully!").build();
        } catch (IOException e) {
            return Response.serverError().entity("Failed to upload file").build();
        }
    }
}