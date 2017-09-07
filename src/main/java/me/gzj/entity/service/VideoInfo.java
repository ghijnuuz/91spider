package me.gzj.entity.service;

import java.util.Date;

public class VideoInfo {
    /**
     * 视频ID
     */
    private String viewkey;
    /**
     * 视频名称
     */
    private String name;
    /**
     * 时长
     */
    private String runtime;
    /**
     * 观看次数
     */
    private Integer views;
    /**
     * 留言数量
     */
    private Integer comments;
    /**
     * 收藏次数
     */
    private Integer favorites;
    /**
     * 积分
     */
    private Integer point;
    /**
     * 添加日期
     */
    private Date addDate;
    /**
     * 下载地址
     */
    private String downloadUrl;

    public String getViewkey() {
        return viewkey;
    }

    public void setViewkey(String viewkey) {
        this.viewkey = viewkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
