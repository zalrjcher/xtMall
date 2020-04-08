package com.xtuniversity.mall.model.address.service;

import com.xtuniversity.mall.model.address.dao.IAddressDao;
import com.xtuniversity.mall.model.address.entity.Address;
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
public class AddressService {
    @Autowired
    @Qualifier("addressDao")
    IAddressDao addressDao;
    public List<Address> getAddressByUserId(long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
      return addressDao.findAllByParam(map,new LinkedHashMap<>());
    }

    public void saveAddress(Address address1) {
        addressDao.save(address1);
    }

    public Address getAddressById(Long addressId) {
        return  addressDao.findById(addressId);
    }

    public void removeAddress(Address address) {
        addressDao.delete(address.getId());
    }

    public void update(Address address) {
        addressDao.update(address);
    }
}
