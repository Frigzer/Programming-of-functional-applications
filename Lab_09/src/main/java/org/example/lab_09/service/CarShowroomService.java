package org.example.lab_09.service;

import jakarta.transaction.Transactional;
import org.example.lab_09.core.CarShowroom;
import org.example.lab_09.repository.CarShowroomRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarShowroomService {
    private final CarShowroomRepository showroomRepository;

    public CarShowroomService(CarShowroomRepository showroomRepository) {
        this.showroomRepository = showroomRepository;
    }

    @Transactional
    public CarShowroom addShowroom(CarShowroom showroom) {
        return showroomRepository.save(showroom);
    }

    @Transactional
    public void deleteShowroom(Long id) {
        showroomRepository.deleteById(id);
    }

    public Boolean existsShowroom(Long id) {
        return showroomRepository.existsById(id);
    }

    public List<CarShowroom> findAllShowrooms() {
        return showroomRepository.findAll();
    }

    public CarShowroomRepository getShowroomRepository() {
        return this.showroomRepository;
    }

    public double findFulfillmentPercentageById(@Param("id") Long id) {
        return showroomRepository.findFulfillmentPercentageById(id);
    }
}
