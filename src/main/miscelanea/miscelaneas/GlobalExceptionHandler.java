package com.gestorempleados.gestorempleados.miscelaneas;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public String handlerMulplipar(MultipartException e, RedirectAttributes attributes){
        attributes.addFlashAttribute("msg", e.getCause().getMessage());
        return "redirect:status";
    }
}
