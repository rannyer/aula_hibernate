package com.example.aula.primeiroprojeto.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GetCategoryDto(@NotNull UUID id, @NotBlank String name) {
}
