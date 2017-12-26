package com.example.aiyang.allhttp_networkrequestdemo.model;

import java.util.List;

/**
 * Created by aiyang on 2017/5/17.
 */

public class GankBean {

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 59197e87421aa92c7be61afe
         * createdAt : 2017-05-15T18:10:15.604Z
         * desc : Android灵魂画家的18种混合模式
         * publishedAt : 2017-05-16T12:10:38.580Z
         * source : web
         * type : Android
         * url : https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247484408&idx=1&sn=cd077ffe234b15c6c8a193def53fc963&chksm=96cda2b5a1ba2ba387c910df7b864580f6f5858691bcecb46afe2b8a91684ab804879f01d905&mpshare=1&scene=23&srcid=0515GJX6SNr5N7p6B7bkqG1y#rd
         * used : true
         * who : 陈宇明
         * images : ["http://img.gank.io/8c728347-b326-4d41-b7b5-f8e748e65db0"]
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
