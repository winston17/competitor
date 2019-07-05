package com.yzc.bigdata.competitor.service;

import com.yzc.bigdata.competitor.entity.node.ProjectCp;
import com.yzc.bigdata.competitor.model.EsWinCandidateModel;
import com.yzc.bigdata.competitor.model.InsertModel;
import com.yzc.bigdata.competitor.repository.ProjectCpRepository;
import com.yzc.bigdata.competitor.util.Similarity;
import com.yzc.bigdata.competitor.util.baseUtil.EncryptionUtil;
import com.yzc.bigdata.competitor.util.baseUtil.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.hibernate.sql.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Classname EsService
 * @Author lizonghuan
 * @Description
 * @Date-Time 2019/6/20-16:35
 * @Version 1.0
 */
@Service
@Getter
@Setter
public class EsService {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    ProjectCpRepository projectCpRepository;

    private String searchIndex = "dataplatform";
    private String searchType = "noticemodel";

    /**
     * scroll游标快照超时时间，单位ms
     */
    private static final long SCROLL_TIMEOUT = 1000 * 60 * 30;

    /**
     * 用于将Scroll获取到的结果，处理成dto列表，做复杂映射
     */
    private final SearchResultMapper insertModelSearchResultMapper = new SearchResultMapper() {
        @Override
        public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
            SearchHits searchHits = searchResponse.getHits();
            InsertModel insertModel;
            List<InsertModel> insertModelList = new ArrayList<>();
            if (searchHits.getHits().length <= 0) {
                return new AggregatedPageImpl<T>(Collections.EMPTY_LIST, pageable, searchResponse.getHits().getTotalHits(), searchResponse.getScrollId());
            }
            for(SearchHit searchHit : searchHits){
                if (searchHit.getSourceAsMap().get("noticeTitle") == null || searchHit.getSourceAsMap().get("noticeTitle") == ""){
                    continue;
                }
                if (searchHit.getSourceAsMap().get("winCandidate") != null){
                    JSONArray jsonArray = JSONArray.fromObject(searchHit.getSourceAsMap().get("winCandidate"));
                    JsonConfig config = new JsonConfig();
                    config.setRootClass(EsWinCandidateModel.class);
                    List<EsWinCandidateModel> esWinCandidateModelList = JSONArray.toList(jsonArray,config);
                    for(EsWinCandidateModel esWinCandidateModel : esWinCandidateModelList){
                        if (esWinCandidateModel.getBidderAgg() == null || esWinCandidateModel.getBidderAgg() == ""){
                            continue;
                        }
                        insertModel = new InsertModel();
                        insertModel.setInviterNewId(searchHit.getSourceAsMap().get("tenderAgg") == null ?
                                EncryptionUtil.encrypByMD5("未知机构") :
                                EncryptionUtil.encrypByMD5(searchHit.getSourceAsMap().get("tenderAgg").toString()));
                        insertModel.setProjectId(searchHit.getSourceAsMap().get("noticeTitle") == null ?
                                null : EncryptionUtil.encrypByMD5(searchHit.getSourceAsMap().get("noticeTitle").toString()));
                        insertModel.setSubmitterNewId(EncryptionUtil.encrypByMD5(esWinCandidateModel.getBidderAgg()));
                        insertModel.setInviterId(searchHit.getSourceAsMap().get("tenderAgg") == null ?
                                EncryptionUtil.encrypByMD5("未知机构") :
                                EncryptionUtil.encrypByMD5(searchHit.getSourceAsMap().get("tenderAgg").toString()));
                        insertModel.setInviterName(searchHit.getSourceAsMap().get("tenderAgg") == null ?
                                "未知机构" :
                                searchHit.getSourceAsMap().get("tenderAgg").toString());
                        insertModel.setSubmitterId(EncryptionUtil.encrypByMD5(esWinCandidateModel.getBidderAgg()));
                        insertModel.setSubmitterName(esWinCandidateModel.getBidderAgg());
                        insertModel.setProjectType(7);
                        insertModel.setProjectName(searchHit.getSourceAsMap().get("noticeTitle") == null ?
                                "未知标题" :
                                searchHit.getSourceAsMap().get("noticeTitle").toString());
                        insertModel.setBulletinId(insertModel.getProjectId());
                        insertModel.setExpStatus(null);
                        insertModel.setDealStatus(1);
                        insertModel.setSignUpTime(searchHit.getSourceAsMap().get("pubDate") == null ?
                                null : searchHit.getSourceAsMap().get("pubDate").toString());
                        insertModel.setExpTime(null);
                        insertModel.setDealTime(searchHit.getSourceAsMap().get("pubDate") == null ?
                                null : searchHit.getSourceAsMap().get("pubDate").toString());
                        insertModel.setTendWay(1);
                        insertModel.setDealMoney(esWinCandidateModel.getWinMoney() == null ?
                                null : esWinCandidateModel.getWinMoney().toString());
                        insertModel.setSourceType(3);
                        insertModel.setTenderContacter(searchHit.getSourceAsMap().get("tenderContacter") == null ?
                                null : searchHit.getSourceAsMap().get("tenderContacter").toString());
                        insertModel.setTenderContactPhone(searchHit.getSourceAsMap().get("tenderTel") == null ?
                                null : searchHit.getSourceAsMap().get("tenderTel").toString());
                        insertModel.setTenderCompanyAddress(searchHit.getSourceAsMap().get("tenderAddress") == null ?
                                null : searchHit.getSourceAsMap().get("tenderAddress").toString());
                        insertModel.setBidderContacter(esWinCandidateModel.getContacter());
                        insertModel.setBidderContactPhone(esWinCandidateModel.getContacterTel());
                        insertModel.setBidderCompanyAddress(esWinCandidateModel.getContacterAddress());
                        insertModelList.add(insertModel);
                    }
                }
            }
            if (insertModelList.isEmpty()){
                return new AggregatedPageImpl<T>(Collections.EMPTY_LIST, pageable, searchResponse.getHits().getTotalHits(), searchResponse.getScrollId());
            }
            return new AggregatedPageImpl<T>((List<T>) insertModelList, pageable, searchResponse.getHits().getTotalHits(), searchResponse.getScrollId());
        }
    };

    //在搜索引擎中查询时间范围内的所有站外公告
    public List<InsertModel> selectProject(String lastUpdateTime){
        QueryBuilder queryBuilder1 = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("noticeType", "中标公告"))
                .filter(QueryBuilders.rangeQuery("pubDate").gt(lastUpdateTime))
                .filter(QueryBuilders.existsQuery("winCandidate"));
        QueryBuilder queryBuilder2 = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("noticeType", "中标候选人公示"))
                .filter(QueryBuilders.rangeQuery("pubDate").gt(lastUpdateTime))
                .filter(QueryBuilders.existsQuery("winCandidate"));

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(queryBuilder1)
                .should(queryBuilder2);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(this.searchIndex)
                .withTypes(this.searchType)
                .withQuery(queryBuilder1)
                .withPageable(PageRequest.of(0, 1000))
                .build();

        System.out.println("queryBuilder: " + queryBuilder);
        System.out.println("searchQuery: " + searchQuery.getQuery());


        List<InsertModel> insertModelList = searchInsertModelList(searchQuery);

        List<InsertModel> resultList = new ArrayList<>();
        if (insertModelList != null){
            resultList.addAll(insertModelList);
        }
        return resultList;
    }

    //在搜索引擎中查询时间范围内的所有站外公告, 每次游标1000分批导入
    public int patchSelectImportProject(String lastUpdateTime){
        QueryBuilder queryBuilder1 = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("noticeType", "中标公告"))
                .filter(QueryBuilders.rangeQuery("pubDate").gt(lastUpdateTime))
                .filter(QueryBuilders.existsQuery("winCandidate"));
        QueryBuilder queryBuilder2 = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("noticeType", "中标候选人公示"))
                .filter(QueryBuilders.rangeQuery("pubDate").gt(lastUpdateTime))
                .filter(QueryBuilders.existsQuery("winCandidate"));

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(queryBuilder1)
                .should(queryBuilder2);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(this.searchIndex)
                .withTypes(this.searchType)
                .withQuery(queryBuilder1)
                .withPageable(PageRequest.of(0, 1000))
                .build();

        System.out.println("queryBuilder: " + queryBuilder);
        System.out.println("searchQuery: " + searchQuery.getQuery());


        int count  = patchSearchImportInsertModelList(searchQuery);

        return count;
    }



    //利用游标scroll查询出所有的符合条件的InsertModel
    public List<InsertModel> searchInsertModelList(SearchQuery searchQuery){
        ScrolledPage<InsertModel> scroll = (ScrolledPage<InsertModel>) elasticsearchTemplate.startScroll(SCROLL_TIMEOUT, searchQuery, InsertModel.class, insertModelSearchResultMapper);

        String scrollId = scroll.getScrollId();
        System.out.println("scroll = " + scroll);

        System.out.println("scrollId = " + scrollId);
        List<InsertModel> insertModelList = new ArrayList<>();
        int count = 0;
        while (scroll.hasContent()) {
            insertModelList.addAll(scroll.getContent());
            System.out.println("insertModelList.size() is " + insertModelList.size());
            scrollId = scroll.getScrollId();
            System.out.println("计数: " + count++);
            System.out.println("scroll = " + scroll);
//            System.out.println("scroll.content is " + scroll.getContent());
            System.out.println("scrollId = " + scrollId);
            scroll = (ScrolledPage<InsertModel>) elasticsearchTemplate.continueScroll(scrollId, SCROLL_TIMEOUT, InsertModel.class, insertModelSearchResultMapper);
        }
        elasticsearchTemplate.clearScroll(scrollId);
        return insertModelList;
    }


    //利用游标scroll查询并且导入数据到neo4j, 其中每次游标查出的数千条记录立即导入到neo4j再进行下次查询
    public int patchSearchImportInsertModelList(SearchQuery searchQuery){
        ScrolledPage<InsertModel> scroll = (ScrolledPage<InsertModel>) elasticsearchTemplate.startScroll(SCROLL_TIMEOUT, searchQuery, InsertModel.class, insertModelSearchResultMapper);

        String scrollId = scroll.getScrollId();
        System.out.println("scroll = " + scroll);

        System.out.println("scrollId = " + scrollId);
        int count = 0, round = 0, status = 0;
        List<InsertModel> insertModelList;
        while (scroll.hasContent()) {
            insertModelList = scroll.getContent();
            count += insertModelList.size();
            System.out.println("insertModelList.size() is " + count);
            //每次游标的1000左右的分批都直接塞入
            for(InsertModel insertModel : insertModelList){
                    status = projectCpRepository.addCompanyAndProjectOffSite(insertModel.getInviterNewId(), insertModel.getProjectId(),
                        insertModel.getSubmitterNewId(), insertModel.getInviterId(), insertModel.getInviterName(), insertModel.getSubmitterId(),
                        insertModel.getSubmitterName(), insertModel.getProjectType(), insertModel.getProjectName(), insertModel.getBulletinId(),
                        insertModel.getBulletinName(), insertModel.getExpStatus(), insertModel.getDealStatus(), insertModel.getSignUpTime(),
                        insertModel.getExpTime(), insertModel.getDealTime(), insertModel.getTendWay(), insertModel.getDealMoney(),
                        insertModel.getTenderContacter(), insertModel.getTenderContactPhone(), insertModel.getTenderCompanyAddress(),
                        insertModel.getBidderContacter(), insertModel.getBidderContactPhone(), insertModel.getBidderCompanyAddress(),
                            insertModel.getSourceType());
                    System.out.println("站外InsertModel单条插入状态 " + status);
            }
            scrollId = scroll.getScrollId();
            System.out.println("计数: " + round++);
            System.out.println("scrollId = " + scrollId);
            scroll = (ScrolledPage<InsertModel>) elasticsearchTemplate.continueScroll(scrollId, SCROLL_TIMEOUT, InsertModel.class, insertModelSearchResultMapper);
        }
        elasticsearchTemplate.clearScroll(scrollId);
        return count;
    }


}

