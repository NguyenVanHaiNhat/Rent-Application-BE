package org.example.rentapplicationbe.services.image;

import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.model.Entity.Image;
import org.example.rentapplicationbe.repository.IHouseRepository;
import org.example.rentapplicationbe.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ImageService implements IImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private IHouseRepository iHouseRepository;
    @Override
    public List<Image> getAllImage() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image save(Image image, Long houseId) {
        Optional<House> houseOptional = iHouseRepository.findById(houseId);
        House house = houseOptional.orElseThrow(() -> new IllegalArgumentException("Invalid house ID"));
        image.setHouse(house);
        return imageRepository.save(image);
    }

    @Override
    public void delete(Long id) {
        imageRepository.deleteById(id);
    }
}
