package services;

import context.Component;
import dto.DtoString;
import dto.DtoUser;
import models.User;
import repositories.UserRepositoryImpl;
import utills.TokenizeUser;

public class LoginService implements Component {
    public LoginService() {
    }

    @Override
    public String getName() {
        return "LoginService";
    }
    public String login(User user) {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        TokenizeUser tokenizeUser = new TokenizeUser();
        System.out.println(user.getEmail());
        String token;
        if (userRepository.findByLogin(user.getEmail()).isPresent()) {
            User user1 = userRepository.findByLogin(user.getEmail()).get();
            token = tokenizeUser.createJWT(user1);
        } else {
            userRepository.save(user);
            User user1 = userRepository.findByLogin(user.getEmail()).get();
            token = tokenizeUser.createJWT(user1);
        }
        return token;
    }
}
