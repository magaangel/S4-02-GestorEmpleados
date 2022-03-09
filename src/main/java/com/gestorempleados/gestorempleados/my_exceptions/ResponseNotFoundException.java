package com.gestorempleados.gestorempleados.my_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)//--> permite retornar el codigo de estado correcto y que el programa a pesar de la exception siga corriendo
public class ResponseNotFoundException extends RuntimeException {

    public ResponseNotFoundException(String msg){
        super(msg);
    }

    public ResponseNotFoundException(String msg, Throwable ex){
        super(msg, ex);
    }
}
