package com.workintech.s18d1.util;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BurgerValidation {
    public static void checkName(String name) {
        if(name==null || name.isEmpty()){
            throw new BurgerException("Name is null or empty!", HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkBurgerExist(long id, BurgerDao burgerDao) {
        if(burgerDao.findById(id)==null){
            throw new BurgerException("No Burger found with id: "+id,HttpStatus.NOT_FOUND);
        }
    }

    public static void checkPrice(int price, BurgerDao burgerDao) {
       if(price<=0){
           throw new BurgerException("Invalid price: "+price,HttpStatus.BAD_REQUEST);
       }
       if(burgerDao.findByPrice(price)==null){
           throw new BurgerException("No Burger found with price: "+price,HttpStatus.NOT_FOUND);
       }

    }

    public static void checkBreadTypeExist(String breadType) {
        try {
            // BreadType enumında olup olmadığını kontrol et
            BreadType.valueOf(breadType.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Geçersiz BreadType olduğunda istisna fırlat
            throw new BurgerException("Bread Type is not found: " + breadType, HttpStatus.NOT_FOUND);
        }
    }

    public static void checkContent(String content, List<Burger> burgers) {
        boolean exist=burgers.stream().anyMatch(burger -> burger.getContents().equalsIgnoreCase(content));
        if (!exist){
            throw new BurgerException("Content is not found"+content,HttpStatus.NOT_FOUND);
        }

    }
}
