package com.kaobei.utils;

import com.alibaba.fastjson.JSONArray;
import com.kaobei.dto.ParkDto;
import com.kaobei.entity.ParkEntity;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DtoEntityUtils {


    static Mapper mapper = new DozerBeanMapper();

    public static <D, E> E parseToObject(D t, Class<E> clazz) {
        if (t == null)
            return null;
        return mapper.map(t, clazz);
    }

    public static <D, E> List<E> parseToArray(D[] ts, Class<E> clazz) {
        List<E> es = new ArrayList<E>();
        if (ts == null)
            return es;

        for (D d : ts) {
            E e = (E) parseToObject(d, clazz);
            if (e != null)
                es.add(e);
        }

        return es;
    }


    public static <D, E> List<E> parseToArray(List<D> ts, Class<E> clazz) {
        List<E> es = new ArrayList<E>();
        if (ts == null)
            return es;
        for (D d : ts) {
            E e = (E) parseToObject(d, clazz);
            if (e != null)
                es.add(e);
        }
        return es;
    }
}

