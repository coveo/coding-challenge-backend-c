package com.company.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "cities")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class City {

    @Id
    private Long id;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal population;
}
