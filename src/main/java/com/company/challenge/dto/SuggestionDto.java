package com.company.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SuggestionDto {

    private String name;
    private String latitude;
    private String longitude;
    private BigDecimal score;
}
