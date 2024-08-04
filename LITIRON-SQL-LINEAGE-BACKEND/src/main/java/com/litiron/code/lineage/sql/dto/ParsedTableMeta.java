package com.litiron.code.one.sql.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 解析数据之后的相关的信息
 * @author: Litiron
 * @create: 2024-06-08 10:19
 **/
@Getter
@Setter
public class ParsedTableMeta {

    private List<String> sourceTableList = new ArrayList<>();

    private List<String> destTableList = new ArrayList<>();

    @Override
    public String toString() {
        return "sourceTableList: " + sourceTableList.toString() + "\n" + "destTableList: " + destTableList.toString();
    }
}
