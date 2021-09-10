package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.dto.ParkRecoedAbleDto;
import com.kaobei.dto.ParkRecordDto;
import com.kaobei.entity.ParkRecordEntity;
import com.kaobei.mapper.ParkRecordMapper;
import com.kaobei.service.ParkRecordService;
import com.kaobei.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.Watchable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ParkRecordServiceImpl implements ParkRecordService {
    @Resource
    private ParkRecordMapper parkRecordMapper;


    @Override
    public ParkRecordEntity insertRecord(ParkRecordEntity parkRecordEntity) {
        parkRecordMapper.insert(parkRecordEntity);
        return parkRecordEntity;
    }

    @Override
    public void updateRecord(ParkRecordEntity parkRecordEntity) {
        parkRecordMapper.updateById(parkRecordEntity);
    }

    @Override
    public void deleteRecord(Long parkId) {
        parkRecordMapper.deleteById(parkId);
    }

    @Override
    public ParkRecoedAbleDto getUserIsParkByOpenId(String openId) {
        QueryWrapper<ParkRecordEntity> wrapper = new QueryWrapper<>();

        wrapper.eq("open_id",openId);
        wrapper.eq("status",0);
        ParkRecoedAbleDto parkRecoedAbleDto= new ParkRecoedAbleDto();
        BeanUtils.copyProperties(parkRecordMapper.selectOne(wrapper),parkRecoedAbleDto);
        return parkRecoedAbleDto;
    }

    @Override
    public List<ParkRecordDto> getAreaRecordsByPage(Long areaId, IPage page) {

        return parkRecordMapper.selectRecordsPageByAreaId(areaId, DateUtils.getZeroTime(),page);
    }

    @Override
    public List<ParkRecordDto> getParkRecordsByPage(Long parkId, IPage page) {
        return parkRecordMapper.selectRecordsPageByParkId(parkId,DateUtils.getZeroTime(),page);
    }

    @Override
    public Double getAreaRecordsCostSum(Long areaId) {
        QueryWrapper<ParkRecordEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("area_id",areaId);
        wrapper.select("ifnull(sum(cost),0) as cost");
        wrapper.ge("end_time", DateUtils.getZeroTime());
        ParkRecordEntity parkRecordEntity = parkRecordMapper.selectOne(wrapper);
        return parkRecordEntity.getCost();
    }
}
