package com.dstod.restaurantmanagerapi.management.repositories;

import com.dstod.restaurantmanagerapi.common.config.OpenAPIConfig;
import com.dstod.restaurantmanagerapi.management.models.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findBySectionName(String sectionName);
    @Query("select s from Section s where s.sectionName = :sectionName and s.id = :id")
    Optional<Section> findBySectionNameExcludingId(String sectionName, Long id);
}
