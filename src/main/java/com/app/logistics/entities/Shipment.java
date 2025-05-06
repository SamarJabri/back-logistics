package com.app.logistics.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="Shipment")
public class Shipment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idShipment;

    String client;
    String poids;
    String volume;
    String provenance;

    @Enumerated(EnumType.STRING)
    Incoterm incoterm;

    String magasinCale;

    String insertionBlPath;
    boolean confirme;
    String motifModification;
    String blFinalPath;
    String listeCollisagePath;
    String facturePath;

    LocalDate estimatedDeparture;
    LocalDate estimatedArrival;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
