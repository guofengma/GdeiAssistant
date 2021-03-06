package com.linguancheng.gdeiassistant.Repository.Mysql.GdeiAssistant.Cet;

import com.linguancheng.gdeiassistant.Pojo.Entity.CetNumber;
import org.apache.ibatis.annotations.Param;

public interface CetMapper {

    public CetNumber selectNumber(String username) throws Exception;

    public void insertNumber(@Param("username") String username, @Param("number") Long number) throws Exception;

    public void updateNumber(@Param("username") String username, @Param("number") Long number) throws Exception;
}
