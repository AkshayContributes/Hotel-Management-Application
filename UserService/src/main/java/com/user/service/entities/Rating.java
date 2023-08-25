package com.user.service.entities;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private Float rating;
    private String review;
    private String userId;
    private String hotelId;
}
