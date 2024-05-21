package org.example.lab_09.service;

import jakarta.transaction.Transactional;
import org.example.lab_09.core.Rating;
import org.example.lab_09.repository.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public Double calculateAverageRating(Long vehicleId) {
        return ratingRepository.findAverageRatingByVehicleId(vehicleId);
    }

    public RatingRepository getRatingRepository() {
        return ratingRepository;
    }

    public Rating addRating(Rating rating) {
        ratingRepository.save(rating);
        return rating;
    }
}
