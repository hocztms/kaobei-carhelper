package com.kaobei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.dto.ParkDto;
import com.kaobei.entity.ParkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ParkMapper extends BaseMapper<ParkEntity> {


    @Select("SELECT * FROM tb_park WHERE MATCH (park_name) AGAINST ( #{keyword} IN NATURAL LANGUAGE MODE)")
    List<ParkDto> findParkDtoByKeyword(@Param("keyword") String keyword,IPage ipage);
}
