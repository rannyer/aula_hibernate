package com.example.aula.primeiroprojeto.repositories;

import com.example.aula.primeiroprojeto.models.CategoryModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {



}
