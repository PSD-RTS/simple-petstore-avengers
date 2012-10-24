package org.testinfected.petstore.controllers;

import com.pyxis.petstore.domain.product.Item;
import com.pyxis.petstore.domain.product.ItemInventory;
import org.testinfected.petstore.Controller;

import java.util.List;

import static org.testinfected.petstore.util.Context.context;

public class ListItems implements Controller {

    private final ItemInventory itemInventory;

    public ListItems(ItemInventory itemInventory) {
        this.itemInventory = itemInventory;
    }

    public void process(Request request, Response response) throws Exception {
        String productNumber = request.getParameter("product");
        List<Item> items = itemInventory.findByProductNumber (productNumber);
        response.render("items", context().with("items", items).asMap());
    }
}
