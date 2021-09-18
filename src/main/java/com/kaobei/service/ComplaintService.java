package com.kaobei.service;

import com.kaobei.entity.ComplaintEntity;

import java.util.List;

public interface ComplaintService {


    ComplaintEntity insertComplaint(ComplaintEntity complaintEntity);

    void updateComplaintById(ComplaintEntity complaintEntity);

    void deleteComplaintById(Long complaintId);

    List<ComplaintEntity> findComplaintsByParkId(Long parkId);
}
