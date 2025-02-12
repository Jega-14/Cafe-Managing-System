package com.inn.cafe.serviceImpl;

import com.google.common.base.Strings;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.CategoryDao;
import com.inn.cafe.rest.CategoryRest;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    private static final org. slf4j. Logger log
            = org. slf4j. LoggerFactory. getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestmap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validateCategoryMap(requestmap,false)){
                    categoryDao.save(getCategoryFromMap(requestmap,false));
                    return CafeUtils.getResponseEntity("Category Added Successfully",HttpStatus.OK);
                }
            }else {
                return CafeUtils.getResponseEntity(CafeConstants.Unauthorized_data,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.Something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestmap, boolean validateId) {
        if (requestmap.containsKey("name")){
            if (requestmap.containsKey("id") && validateId){
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String,String> requestMap, Boolean isAdd){
        Category category = new Category();
        if (isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Inside if");
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validateCategoryMap(requestMap,true)){
                    Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        categoryDao.save(getCategoryFromMap(requestMap,true));
                        return CafeUtils.getResponseEntity("Category updated successfully",HttpStatus.OK);
                    }else {
                        return CafeUtils.getResponseEntity("Category id doesn't exist",HttpStatus.OK);
                    }
                }
                return CafeUtils.getResponseEntity(CafeConstants.invalid_data,HttpStatus.BAD_REQUEST);
            }else {
                return CafeUtils.getResponseEntity(CafeConstants.Unauthorized_data,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.Something_went_wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
