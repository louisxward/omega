package org.com.model;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class FileUploadForm {
    @RestForm
    public FileUpload file;
    @RestForm
    public String fileName;
}