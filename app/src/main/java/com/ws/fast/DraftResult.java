package com.ws.fast;

import java.util.List;

public class DraftResult {


    private int total_page;
    private int total_count;
    private int curr_page;
    private int page_size;
    private List<ListBean> list;

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

    public int getCurr_page() {
        return curr_page;
    }

    public void setCurr_page(int curr_page) {
        this.curr_page = curr_page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private String draft_id;
        private String title;
        private String text;
        private int length;
        private int hots;
        private int sort_by;
        private String create_time;
        private List<TagsBean> tags;

        public String getDraft_id() {
            return draft_id;
        }

        public void setDraft_id(String draft_id) {
            this.draft_id = draft_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getHots() {
            return hots;
        }

        public void setHots(int hots) {
            this.hots = hots;
        }

        public int getSort_by() {
            return sort_by;
        }

        public void setSort_by(int sort_by) {
            this.sort_by = sort_by;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * tag_id : 2000
             * tag_name : 超市促销
             */

            private String tag_id;
            private String tag_name;

            public String getTag_id() {
                return tag_id;
            }

            public void setTag_id(String tag_id) {
                this.tag_id = tag_id;
            }

            public String getTag_name() {
                return tag_name;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }
        }
    }
}
