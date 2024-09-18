package com.example.aula.primeiroprojeto.controllers;


import com.example.aula.primeiroprojeto.dtos.ProductRecordDto;
import com.example.aula.primeiroprojeto.models.CategoryModel;
import com.example.aula.primeiroprojeto.models.ProductModel;
import com.example.aula.primeiroprojeto.repositories.CategoryRepository;
import com.example.aula.primeiroprojeto.repositories.ProductRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;
import jakarta.validation.Valid;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @PersistenceContext
    EntityManager em;

    UserTransaction utx;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable(value="id") UUID id ){
        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product nao encontrado");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(productO.get());
        }
    }
    @GetMapping("/category/{category_name}")

    @PostMapping
    public ResponseEntity<ProductModel> addProduct(@RequestBody @Valid ProductRecordDto productDto){
        ProductModel productModel =  new ProductModel();


        BeanUtils.copyProperties(productDto, productModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteProduct(@PathVariable(value="id") UUID id){
        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product nao encontrado");
        }else {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Product excluido com sucesso!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product nao encontrado");
        }else {
            var productModel = productO.get();
            BeanUtils.copyProperties(productRecordDto, productModel);
            return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
        }
    }

    @PostMapping("/jpa")
    public ResponseEntity<Object> addProductJPA(@RequestBody @Valid ProductRecordDto productDto) throws  Exception{
        String oi = "aa";
        try{
            utx.begin();
            em.persist(productDto);

            utx.commit();
            ProductModel productModel =  new ProductModel();


            BeanUtils.copyProperties(productDto, productModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
        }catch (Exception e){
            utx.rollback();
            throw  e;
        }
    }

    @Transactional
    public String criarProduto() throws Exception{
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = null;







        try {
            transaction = session.beginTransaction();

            ProductModel product =  new ProductModel();
            product.setName("Notebook");
            product.setValue(new BigDecimal(2000));

            session.save(product);

            transaction.commit();
        }catch (Exception e){
            if(transaction !=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
            return "oi";
        }
//JQPL
//        String jqpl = "SELECT * FROM Product";
//        TypedQuery<ProductModel> query = em.createQuery(jqpl,ProductModel.class );
//        List<ProductModel> productModels = query.getResultList();
//
//        String jqpl2 = "SELECT * FROM product WHERE price >= :precoMinimo";
//        TypedQuery<ProductModel> query2 = em.createQuery(jqpl,ProductModel.class );
//        query2.setParameter("precoMinimo",2000);
//        List<ProductModel> productByValue = query.getResultList();



    }




}
