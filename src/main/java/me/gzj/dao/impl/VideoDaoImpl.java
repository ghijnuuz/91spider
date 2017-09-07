package me.gzj.dao.impl;

import me.gzj.dao.i.IVideoDao;
import me.gzj.entity.dao.Video;
import me.gzj.utils.MorphiaUtil;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class VideoDaoImpl implements IVideoDao {
    private final Datastore datastore = MorphiaUtil.getDatastore();

    /**
     * 获取视频
     * @param viewkey
     * @return
     */
    public Video getVideo(String viewkey) {
        return datastore.createQuery(Video.class).field(Mapper.ID_KEY).equal(viewkey).get();
    }

    /**
     * 保存视频
     * @param video
     * @return
     */
    public int saveVideo(Video video) {
        Query<Video> query = datastore.createQuery(Video.class).field(Mapper.ID_KEY).equal(video.getViewkey());
        UpdateOperations<Video> update = datastore.createUpdateOperations(Video.class);
        if (video.getName() != null) {
            update.set("name", video.getName());
        }
        if (video.getRuntime() != null) {
            update.set("runtime", video.getRuntime());
        }
        if (video.getViews() != null) {
            update.set("views", video.getViews());
        }
        if (video.getComments() != null) {
            update.set("comments", video.getComments());
        }
        if (video.getFavorites() != null) {
            update.set("favorites", video.getFavorites());
        }
        if (video.getPoint() != null) {
            update.set("point", video.getPoint());
        }
        if (video.getAddDate() != null) {
            update.set("add_date", video.getAddDate());
        }
        if (video.getDownloads() != null) {
            update.set("downloads", video.getDownloads());
        }
        if (video.getMiss() != null) {
            update.set("miss", video.getMiss());
        }
        update.set("update_time", new Date());
        UpdateResults result = datastore.update(query, update, true);
        return result.getUpdatedExisting() ? result.getUpdatedCount() : result.getInsertedCount();
    }

    /**
     * 获取未下载、观看次数最多的视频信息列表
     * @param limit
     * @return
     */
    public List<Video> getNotDownloadMaxViewVideoList(int limit) {
        Query<Video> query = datastore.createQuery(Video.class);
        query.field("miss").notEqual(true);
        query.or(
                query.criteria("downloads").doesNotExist(),
                query.criteria("downloads").equal(0)
        );
        query.order(Sort.descending("views"));
        return query.asList(new FindOptions().limit(limit));
    }
}
