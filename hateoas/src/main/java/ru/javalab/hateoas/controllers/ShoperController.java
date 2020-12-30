package ru.javalab.hateoas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.javalab.hateoas.services.ShopersService;

@RepositoryRestController
public class ShoperController {

    @Autowired
    private ShopersService shopersService;

    @RequestMapping(value = "/shopers/{shoper-id}/ban", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> ban(@PathVariable("shoper-id") Long courseId) {
        return ResponseEntity.ok(
                EntityModel.of(shopersService.ban(courseId)));
    }
}
