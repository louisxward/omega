package org.com.api;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.exception.ValidationException;
import org.com.model.FileUploadForm;
import org.jboss.logging.Logger;

@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
public class FileUploadResource {
    
    @Inject
    Logger logger;
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@NotNull @Valid FileUploadForm form) {
        logger.info("uploadFile");
        var fileSize = form.file.size();
        if (fileSize < 1L) {
            throw new ValidationException("file", "Please upload a file");
        }
        if (fileSize > 2 * 1024L * 1024L) {
            throw new ValidationException("file", "File is too bigg");
        }
        var file = form.file;
        System.out.println("Req fileName: " + form.fileName);
        System.out.println("fileName: " + file.fileName());
        System.out.println("fileSize: " + file.size());
        System.out.println("filePath: " + file.filePath());
        System.out.println("name: " + file.name());
        System.out.println("name: " + file.contentType());
        // more checks
        
        return Response.ok().build();
    }
}