package ru.javalab.hateoas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javalab.hateoas.models.Shoper;

@Service
public class ShopersServiceImpl implements ShopersService {

    @Autowired
    private CoursesRepository coursesRepository;

    @Override
    public Shoper ban(Long shoperId) {
        Shoper shoper = coursesRepository.findById(shoperId).orElseThrow(IllegalArgumentException::new);
        shoper.ban();
        coursesRepository.save(shoper);
        return shoper;
    }
}