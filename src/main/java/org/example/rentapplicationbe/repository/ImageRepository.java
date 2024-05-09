package org.example.rentapplicationbe.repository;

import org.example.rentapplicationbe.model.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
