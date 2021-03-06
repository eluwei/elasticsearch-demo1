package com.liqihua.demo.restful;

import com.liqihua.config.ESConfig;
import com.liqihua.utils.Tool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;


/**
 * @author liqihua
 * @since 2018/5/4
 */
public class QueryDemo {



    /**
     * matchAll查询
     * {"query":{"match_all":{}}}
     */
    @Test
    public void match_all(){
        //索引名，如果值为""，则查出所有index的数据
        String index = "index_1";
        //索引名还能使用通配符
        //String index = "xxx*";

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject match_all = new JSONObject();
        query.element("match_all",match_all);
        json.element("query",query);

        String url = ESConfig.URL+"/"+index+"/_search";
        Tool.post(json.toString(),url);
    }


    /**
     * 排序
     * {"query":{"match_all":{}},"sort":{"create_on":{"order":"desc"}}}
     */
    @Test
    public void match_all_sort(){
        String index = "index_1";

        JSONObject data = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject match_all = new JSONObject();
        JSONObject sort = new JSONObject();
        JSONObject order = new JSONObject();
        order.element("order","desc");
        sort.element("create_on",order);
        query.element("match_all",match_all);
        data.element("query",query);
        data.element("sort",sort);

        String url = ESConfig.URL+"/"+index+"/_search";
        Tool.post(data.toString(),url);
    }


    /**
     * match查询
     * 查询匹配就会进行分词，比如"宝马多少马力"会被分词为"宝马 多少 马力", 所有有关"宝马 多少 马力", 那么所有包
     * 含这三个词中的一个或多个的文档就会被搜索出来，文档"我的保时捷马力不错"也会被搜索出来
     * {"query":{"match":{"product_type":{"query":"新款系带气垫缓震运动鞋"}}}}
     */
    @Test
    public void match(){
        String index = "index_1";
        String field = "product_name";
        String keyword = "新款系带气垫缓震运动鞋";

        JSONObject data = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject match = new JSONObject();
        JSONObject content = new JSONObject();
        content.element("query",keyword);
        match.element(field,content);
        query.element("match",match);
        data.element("query",query);

        String url = ESConfig.URL+"/"+index+"/_search";
        Tool.post(data.toString(),url);
    }



    /**
     * match_phrase查询
     * 在match中"宝马多少马力"会被分词为"宝马 多少 马力"，"我的保时捷马力不错"也会被搜索出来，
     * 想要精确匹配所有同时包含"宝马 多少 马力"的文档，就需要使用match_phrase
     * {"query":{"match_phrase":{"product_name":{"query":"Nike 新款系带气垫缓震运动鞋"}}}}
     */
    @Test
    public void match_phrase(){
        String index = "index_1";
        String field = "product_name";
        String keyword = "Nike 新款系带气垫缓震运动鞋";

        JSONObject data = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject match_phrase = new JSONObject();
        JSONObject content = new JSONObject();
        content.element("query",keyword);
        match_phrase.element(field,content);
        query.element("match_phrase",match_phrase);
        data.element("query",query);

        String url = ESConfig.URL+"/"+index+"/_search";
        Tool.post(data.toString(),url);

    }



    /**
     * multi_match查询
     * 将文本或短语与多个字段匹配
     * 查询create_by或op_by符合关键词的doc
     * {"query":{"multi_match":{"query":"运动鞋","fields":["product_type","product_name"]}}}
     */
    @Test
    public void multi_match(){
        String index = "index_1";
        String[] fields = new String[]{"product_type","product_name"};
        String keyword = "运动鞋";

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject multi_match = new JSONObject();
        multi_match.element("query",keyword);
        multi_match.element("fields",fields);
        query.element("multi_match",multi_match);
        json.element("query",query);

        String url = ESConfig.URL+"/"+index+"/_search";
        Tool.post(json.toString(),url);
    }



