package me.gzj.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity(value = "video", noClassnameStored = true)
@Indexes({
    @Index(fields = {@Field(value = "viewkey")}, options = @IndexOptions(unique = true))
})
public class Video {
    /**
     * ID
     */
    @Id
    private ObjectId id;

    /**
     * 视频ID
     */
    private String viewkey;
    /**
     * 标题
     */
    private String title;
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
     * 下载次数
     */
    private Integer downloads;
    /**
     * 视频是否消失
     */
    private Boolean miss;
    /**
     * 更新时间（秒）
     */
    @Property("update_time")
    private Integer updateTime;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getViewkey() {
        return viewkey;
    }

    public void setViewkey(String viewkey) {
        this.viewkey = viewkey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}
