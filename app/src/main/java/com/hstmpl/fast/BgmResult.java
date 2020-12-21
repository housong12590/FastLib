package com.hstmpl.fast;

import java.util.List;

public class BgmResult {


    /**
     * page_size : 15
     * curr_page : 1
     * total_page : 10
     * total_count : 149
     * list : [{"duration":199,"bgm_name":"节奏控-劲舞团","class_id":"46550854026792960","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/242553390b5d44cea327a230d0bd7607.mp3","bgm_id":"80424303992115200"},{"duration":162,"bgm_name":"童欢同乐","class_id":"46597099088908288","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/3059b7de6d8d4c27a5aceed406227630.mp3","bgm_id":"80424317090926592"},{"duration":202,"bgm_name":"动感浪漫音-浪漫情人节 (伴奏)","class_id":"46597044911083520","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/7823a4a4ebb74ee38d60dccb8142a1e1.mp3","bgm_id":"80424312322002944"},{"duration":155,"bgm_name":"轻音乐-Summer(菊次郎的夏天)","class_id":"46596996756279296","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/b926e9efd2b04838a1a63d91247550fd.mp3","bgm_id":"80424309516013568"},{"duration":71,"bgm_name":"大气专题音1","class_id":"47327253784301568","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/1ab9a5838822426e872b2a7ce3cef71a.mp3","bgm_id":"80424317908815872"},{"duration":199,"bgm_name":"节奏控-劲舞团","class_id":"46596939302703104","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/242553390b5d44cea327a230d0bd7607.mp3","bgm_id":"80424305896329216"},{"duration":204,"bgm_name":"节奏控-梦想时光","class_id":"46550854026792960","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/dce504e7b3ef4874b1b4f975cef43ec1.mp3","bgm_id":"80424304105361408"},{"duration":472,"bgm_name":"大气专题音2","class_id":"47327253784301568","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/f65b91af62a34eb7a02c69908f6d2999.mp3","bgm_id":"80424318017867776"},{"duration":61,"bgm_name":"轻音乐-告白の夜(唯美钢琴版)","class_id":"46596996756279296","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/27f136d89595455c9b09133338418bde.mp3","bgm_id":"80424309629259776"},{"duration":151,"bgm_name":"Good Plan","class_id":"46597099088908288","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/09bfc9010d394814ba21d6a022b43c16.mp3","bgm_id":"80424317191589888"},{"duration":267,"bgm_name":"动感浪漫音-第一次的情人节(伴奏版)","class_id":"46597044911083520","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/2f8bbc983278412bae736a4505b9b680.mp3","bgm_id":"80424312435249152"},{"duration":204,"bgm_name":"节奏控-梦想时光","class_id":"46596939302703104","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/dce504e7b3ef4874b1b4f975cef43ec1.mp3","bgm_id":"80424306001186816"},{"duration":107,"bgm_name":"In The Country","class_id":"46597099088908288","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/65dd568cc8514de498b799f543d37c98.mp3","bgm_id":"80424317296447488"},{"duration":286,"bgm_name":"动感浪漫音-情人节的礼物 (伴奏)","class_id":"46597044911083520","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/59e0396ba1894f86805ebfff490fbf65.mp3","bgm_id":"80424312531718144"},{"duration":254,"bgm_name":"节奏控-冠军舞曲","class_id":"46596939302703104","bgm_url":"http://cdn-tts.ciinweb.com/tts/pro/bgm/05215ab8073f4a9697802dc6d646fa54.mp3","bgm_id":"80424306101850112"}]
     */

    private int page_size;
    private int curr_page;
    private int total_page;
    private int total_count;
    private List<ListBean> list;

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getCurr_page() {
        return curr_page;
    }

    public void setCurr_page(int curr_page) {
        this.curr_page = curr_page;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * duration : 199
         * bgm_name : 节奏控-劲舞团
         * class_id : 46550854026792960
         * bgm_url : http://cdn-tts.ciinweb.com/tts/pro/bgm/242553390b5d44cea327a230d0bd7607.mp3
         * bgm_id : 80424303992115200
         */

        private int duration;
        private String bgm_name;
        private String class_id;
        private String bgm_url;
        private String bgm_id;

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getBgm_name() {
            return bgm_name;
        }

        public void setBgm_name(String bgm_name) {
            this.bgm_name = bgm_name;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getBgm_url() {
            return bgm_url;
        }

        public void setBgm_url(String bgm_url) {
            this.bgm_url = bgm_url;
        }

        public String getBgm_id() {
            return bgm_id;
        }

        public void setBgm_id(String bgm_id) {
            this.bgm_id = bgm_id;
        }
    }
}
