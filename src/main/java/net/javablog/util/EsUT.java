package net.javablog.util;


import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import net.javablog.bean.tb_singlepage;
import org.apache.lucene.search.Explanation;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EsUT {


    private static final String PWDKEY = "X-SCE-ES-PASSWORD";
    private static Logger logger = LoggerFactory.getLogger(EsUT.class);

    private static JestClient client;
    private static String indices = "db_javacore";
    private static String types = "tb_singlepage";
    private static String dateStyle = "yyyy-MM-dd HH:mm:ss";

    static {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://195.154.164.248:9200")
//                .Builder("http://23.88.110.58:9200")
                .gson(new GsonBuilder().setDateFormat(dateStyle).create())
                .discoveryEnabled(false).build());
        client = (JestHttpClient) factory.getObject();
    }

    public static void createIndex(String json, String id) {
        try {
            if (client != null) {
                Index index = new Index.Builder(json).index(indices).type(types).id(id).build();
                JestResult result = client.execute(index);
                System.out.println(result.getJsonString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 把从es中查询到的内容转换到stringbuilder里
     *
     * @param q
     * @return allcount ,datas
     */
    public static Map search(String q, int pagesize, int pageno) {

        Map finalOut = new HashMap();
        List out = new ArrayList();
        //minpage
        //page
        //datas
        String allcount = "";
        try {

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(q, "_title", "_content_text"))
                    .from(pageno - 1)
                    .size(pagesize)
//                    .minScore(0.05F)
                    .highlight(new HighlightBuilder().order("score").field("_content_text").preTags("[tag1]").postTags("[/tag1]")); //.postTags("<tag1>").postTags("</tag1>"));

            Search search = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex(indices)
                    .addType(types)
                    .build();
            SearchResult result = client.execute(search);


            List<SearchResult.Hit<tb_singlepage, Explanation>> hits = result.getHits(tb_singlepage.class, Explanation.class);
//            SearchResult.Hit<tb_singlepage, Explanation> hits = result.getFirstHit(tb_singlepage.class, Explanation.class);
            allcount = result.getTotal() + "";


                for (int i = 0; i < hits.size(); i++) {
                SearchResult.Hit<tb_singlepage, Explanation> item = hits.get(i);

                Map m = new HashMap();
                m.put("title", item.source.get_title());
                m.put("tag", item.source.get_tags());
                m.put("href", "/page" + (item.source.get_index_inseris() == 0 ? "" : "s") + "/" + item.source.getCopy_id() + "/" + item.source.get_titleen() + ".html");
                m.put("time", Times.format("yyyy-MM-dd", item.source.getUt()));
                if (item.highlight != null) {
                    m.put("desc", JsoupBiz.getTextFromTHML(item.highlight.get("_content_text").get(0)));
                } else {
                    m.put("desc", item.source.get_title());
                }

                m.put("desc", m.get("desc").toString().replace("[tag1]", "<em>").replace("[/tag1]", "</em>").replace("<!--", "").replace("-->", ""));
                System.out.println(m.get("title"));
                System.out.println(m.get("href"));
                System.out.println("=====" + i + "=======");
                out.add(m);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        finalOut.put("allcount", Strings.isBlank(allcount) ? "0" : allcount);
        finalOut.put("datas", out);
        return finalOut;
    }

    public static void main(String[] args) {

        Map out = search("java map", 6, 1);

        System.out.println(out);
    }


    public static void delIndex(int copyid) {
        try {
            client.execute(new Delete.Builder(copyid + "")
                    .index(indices)
                    .type(types)
                    .build());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
