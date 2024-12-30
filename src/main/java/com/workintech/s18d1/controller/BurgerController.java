package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import com.workintech.s18d1.util.BurgerValidation;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/burger")
public class BurgerController {


    private final BurgerDao burgerDao;
    @Autowired
    public BurgerController(BurgerDao burgerDao) {
        this.burgerDao = burgerDao;
    }

    @GetMapping
    public List<Burger> findAll(){
        return burgerDao.findAll();
    }
    @GetMapping("/{id}")
    public Burger findById(@PathVariable("id") long id){
        BurgerValidation.checkBurgerExist(id,burgerDao);
        return burgerDao.findById(id);
    }
    @PostMapping
    public Burger saveBurger(@RequestBody Burger burger){
        BurgerValidation.checkName(burger.getName());
        return burgerDao.save(burger);
    }
    @PutMapping
    public Burger updateBurger(@RequestBody Burger burger){

        return burgerDao.update(burger);
    }
    @DeleteMapping("/{id}")
    public Burger deleteBurger(@PathVariable("id") Long id){

        return burgerDao.remove(id);
    }
    @GetMapping("/price/{price}")
    public List<Burger> findByPrice(@PathVariable("price") Integer price){
        BurgerValidation.checkPrice(price,burgerDao);
        return burgerDao.findByPrice(price);
    }

    @GetMapping("/breadType/{breadType}")
    public List<Burger> findByBreadType(@PathVariable("breadType") String breadType){

        BurgerValidation.checkBreadTypeExist(breadType);


        BreadType breadTypeEnum = BreadType.valueOf(breadType.toUpperCase());

        return burgerDao.findByBreadType(breadTypeEnum);
    }

    @GetMapping("/content/{content}")
    public List<Burger> findByContent(@PathVariable("content") String content){

        return burgerDao.findByContent(content);
    }





}
