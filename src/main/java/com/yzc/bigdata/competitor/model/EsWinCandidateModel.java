package com.yzc.bigdata.competitor.model;

import lombok.*;

import java.io.Serializable;

/**
 * @Classname EsWinCandidateModel
 * @Author lizonghuan
 * @Description 搜索引擎中标公告中的wincandidate映射对象
 * @Date-Time 2019/6/20-18:01
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EsWinCandidateModel implements Serializable {

    private String bidder;

    private String bidderAgg;

    private Double winMoney;

    private Integer rank;

    private Integer wcType;

    private String contacter;

    private String contacterTel;

    private String contacterAddress;

}
