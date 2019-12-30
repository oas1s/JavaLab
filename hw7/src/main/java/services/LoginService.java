package services;

import context.Component;
import dto.DtoString;
import dto.DtoUser;
import models.User;
import protocol.Request;
import protocol.Response;
import repositories.UserRepositoryImpl;
import utills.TokenizeUser;

public class LoginService implements Component {
    public LoginService() {
    }

    @Override
    public String getName() {
        return "LoginService";
    }
    public Response login(Request request) {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        Request<DtoUser> loginRequest = request;
        TokenizeUser tokenizeUser = new TokenizeUser();
        User user = new User();
        user.setEmail(loginRequest.getPayload().getEmail());
        user.setPassword(loginRequest.getPayload().getPassword());
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

        Response response = new Response();
        response.setResponse(new DtoString(token));
        return response;
    }
}