    /**
     * term查询
     * term是代表完全匹配，即不进行分词器分析，文档中必须包含整个搜索的词汇
     * {"query":{"term":{"product_type":"护肤"}}}
     */
    @Test
    public void term(){
        String index = "index_1";
        String field = "product_sku";
        String value = "151160010137";

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject term = new JSONObject();
        term.element(field,value);
        query.element("term",term);
        json.element("query",query);

        String url = ESConfig.URL+"/"+index+"/_search";
        Tool.post(json.toString(),url);
    }




    /**
     * query_string
     * 关键字查询
     * {"query":{"query_string":{"query":"新款"}}}
     */
    @Test
    public void query_string(){
        String index = "index_1";
        String keyword = "新款";

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject query_string = new JSONObject();
        query_string.element("query",keyword);
        query.element("query_string",query_string);
        json.element("query",query);

        String url = ESConfig.URL+"/"+index+"/_search";
        Tool.post(json.toString(),url);
    }





    /**
     * 范围查询
     * 此查询用于查找值的范围之间的值的对象。 为此，需要使用类似 -
     * gte − 大于和等于
     * gt − 大于
     * lte − 小于和等于
     * lt − 小于
     * {"query":{"range":{"product_price":{"gte":"2000"}}}}
     */
    @Test
    public void rangeByPrice(){
        String index = "index_ee";//索引名
        String field = "product_price";//字段名

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject fieldJson = new JSONObject();
        JSONObject scope = new JSONObject();
        scope.element("gte","2000");
        fieldJson.element(field,scope);
        query.element("range",fieldJson);
        json.element("query",query);

        String url = ESConfig.URL+"/"+index+"/_search";

        Tool.post(json.toString(),url);
    }


    /**
     *
     */
    @Test
    public void rangeByDate(){
        String index = "index_aa";//索引名
        String field = "create_on";//字段名

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject fieldJson = new JSONObject();
        JSONObject scope = new JSONObject();
        scope.element("gt","2017-06-01 16:59:14");
        fieldJson.element(field,scope);
        query.element("range",fieldJson);
        json.element("query",query);

        String url = ESConfig.URL+"/"+index+"/_search";

        Tool.post(json.toString(),url);
    }


    /**
     * 类型查询 - 具有特定类型的文档
     */
    @Test
    public void type(){
        //类型
        String type = "pm_stage_product";
        //索引名
        String index = "index_pm_stage_product";

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject value = new JSONObject();
        value.element("value",type);
        query.element("type",value);
        json.element("query",query);

        String url = ESConfig.URL+"/"+index+"/_search";

        System.out.println(url);
        System.out.println(json.toString());

        Tool.post(json.toString(),url);
    }










    @Test
    public void convert(){
        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject match_all = new JSONObject();
        query.element("match_all",match_all);
        json.element("query",query);

        //索引名，如果值为""，则查出所有index的数据
        String index = "index_pm_stage_product";
        //索引名还能使用通配符
        //String index = "index_pm_stage_product*";

        String url = ESConfig.URL+"/"+index+"/_search";

        System.out.println(url);
        System.out.println(json.toString());

        String result = Tool.post(json.toString(),url);
        JSONObject resultJson = JSONObject.fromObject(result);
        JSONObject hits = resultJson.getJSONObject("hits");
        JSONArray hitsArr = hits.getJSONArray("hits");
        System.out.println(hitsArr.toString());
        for(int i=0; i<hitsArr.size(); i++){
            boolean update = false;
            JSONObject product = hitsArr.getJSONObject(i).getJSONObject("_source");
            String product_price = product.getString("product_price");
            if(Tool.isNumber(product_price)){
                product.element("product_price",Integer.parseInt(product_price));
                update = true;
            }
            if(Tool.isDouble(product_price)){
                product.element("product_price",Double.parseDouble(product_price));
                update = true;
            }
            if(update){
                String id = hitsArr.getJSONObject(i).getString("_id");
                System.out.println("update:"+product.toString());
                //String updateResult = Tool.put(product.toString(),URL+"/"+index+"/pm_stage_product/"+id);
                //System.out.println("updateResult:"+updateResult);
            }
        }
        System.out.println(hitsArr.toString());

    }



}
