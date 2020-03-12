package com.qf.service.impl;

import com.qf.entity.TProduct;
import com.qf.mapper.TProductMapper;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService implements ISearchService {
    @Autowired
    private SolrClient solrClient;
    @Autowired
    private TProductMapper tProductMapper;


    //处理msql中查询出的产品的所有数据，将需要显示的数据输入到solr仓库中
    @Override
    public void initDataToSolr() {
        List<TProduct> tProducts = tProductMapper.selectAll();
        List<SolrInputDocument> docs = new ArrayList<>();

        for (TProduct tProduct : tProducts) {
            SolrInputDocument doc = new SolrInputDocument();
            //封装
            doc.setField("id", tProduct.getPid());
            doc.setField("pname", tProduct.getPname());
            doc.setField("prices", tProduct.getPrice().doubleValue());
            doc.setField("pimage", tProduct.getPimage());
            docs.add(doc);
        }
        try {
            solrClient.add(docs);
            solrClient.commit(); //提交到solr
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //通过关键字查询。返回结果集
    @Override
    public List<TProduct> searchProduct(String keywords) {
        //通过solrClient执行查询
        //1.设置查询参数，封装到SolrQuery对象中
        SolrQuery query = new SolrQuery();
        query.setQuery(keywords);
        query.set("df", "t_item_keywords");
        query.setStart(0);
        query.setRows(10);
        //高亮
        query.setHighlight(true);
        query.addHighlightField("pname");
        query.setHighlightSimplePre("<span style='color:red'>");
        query.setHighlightSimplePost("</span>");
        List<TProduct> products = new ArrayList<>();
        try {
            QueryResponse response = solrClient.query(query);
            //获得所有商品数据
            SolrDocumentList results = response.getResults();
            //获得所有高亮数据
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            for (SolrDocument doc : results) {
                String id_str = (String) doc.getFieldValue("id");
                Long pid = Long.parseLong(id_str);
                TProduct product = new TProduct();
                product.setPid(pid);
                //==高亮==
                Map<String, List<String>> stringListMap = highlighting.get(id_str);
                List<String> pnames = stringListMap.get("pname");
                String pname = pnames.get(0);
                product.setPname(pname);
                //==高亮==

                Double price = (Double) doc.getFieldValue("prices");
                product.setPrice(new BigDecimal(price));

                String pimage = (String) doc.getFieldValue("pimage");
                product.setPimage(pimage);

                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

}
