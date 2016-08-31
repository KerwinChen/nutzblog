package net.javablog.util;


public class FrontPagerUT {

    /**
     * 返回的是分页部分的html内容  <br>
     * offset 可以设置的范围是>0即可
     * curr_page是从1开始的
     *
     * @param allCount      分页前的根据搜索条件得到的总数量
     * @param curr_page     要请求第几页的内容
     * @param pageurl       url的通用地址[要携带可能有的查询参数]
     * @param pageno_offset
     * @return
     */
    public static String pages(int pagesize, int allCount, int curr_page, String pageurl, int pageno_offset) {

        //总页数
        int pagenums = allCount % pagesize == 0 ? allCount / pagesize : allCount / pagesize + 1;

        if (pagenums <= 1) {
            return "";
        }
        String multipage = new String();

        if (pageno_offset < 0) {
            pageno_offset = 0;
        }
        int offset = pageno_offset;//当前连接左右的个数   【】


        //+1是当前页面，+2是第一页和最后一页
        int fullsize = 2 * offset + 1 + 2;//最全状态下要显示的数量，两个省略号中的数量+2=11

        int from = curr_page - offset;
        int to = curr_page + offset;

        //确定from和to的位置
        if (fullsize >= pagenums) {
            from = 2;
            to = pagenums - 1;
        } else {
            if (from <= 1) {
                to = fullsize - 1;
                from = 2;
            } else if (to >= pagenums) {
                from = pagenums - (fullsize - 2);
                to = pagenums - 1;
            }
        }

        // 上一页连接和第一页的连接
        if (curr_page > 0) {
            int perpage = curr_page == 1 ? 1 : curr_page - 1;
            if (curr_page == 1) {
                multipage += "<a class='previous_page disabled' href=" + String.format(pageurl, perpage) + ">上一页</a>  ";
                multipage += "<a class='page active'>1</a>";
            } else {
                multipage += "<a class='previous_page' href=" + String.format(pageurl, perpage) + ">上一页</a>  ";
                multipage += " <a class='page' href=" + String.format(pageurl, "1") + ">1</a>  ";
            }
        }

        //两个省略号中间的部分
        for (int i = from; i <= to; i++) {
            if (i != curr_page) {
                multipage += " <a class='page' href=" + String.format(pageurl, i) + ">" + i + "</a>  ";
            } else {
                multipage += " <a class='page active'>" + curr_page + "</a>  ";
            }
        }

        //最后的链接
        if (curr_page < pagenums) {
            multipage += " <a class='page' href=" + String.format(pageurl, pagenums) + ">" + pagenums + "</a> ";
            multipage += "  <a class='next_page' href=" + String.format(pageurl, curr_page + 1) + ">下一页</a>  ";
        } else if (curr_page == pagenums) {
            multipage += " <a class='page active' href=" + String.format(pageurl, pagenums) + ">" + pagenums + "</a> ";
            multipage += " <a class='next_page  disabled' href=" + String.format(pageurl, curr_page) + ">下一页</a>";
        }
        return multipage;
    }


}
