package com.gestorempleados.gestorempleados.service;

import com.gestorempleados.gestorempleados.controller.EmployeeController;
import com.gestorempleados.gestorempleados.model.Employee;
import com.gestorempleados.gestorempleados.my_exceptions.FileStorageException;
import com.gestorempleados.gestorempleados.my_exceptions.ResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
public class FileStorageService {
    private Path fileStorageLocation;

    @Autowired
    public FileStorageService(Employee employee) {
        String uploadDir = "./src/main/pictures/" + employee.getId() + "/";
        if(employee.getUploadDir() != null) {
            uploadDir = uploadDir + employee.getUploadDir();
        }

        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (FileAlreadyExistsException ex) {

        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {//--> revision del nombre del archivo para que no tenga caracteres invalidos
                throw new FileStorageException("Sorry! Filename contains invalid characters" + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new ResponseNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new ResponseNotFoundException("File not found " + fileName, ex);
        }
    }

    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }

}
