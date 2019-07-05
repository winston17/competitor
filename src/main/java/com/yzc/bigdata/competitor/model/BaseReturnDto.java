package com.yzc.bigdata.competitor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Classname BaseReturnDto
 * @Author lizonghuan
 * @Description 返回结果dto对象
 * @Date-Time 2019/6/19-14:27
 * @Version 1.0
 */

@Getter
@Setter
@ToString
public class BaseReturnDto<T> {
    private String status;

    private String desc;

    private T data;

    private List<T> dataList;

}
