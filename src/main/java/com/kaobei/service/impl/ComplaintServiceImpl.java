package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.entity.ComplaintEntity;
import com.kaobei.mapper.ComplaintMapper;
import com.kaobei.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintMapper complaintMapper;


    @Override
    public ComplaintEntity insertComplaint(ComplaintEntity complaintEntity) {
        complaintMapper.insert(complaintEntity);

        return complaintEntity;
    }

    @Override
    public ComplaintEntity findComplaintById(Long complaintId) {
        return complaintMapper.selectById(complaintId);
    }

    @Override
    public void updateComplaintById(ComplaintEntity complaintEntity) {
        complaintMapper.updateById(complaintEntity);
    }

    @Override
    public void deleteComplaintById(Long complaintId) {
        complaintMapper.deleteById(complaintId);
    }

    @Override
    public IPage findComplaintPageByParkId(Long parkId, IPage page) {
        QueryWrapper<ComplaintEntity>wrapper = new QueryWrapper<>();
        wrapper.eq("park_id",parkId);
        wrapper.orderByAsc("status");
        return complaintMapper.selectPage(page,wrapper);
    }
}
