package com.app.logistics.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ShipmentResponseDTO {
	
	private Long idShipment;
    private String client;
    private String poids;
    private String volume;
    private String provenance;
    private String incoterm;
    private String magasinCale;
    private boolean confirme;
    private String motifModification;
    private String blFinalPath;
    private String insertionBlPath;
    private String listeCollisagePath;
    private String facturePath;
    private LocalDate estimatedDeparture;
    private LocalDate estimatedArrival;
    private String username;

}
