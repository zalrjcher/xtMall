package com.xtuniversity.mall.model.complaint.service;

import com.xtuniversity.mall.model.base.BaseService;
import com.xtuniversity.mall.model.complaint.dao.IComplaintDao;
import com.xtuniversity.mall.model.complaint.entity.Complaint;
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
public class ComplaintService extends BaseService {

    @Autowired
    @Qualifier("complaintDao")
    IComplaintDao complaintDao;

    public void save(Complaint complaint) {
        complaintDao.save(complaint);
    }

    public List<Complaint> getAll() {
       return complaintDao.findAll();
    }

    public Complaint getComplanitById(long complaintId) {
        return  complaintDao.findById(complaintId);
    }

    public void update(Complaint complaint) {
        complaintDao.update(complaint);
    }

    public List<Complaint> getComplanitByUserId(long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        return complaintDao.findAllByParam(map,new LinkedHashMap<>());
    }
}
