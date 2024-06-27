package lim.seyeon.safe.stay.infrastructure.Specifications;

import lim.seyeon.safe.stay.domain.House.House;
import lim.seyeon.safe.stay.presentation.DTO.HouseFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

/*
public class HouseSpecifications {

    public static Specification<House> getHouseByFilter(HouseFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
            }
            if(filter.getNeighborhood() != null && !filter.getNeighborhood().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("neighborhood"), "%" + filter.getNeighborhood() + "%"));
            }
            if(filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }
            if(filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
*/