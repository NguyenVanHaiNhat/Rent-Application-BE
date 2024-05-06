package org.example.rentapplicationbe.services.image;

import org.example.rentapplicationbe.model.Entity.Image;

import java.util.List;
import java.util.Optional;

public interface IImageService {
    List<Image> getAllImage();
    Optional<Image> getImageById(Long id);
    Image save(Image image, Long id_house);
    void delete(Long id);
}
