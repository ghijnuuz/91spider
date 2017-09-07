package me.gzj.entity.dao;

import org.mongodb.morphia.annotations.*;

import java.util.Date;

@Entity(value = "video", noClassnameStored = true)
@Indexes({
    @Index(fields = {@Field(value = "viewkey")})
})
public class Video {
    /**
     * 视频ID
     */
    @Id
    private String viewkey;
    /**
     * 视频名称
     */
    @Property("name")
    private String name;
    /**
     * 时长
     */
    @Property("runtime")
    private String runtime;
    /**
     * 观看次数
     */
    @Property("views")
    private Integer views;
    /**
     * 留言数量
     */
    @Property("comments")
    private Integer comments;
    /**
     * 收藏次数
     */
    @Property("favorites")
    private Integer favorites;
    /**
     * 积分
     */
    @Property("point")
    private Integer point;
    /**
     * 添加日期
     */
    @Property("add_date")
    private Date addDate;

    /**
     * 下载次数
     */
    @Property("downloads")
    private Integer downloads;
    /**
     * 视频是否消失
     */
    @Property("miss")
    private Boolean miss;
    /**
     * 更新时间
     */
    @Property("update_time")
    private Date updateTime;

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

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Boolean getMiss() {
        return miss;
    }

    public void setMiss(Boolean miss) {
        this.miss = miss;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
