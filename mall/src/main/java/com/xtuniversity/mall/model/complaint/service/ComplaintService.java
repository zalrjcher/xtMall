package com.xtuniversity.mall.model.complaint.service;

import com.xtuniversity.mall.model.base.BaseService;
import com.xtuniversity.mall.model.complaint.dao.IComplaintDao;
import com.xtuniversity.mall.model.complaint.entity.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
