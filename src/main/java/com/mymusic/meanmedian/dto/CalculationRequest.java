package com.mymusic.meanmedian.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CalculationRequest(
        @NotEmpty
        List<@NotNull Double> values
) {}
