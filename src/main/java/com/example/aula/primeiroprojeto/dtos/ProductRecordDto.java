package com.example.aula.primeiroprojeto.dtos;

import com.example.aula.primeiroprojeto.models.CategoryModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value, CategoryModel category) {
}
