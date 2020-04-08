package com.xtuniversity.mall.model.type.service;

import com.xtuniversity.mall.model.type.dao.ITypeDao;
import com.xtuniversity.mall.model.type.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zlj
 * @introduce
 */
@Service
public class TypeService {
    @Qualifier(value="typeDao")
    @Autowired
    ITypeDao typeDao;

    public List<Type> getAllType() {
        return  typeDao.findAll();
    }

    public Type getTypeById(Long type) {
        return typeDao.findById(type);
    }
}
