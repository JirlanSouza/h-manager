package com.js.hmanager.inventory.infra.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class RoomModel {
    @Id
    private UUID id;
    private String number;
    private int doubleBeds;
    private int singleBeds;
    private BigDecimal dailyRate;
    private boolean available;
}
