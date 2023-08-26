package com.user.service.service;

import com.user.service.config.Config;
import com.user.service.config.Constants;
import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exception.ResourceNotFoundException;
import com.user.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private RestTemplate restTemplate  ;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users =  userRepository.findAll();

        users.forEach(user -> {
            List<Rating> ratings = restTemplate.getForObject(
                    String.format("%s%s", Constants.RATING_SERVICE_HOST, getUserById(user.getUserId())), ArrayList.class
            );

            user.setRatings(ratings);
        });

        return users;

    }

    @Override
    public User getUserById(String userId) {

        //Fetches user without rating
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found !!"));

        //fetch rating by user => localhost:8083/ratings/users/6bdb44fe-3519-4981-a54e-6f8519c0b56f
        List<Rating> ratings = restTemplate.exchange(
                String.format("%s%s", Constants.RATING_SERVICE_HOST, user.getUserId()),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Rating>>() {
                }).getBody();

        ratings.forEach(rating ->{
            rating.setHotel(restTemplate.exchange(
                    String.format("%s%s", Constants.HOTEL_SERVICE_HOST, rating.getHotelId()),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Hotel>() {
                    }
            ).getBody());
        });

        log.info(ratings.toString());

        user.setRatings(ratings);

        return user;

    }


}
