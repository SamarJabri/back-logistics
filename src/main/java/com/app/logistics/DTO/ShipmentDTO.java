package com.app.logistics.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.app.logistics.entities.Incoterm;

import lombok.Data;

@Data
public class ShipmentDTO {
	
	private String client;
    private String poids;
    private String volume;
    private String provenance;
    private Incoterm incoterm;
    private String magasinCale;
    private boolean confirme;
    private String motifModification;
    private LocalDate estimatedDeparture;
    private LocalDate estimatedArrival;
    private Long userId;

}
