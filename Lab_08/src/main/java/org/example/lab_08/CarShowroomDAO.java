package org.example.lab_08;

import jakarta.persistence.criteria.*;
import org.example.lab_08.core.CarShowroom;
import org.example.lab_08.core.ItemCondition;
import org.example.lab_08.core.Rating;
import org.example.lab_08.core.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CarShowroomDAO {
    private final SessionFactory sessionFactory;

    public CarShowroomDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addCarShowroom(CarShowroom showroom) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(showroom);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void removeCarShowroom(String name) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CarShowroom showroom = session.get(CarShowroom.class, name);
            if (showroom != null) {
                session.delete(showroom);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<CarShowroom> findAll() {
        Transaction transaction = null;
        List<CarShowroom> showrooms = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            // Utworzenie zapytania HQL do pobierania wszystkich salonów
            Query<CarShowroom> query = session.createQuery("from CarShowroom", CarShowroom.class);
            showrooms = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return showrooms;
    }

    public List<Object[]> findVehicles(CarShowroom showroom, ItemCondition condition, Double minPrice, Double maxPrice, String searchName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
            Root<Vehicle> root = query.from(Vehicle.class);
            Join<Vehicle, Rating> ratingJoin = root.join("ratings", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();
            if (showroom != null && !"All".equals(showroom.getShowroomName())) {
                predicates.add(cb.equal(root.get("showroom"), showroom));
            }
            if (condition != null) {
                predicates.add(cb.equal(root.get("condition"), condition));
            }
            if (minPrice != null) {
                predicates.add(cb.ge(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.le(root.get("price"), maxPrice));
            }
            if (searchName != null && !searchName.isEmpty()) {
                Predicate brandPredicate = cb.like(cb.lower(root.get("brand")), "%" + searchName.toLowerCase() + "%");
                Predicate modelPredicate = cb.like(cb.lower(root.get("model")), "%" + searchName.toLowerCase() + "%");
                predicates.add(cb.or(brandPredicate, modelPredicate));
            }

            // Dodanie do zapytania pól do wyświetlenia oraz agregacji
            query.multiselect(
                            root.get("id"),
                            root.get("brand"),
                            root.get("model"),
                            root.get("condition"),
                            root.get("price"),
                            root.get("yearOfProduction"),
                            root.get("mileage"),
                            root.get("engineCapacity"),
                            root.get("quantity"),
                            root.get("showroom").get("showroomName"),
                            cb.count(ratingJoin),
                            cb.avg(ratingJoin.get("score"))
                    ).where(cb.and(predicates.toArray(new Predicate[0])))
                    .groupBy(
                            root.get("brand"),
                            root.get("model"),
                            root.get("condition"),
                            root.get("price"),
                            root.get("yearOfProduction"),
                            root.get("mileage"),
                            root.get("engineCapacity"),
                            root.get("quantity"),
                            root.get("showroom").get("showroomName"),
                            root.get("id")
                    );

            return session.createQuery(query).getResultList();
        }
    }


}
