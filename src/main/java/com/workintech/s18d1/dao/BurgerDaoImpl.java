package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Repository
public class BurgerDaoImpl implements BurgerDao{

    private final EntityManager entityManager;

    @Autowired
    public BurgerDaoImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }
    @Transactional
    @Override
    public Burger save(Burger burger) {
        log.info("save started");
        entityManager.persist(burger);
        log.info("save success ended");
        return burger;
    }

    @Override
    public Burger findById(long id) {
      Burger burger= entityManager.find(Burger.class,id);
      if(burger==null){
          throw new BurgerException("Burger not found: "+id, HttpStatus.NOT_FOUND);
      }
        return burger;
    }

    @Override
    public List<Burger> findAll() {
        TypedQuery<Burger> burgerQuery=entityManager.createQuery("SELECT b FROM Burger b",Burger.class);
        return burgerQuery.getResultList();
    }

    @Override
    public List<Burger> findByPrice(Integer price) {
        TypedQuery<Burger> priceQuery=entityManager.createQuery("SELECT b FROM Burger b WHERE b.price>:price ORDER BY b.price DESC",Burger.class);
        priceQuery.setParameter("price",price);
        return priceQuery.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        TypedQuery<Burger> breadTypeQuery=entityManager.createQuery("SELECT b FROM Burger b WHERE b.breadType=:bread_type ORDER BY b.name",Burger.class);
        breadTypeQuery.setParameter("breadType",breadType);
        return breadTypeQuery.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
       TypedQuery<Burger> contentTypeQuery=entityManager.createQuery("SELECT b FROM Burger b WHERE b.contents ILIKE CONCAT('%',:content,'%')",Burger.class);
       contentTypeQuery.setParameter("content",content);
       return contentTypeQuery.getResultList();
    }
    @Transactional
    @Override
    public Burger update(Burger burger) {
        return entityManager.merge(burger);

    }
    @Transactional
    @Override
    public Burger remove(long id) {
        Burger willBeDeletedBurger=entityManager.find(Burger.class,id);
        entityManager.remove(willBeDeletedBurger);
        return willBeDeletedBurger;

    }
}
