package org.testinfected.petstore.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testinfected.petstore.procurement.ProcurementRequestHandler;
import org.testinfected.petstore.product.DuplicateProductException;

import com.vtence.molecule.Application;
import com.vtence.molecule.http.HttpStatus;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;

public class CreateProduct implements Application {

    private final ProcurementRequestHandler requestHandler;
    private static final Pattern NUMBER = Pattern.compile("^[A-Z]{3}-[0-9]{4}$", Pattern.DOTALL);
    
    public CreateProduct(ProcurementRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void handle(Request request, Response response) throws Exception {
        
        String number = request.parameter("number");
        if( number == null )  {
            response.status(HttpStatus.NOT_ACCEPTABLE);
            return;
        }
        Matcher matcher = NUMBER.matcher(number);
        if( ! matcher.find())  {
            response.status(HttpStatus.NOT_ACCEPTABLE);
            return;
        }
        
        try {
            requestHandler.addProductToCatalog(
                    request.parameter("number"),
                    request.parameter("name"),
                    request.parameter("description"),
                    request.parameter("photo"));
            response.status(HttpStatus.CREATED);
        } catch (DuplicateProductException e) {
            response.status(HttpStatus.CONFLICT);
        }
    }
}