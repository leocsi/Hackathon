package com.example.DialBank.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ExceptionJSON {
    public String error;

    public ExceptionJSON(String error_message){
        this.error = error_message;
    }
    public String json(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try{
            return ow.writeValueAsString(this);
        } catch(JsonProcessingException exc){
            return "{error: " + exc.getMessage() +"}";
        }
    }
}
