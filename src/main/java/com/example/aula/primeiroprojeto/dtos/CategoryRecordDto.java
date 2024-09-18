package com.example.aula.primeiroprojeto.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryRecordDto(@NotBlank String name) {
}
