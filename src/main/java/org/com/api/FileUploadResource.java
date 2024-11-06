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

@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
public class FileUploadResource {
    
    @Inject
    Logger logger;
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(FileUploadForm form) {
        logger.info("uploadFile");
        System.out.println("fileName: " + form.fileName);
        System.out.println("fileSize: " + form.file.size());
        return Response.ok().build();
    }
}