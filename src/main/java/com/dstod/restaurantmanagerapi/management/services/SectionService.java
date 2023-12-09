package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.management.repositories.SectionRepository;
import org.springframework.stereotype.Service;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }
}
