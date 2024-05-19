package com.alura.literalura.repository;

import com.alura.literalura.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    List<AuthorEntity> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(int year, int year2);
}
