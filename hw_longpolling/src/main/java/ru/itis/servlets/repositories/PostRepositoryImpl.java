package ru.itis.servlets.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.servlets.models.Post;
import ru.itis.servlets.models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Component
public class PostRepositoryImpl {

    @Autowired
    private EntityManager entityManager;

    public void save(Post post){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(post);
        entityTransaction.commit();
    }

    public Post find(int id){
        Post post  = entityManager.find(Post.class, id);
        entityManager.detach(post);
        return post;
    }

    public List<Post> findByUser(User user){
        List<Post> posts = entityManager.createQuery("select post from Post post where post.user_id = ?1")
                .setParameter(1,user)
                .getResultList();
        return posts;
    }
}
