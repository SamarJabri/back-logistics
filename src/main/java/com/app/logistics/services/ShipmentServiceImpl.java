package com.app.logistics.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.logistics.DTO.ShipmentDTO;
import com.app.logistics.entities.Shipment;
import com.app.logistics.entities.User;
import com.app.logistics.repositories.ShipmentRepository;
import com.app.logistics.repositories.UserRepository;

@Service
public class ShipmentServiceImpl implements ShipmentService {
	
	@Value("${upload.directory}")
    private String uploadDir;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private UserRepository userRepository;

    private String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, filename);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return path.toString();
    }

    @Override
    public Shipment addShipment(ShipmentDTO dto, MultipartFile blFile, MultipartFile blFinalFile,
                                MultipartFile collisageFile, MultipartFile factureFile) throws IOException {

        Shipment shipment = new Shipment();
        shipment.setClient(dto.getClient());
        shipment.setPoids(dto.getPoids());
        shipment.setVolume(dto.getVolume());
        shipment.setProvenance(dto.getProvenance());
        shipment.setIncoterm(dto.getIncoterm());
        shipment.setMagasinCale(dto.getMagasinCale());
        shipment.setConfirme(dto.isConfirme());
        shipment.setMotifModification(dto.getMotifModification());
        shipment.setEstimatedDeparture(dto.getEstimatedDeparture());
        shipment.setEstimatedArrival(dto.getEstimatedArrival());

        // Save files
        shipment.setInsertionBlPath(saveFile(blFile));
        shipment.setBlFinalPath(saveFile(blFinalFile));
        shipment.setListeCollisagePath(saveFile(collisageFile));
        shipment.setFacturePath(saveFile(factureFile));

        // Link to user
       /* User user = userRepository.findById(dto.getUserId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        shipment.setUser(user);*/

        return shipmentRepository.save(shipment);
    }

    @Override
    public Shipment updateShipment(Long id, ShipmentDTO dto, MultipartFile blFile, MultipartFile blFinalFile,
                                   MultipartFile collisageFile, MultipartFile factureFile) throws IOException {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        shipment.setClient(dto.getClient());
        shipment.setPoids(dto.getPoids());
        shipment.setVolume(dto.getVolume());
        shipment.setProvenance(dto.getProvenance());
        shipment.setIncoterm(dto.getIncoterm());
        shipment.setMagasinCale(dto.getMagasinCale());
        shipment.setConfirme(dto.isConfirme());
        shipment.setMotifModification(dto.getMotifModification());
        shipment.setEstimatedDeparture(dto.getEstimatedDeparture());;
        shipment.setEstimatedArrival(dto.getEstimatedArrival());;

        if (blFile != null) shipment.setInsertionBlPath(saveFile(blFile));
        if (blFinalFile != null) shipment.setBlFinalPath(saveFile(blFinalFile));
        if (collisageFile != null) shipment.setListeCollisagePath(saveFile(collisageFile));
        if (factureFile != null) shipment.setFacturePath(saveFile(factureFile));

        return shipmentRepository.save(shipment);
    }

    @Override
    public Shipment getShipment(Long id) {
        return shipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }
    
    @Override
    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }
    
    public Resource getShipmentFile(Long shipmentId, String fileType) throws IOException {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        String filePath = switch (fileType.toLowerCase()) {
            case "bl" -> shipment.getInsertionBlPath();
            case "blfinal" -> shipment.getBlFinalPath();
            case "collisage" -> shipment.getListeCollisagePath();
            case "facture" -> shipment.getFacturePath();
            default -> throw new IllegalArgumentException("Invalid file type: " + fileType);
        };

        if (filePath == null || filePath.isBlank()) {
            throw new FileNotFoundException("File not found for type: " + fileType);
        }

        Path path = Paths.get(filePath).normalize();
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("Unable to read file at path: " + filePath);
        }

        return resource;
    }
}
