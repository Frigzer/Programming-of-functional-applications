package org.example.lab_09.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class CarShowroomService {

    @PersistenceContext
    private EntityManager entityManager;

    public CarShowroom findShowroomByName(String name) {
        return entityManager.createQuery("SELECT c FROM CarShowroom c WHERE c.showroomName = :name", CarShowroom.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void addCenter(String name, int maxCapacity) {
        CarShowroom newShowroom = new CarShowroom(name, maxCapacity);
        entityManager.persist(newShowroom);
    }

    public void removeCenter(String name) {
        CarShowroom showroom = findShowroomByName(name);
        if (showroom != null) {
            entityManager.remove(showroom);
        }
    }

    public List<CarShowroom> getShowrooms() {
        return entityManager.createQuery("SELECT c FROM CarShowroom c", CarShowroom.class).getResultList();
    }
}
