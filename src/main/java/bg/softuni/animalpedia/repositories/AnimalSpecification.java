package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.Animal;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnimalSpecification implements Specification<Animal> {
    private final String searchTerm;

    public AnimalSpecification(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Specification<Animal> where = specieNameLike(searchTerm).or(scientificNameLike("%" + searchTerm));

        return where.toPredicate(root, query, criteriaBuilder);
    }

    private static Specification<Animal> specieNameLike(String specieName) {
        return (root, query, criteriaBuilder) -> {
            String[] keywords = specieName.split("\\s+");
            List<Predicate> predicates = new ArrayList<>();

            for (String keyword : keywords) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("specieName"), keyword + "%"),
                        criteriaBuilder.like(root.get("specieName"), "% " + keyword),
                        criteriaBuilder.like(root.get("specieName"), "% " + keyword + "%")
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Specification<Animal> scientificNameLike(String scientificName) {
        return (root, query, criteriaBuilder) -> {
            String[] keywords = scientificName.split("\\s+");
            List<Predicate> predicates = new ArrayList<>();

            for (String keyword : keywords) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("scientificName"), keyword + "%"),
                        criteriaBuilder.like(root.get("scientificName"), "% " + keyword),
                        criteriaBuilder.like(root.get("scientificName"), "% " + keyword + "%")
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
