package ru.javalab.hateoas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.javalab.hateoas.controllers.ShoperController;
import ru.javalab.hateoas.models.Shoper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ShoperRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Shoper>> {

    @Autowired
    private RepositoryEntityLinks links;

    @Override
    public EntityModel<Shoper> process(EntityModel<Shoper> model) {
        Shoper shoper = model.getContent();
        if (shoper != null && shoper.getStatus().equals("Active")) {
            model.add(linkTo(methodOn(ShoperController.class)
                    .ban(shoper.getId())).withRel("Ban"));
        }

        if (shoper != null && shoper.getStatus().equals("Deleted")) {
            model.add(links.linkToItemResource(Shoper.class, shoper.getId()).
                    withRel("delete"));
        }
        return model;
    }
}
