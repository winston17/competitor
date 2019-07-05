package com.yzc.bigdata.competitor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2019/7/1.
 */
@Getter
@Setter
public class PageQueryModel<T> {
    private int pageSize; //页面大小
    private  int page;//页码
    //查询结果
    private List<T> result;
    private int total;
}
