package org.com.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

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
    //@RestForm FileUpload file, @RestForm String fileName
//        System.out.println("fileName: " + fileName);
//        System.out.println("fileSize: " + file.size());

//    FileUploadForm form
//        System.out.println("fileName: " + form.fileName());
//        System.out.println("fileSize: " + form.file().size());
    public Response uploadFile(@RestForm FileUpload file, @RestForm String fileName) {
        logger.info("uploadFile");
        System.out.println("fileName: " + fileName);
        System.out.println("fileSize: " + file.size());

//        byte[] bytes = file.bytes();
//
//        // Save the file to a desired location
//        Files.write(Paths.get("/path/to/save/" + fileName), bytes);
        
        return Response.ok("File uploaded successfully!").build();
//        try (InputStream inputStream = form.file()) {
//            Files.createDirectories(Paths.get(UPLOAD_DIR));
//            File file = new File(UPLOAD_DIR, form.fileName());
//            try (FileOutputStream outputStream = new FileOutputStream(file)) {
//                inputStream.transferTo(outputStream);
//            }
//            return Response.ok("File uploaded successfully!").build();
//        } catch (IOException e) {
//            return Response.serverError().entity("Failed to upload file").build();
//        }
    }
}