package com.example.aula.primeiroprojeto.controllers;

import com.example.aula.primeiroprojeto.dtos.CategoryRecordDto;

import com.example.aula.primeiroprojeto.models.CategoryModel;

import com.example.aula.primeiroprojeto.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<CategoryModel>> getAllCategorys(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.findAll());
    }
    

//    Get usando DTO (criada através de classe) sem a lista de produtos
//    @GetMapping
//    public ResponseEntity<List<GetCategoryDTO>> getAllCategorysSemProducts(){
//        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.findAll()
//                .stream()
//                .map(categoryModel -> new GetCategoryDto(category.getId(), category.getName()));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable(value="id") UUID id ){
        CategoryModel categoria = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria nao encontrada") );

            return ResponseEntity.status(HttpStatus.OK).body(categoria);

    }
    @PostMapping
    public ResponseEntity<CategoryModel> addCategory(@RequestBody @Valid CategoryRecordDto categoryDto){
        var categoryModel =  new CategoryModel();
        BeanUtils.copyProperties(categoryDto, categoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(categoryModel));
    }







}
