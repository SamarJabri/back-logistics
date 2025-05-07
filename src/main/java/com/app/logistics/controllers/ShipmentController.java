package com.app.logistics.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.logistics.DTO.ShipmentDTO;
import com.app.logistics.entities.Shipment;
import com.app.logistics.services.ShipmentService;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @CrossOrigin(origins = "https://stupendous-2db6ae.netlify.app")
    @PostMapping(value ="/add-shipment",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Shipment> addShipment(
            @RequestPart("shipment") ShipmentDTO dto,
            @RequestPart(value = "blFile", required = false) MultipartFile blFile,
            @RequestPart(value = "blFinalFile", required = false) MultipartFile blFinalFile,
            @RequestPart(value = "collisageFile", required = false) MultipartFile collisageFile,
            @RequestPart(value = "factureFile", required = false) MultipartFile factureFile
    ) throws IOException {
        Shipment shipment = shipmentService.addShipment(dto, blFile, blFinalFile, collisageFile, factureFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(shipment);
    }

    @CrossOrigin(origins = "https://stupendous-2db6ae.netlify.app")
    @PutMapping(value = "update-shipment/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Shipment> updateShipment(
            @PathVariable Long id,
            @RequestPart("shipment") ShipmentDTO dto,
            @RequestPart(value = "blFile", required = false) MultipartFile blFile,
            @RequestPart(value = "blFinalFile", required = false) MultipartFile blFinalFile,
            @RequestPart(value = "collisageFile", required = false) MultipartFile collisageFile,
            @RequestPart(value = "factureFile", required = false) MultipartFile factureFile
    ) throws IOException {
        Shipment updated = shipmentService.updateShipment(id, dto, blFile, blFinalFile, collisageFile, factureFile);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("get-shipment/{id}")
    public ResponseEntity<Shipment> getShipment(@PathVariable Long id) {
        Shipment shipment = shipmentService.getShipment(id);
        return ResponseEntity.ok(shipment);
    }

    @GetMapping ("get-all-shipments")
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }

    @DeleteMapping("delete-shipment/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/download/{fileType}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id, @PathVariable String fileType) {
        try {
            Resource fileResource = shipmentService.getShipmentFile(id, fileType);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                    .body(fileResource);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

