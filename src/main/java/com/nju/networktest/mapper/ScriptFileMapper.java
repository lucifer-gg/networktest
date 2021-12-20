package com.nju.networktest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScriptFileMapper {
    List<Integer> test(@Param("id")int id);
}
