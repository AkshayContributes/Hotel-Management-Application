package com.rating.services;

import com.rating.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    //create

    Rating createRating(Rating rating);

    //getAll
    List<Rating> getAllRatings();

    //getAllByUserId
    List<Rating> getRatingsByUser(String userId);

    //getAllByHotelId
    List<Rating> getRatingsByHotelId(String hotelId);



}
