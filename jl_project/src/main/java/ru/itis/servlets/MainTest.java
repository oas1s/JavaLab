//package ru.itis.servlets;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.stereotype.Component;
//import ru.itis.servlets.config.ApplicationContextConfig;
//import ru.itis.servlets.models.Post;
//import ru.itis.servlets.repositories.PostRepositoryImpl;
//
//@Component
//public class MainTest {
//    public static void main(String[] args) {
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
//        PostRepositoryImpl postRepository = applicationContext.getBean(PostRepositoryImpl.class);
//        postRepository.save(Post.builder().name("asddasads").text("form java").build());
//    }
//}
