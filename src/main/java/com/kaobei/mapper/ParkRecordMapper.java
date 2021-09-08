package com.kaobei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.dto.ParkRecordDto;
import com.kaobei.entity.ParkRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;

@Mapper
public interface ParkRecordMapper extends BaseMapper<ParkRecordEntity> {



    @Select("select * from tb_parkRecord where area_id = #{areaId} and status = 1 and end_time >= #{date} order by park_id desc")
    List<ParkRecordDto> selectRecordsPageByAreaId(@Param("areaId") Long areaId,@Param("date") Date date, IPage iPage);

    @Select("select * from tb_parkRecord where park_id = #{parkId} and status = 1 and end_time >= #{date} order by park_id desc")
    List<ParkRecordDto> selectRecordsPageByParkId(@Param("parkId") Long parkId,@Param("date") Date date, IPage iPage);
}
