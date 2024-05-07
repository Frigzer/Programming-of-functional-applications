package org.example.lab_08;

import jakarta.persistence.criteria.*;
import org.example.lab_08.core.Rating;
import org.example.lab_08.core.Vehicle;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

import static org.example.lab_08.HibernateUtil.sessionFactory;

public class VehicleDAO {
    private Session session;

    public VehicleDAO(Session session) {
        this.session = session;
    }

    public List<Object[]> countVehiclesByShowroom() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Vehicle> root = cq.from(Vehicle.class);

        cq.multiselect(root.get("showroom").get("showroomName"), cb.sum(root.get("quantity")));
        cq.groupBy(root.get("showroom").get("showroomName"));

        Query<Object[]> query = session.createQuery(cq);
        return query.getResultList();
    }


    public List<Object[]> getVehicleRatings() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = cb.createQuery(Object[].class);
        Root<Vehicle> root = criteria.from(Vehicle.class);
        Join<Vehicle, Rating> ratingsJoin = root.join("ratings", JoinType.LEFT);

        criteria.multiselect(
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
                cb.count(ratingsJoin),
                cb.avg(ratingsJoin.get("score"))

        ).groupBy(
                root.get("brand"),
                root.get("model"),
                root.get("condition"),
                root.get("price"),
                root.get("mileage"),
                root.get("engineCapacity"),
                root.get("yearOfProduction"),
                root.get("quantity"),
                root.get("showroom").get("showroomName"),
                root.get("id")
        );


        return session.createQuery(criteria).getResultList();
    }

    public List<Object[]> getVehicleCountsByName() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteria = cb.createQuery(Object[].class);
            Root<Vehicle> root = criteria.from(Vehicle.class);

            criteria.multiselect(root.get("brand"), root.get("model"), cb.count(root));
            criteria.groupBy(root.get("brand"), root.get("model"));
            Query<Object[]> query = session.createQuery(criteria);
            return query.getResultList();
        }
    }
}
