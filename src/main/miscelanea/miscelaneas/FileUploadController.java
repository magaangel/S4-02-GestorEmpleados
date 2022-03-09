package com.gestorempleados.gestorempleados.miscelaneas;

import com.gestorempleados.gestorempleados.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller//--> se usa controller porque se trabaja con vistas
public class FileUploadController {

    @GetMapping("/")
    public String getFile(Model model){
        model.addAttribute("employess", new Employee());
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam(name = "file")MultipartFile file, RedirectAttributes attributes){
        if(file.isEmpty()){
            attributes.addFlashAttribute("msg", "Please, select a file");
            return "redirect:/status";
        }
        String rutaAlmacenamientoFile = "./src/main/java/pictures";
        try{
            byte[] fileBytes = file.getBytes();
            Path rutaAbsoluta = Paths.get(rutaAlmacenamientoFile + "/" + file.getOriginalFilename());
            Files.write(rutaAbsoluta, fileBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        attributes.addFlashAttribute("msg", "file upload successfully");
        return "redirect:/status";
    }

    @GetMapping("/status")
    public String status(){
        return "status";
    }
}
