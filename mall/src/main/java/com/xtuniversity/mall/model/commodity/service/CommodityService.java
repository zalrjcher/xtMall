package com.xtuniversity.mall.model.commodity.service;

import com.xtuniversity.mall.model.base.BaseService;
import com.xtuniversity.mall.model.commodity.dao.ICommodityDao;
import com.xtuniversity.mall.model.commodity.entity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zlj
 * @introduce
 */
@Service
public class CommodityService extends BaseService {
    @Autowired
    @Qualifier(value = "commodityDao")
    ICommodityDao commodityDao ;

    public Commodity getCommodityById(long commodityId) {
        return commodityDao.findById(commodityId);
    }

    public void updateCommodity(Commodity commodity) {
        commodityDao.update(commodity);
    }

    public void saveCommodity(Commodity commodity) {
        commodityDao.save(commodity);
    }

    public List<Commodity> getCommodityByUserId(long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
      return commodityDao.findAllByParam(map,null);
    }

    public boolean deleteCommodityById(long commodityId) {
        commodityDao.delete(commodityId);
      Commodity commodity =  commodityDao.findById(commodityId);
      if(commodity == null ){
          return  true;
      }
      return  false ;
    }


    public List<Commodity> getCommodityByType(int type) {
        if(type == 0 ){
            return commodityDao.findAll();
        }
        else{
            Map<String,Object> map = new HashMap<>();
            map.put("type",type);
            return commodityDao.findAllByParam(map,new LinkedHashMap<>());
        }
    }

    public List<Commodity> getCommodityByState(int state) {
        Map<String,Object> map = new HashMap<>();
        map.put("state",state);
        return commodityDao.findAllByParam(map,new LinkedHashMap<>());
    }
}
