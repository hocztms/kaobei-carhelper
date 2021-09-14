package com.kaobei.service;

import com.kaobei.entity.ComplaintEntity;

public interface ComplaintService {


    ComplaintEntity insertComplaint(ComplaintEntity complaintEntity);

    void updateComplaintById(ComplaintEntity complaintEntity);

    void deleteComplaintById(Long complaintId);
}
