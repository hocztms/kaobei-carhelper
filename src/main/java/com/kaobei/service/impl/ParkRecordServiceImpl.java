package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.dto.ParkRecoedAbleDto;
import com.kaobei.dto.ParkRecordDto;
import com.kaobei.entity.ParkRecordEntity;
import com.kaobei.mapper.ParkRecordMapper;
import com.kaobei.service.ParkRecordService;
import com.kaobei.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
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
    public ParkRecordEntity getUserIsParkByOpenId(String openId){
        QueryWrapper<ParkRecordEntity> wrapper = new QueryWrapper<>();

        wrapper.eq("open_id",openId);
        wrapper.eq("status",0);
        //ParkRecoedAbleDto parkRecoedAbleDto= new ParkRecoedAbleDto();
        //BeanUtils.copyProperties(parkRecordMapper.selectOne(wrapper),parkRecoedAbleDto);
        return parkRecordMapper.selectOne(wrapper);
    }

    @Override
    public List<ParkRecordDto> getAreaDayRecordsByPage(Long areaId, IPage page, Date date) {

        return parkRecordMapper.selectRecordsPageByAreaId(areaId, DateUtils.getZeroTime(),page);
    }

    @Override
    public IPage getParkDateRecordsByPage(Long parkId, Date date, IPage page) {
        QueryWrapper<ParkRecordEntity> wrapper = new QueryWrapper<>();

        wrapper.eq("park_id",parkId);
        wrapper.ge("end_time",date);
        wrapper.le("end_time",DateUtils.getTomoZeroTime(date));
        return parkRecordMapper.selectPage(page,wrapper);
    }

    @Override
    public Double getAreaRecordsCostSum(Long areaId, Date dayTime) {
        QueryWrapper<ParkRecordEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("area_id",areaId);
        wrapper.select("ifnull(sum(cost),0) as cost");
        wrapper.ge("end_time", DateUtils.getZeroTime(dayTime));
        wrapper.le("end_time",DateUtils.getTomoZeroTime(dayTime));
        ParkRecordEntity parkRecordEntity = parkRecordMapper.selectOne(wrapper);
        return parkRecordEntity.getCost();
    }

    @Override
    public Double getParkRecordsCostSum(Long parkId, Date dayTime) {
        QueryWrapper<ParkRecordEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("park_id",parkId);
        wrapper.select("ifnull(sum(cost),0) as cost");
        wrapper.ge("end_time", DateUtils.getZeroTime(dayTime));
        wrapper.le("end_time",DateUtils.getTomoZeroTime(dayTime));
        ParkRecordEntity parkRecordEntity = parkRecordMapper.selectOne(wrapper);
        return parkRecordEntity.getCost();
    }
}
