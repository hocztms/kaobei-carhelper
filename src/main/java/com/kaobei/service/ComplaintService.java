package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.entity.ComplaintEntity;

import java.util.List;

public interface ComplaintService {


    ComplaintEntity insertComplaint(ComplaintEntity complaintEntity);

    ComplaintEntity findComplaintById(Long complaintId);

    void updateComplaintById(ComplaintEntity complaintEntity);

    void deleteComplaintById(Long complaintId);

    IPage findComplaintPageByParkId(Long parkId, IPage page);
}
