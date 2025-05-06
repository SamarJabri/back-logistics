package com.app.logistics.services;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.app.logistics.DTO.ShipmentDTO;
import com.app.logistics.entities.Shipment;

public interface ShipmentService {

	Shipment addShipment(ShipmentDTO shipmentDTO, MultipartFile blFile, MultipartFile blFinalFile,
            MultipartFile collisageFile, MultipartFile factureFile) throws IOException;

Shipment updateShipment(Long id, ShipmentDTO shipmentDTO, MultipartFile blFile, MultipartFile blFinalFile,
               MultipartFile collisageFile, MultipartFile factureFile) throws IOException;

Shipment getShipment(Long id);

void deleteShipment(Long id);

List<Shipment> getAllShipments();

Resource getShipmentFile(Long shipmentId, String fileType) throws IOException;

}
