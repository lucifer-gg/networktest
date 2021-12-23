package com.nju.networktest.mapper;

import com.nju.networktest.entity.po.ScriptFilePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScriptFileMapper {
    List<Integer> test(@Param("id")int id);

    void insertScriptFile(ScriptFilePO scriptFilePO);

    void insertScriptCommand(@Param("fileId")Integer fileId,@Param("routerName")String routerName,@Param("commandOrder")Integer commandOrder,@Param("command")String command);

    List<ScriptFilePO> getScriptFile(@Param("fileName")String fileName);

    List<String> getCommands(@Param("id")Integer id,@Param("routerName")String routerName);

    void deleteScriptFile(@Param("fileName")String fileName);

    void deleteScriptCommands(@Param("fileId")Integer fileId);

    List<Integer> getFileIdByName(@Param("fileName")String fileName);

    List<String> getAllScriptFileName();
}
