package me.gzj.dao.impl;

import me.gzj.dao.i.IVideoDao;
import me.gzj.entity.Video;
import me.gzj.utils.MorphiaUtil;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.stereotype.Repository;

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
        if (video.getTitle() != null) {
            update.set("title", video.getTitle());
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
        if (video.getDownloads() != null) {
            update.set("downloads", video.getDownloads());
        }
        if (video.getMiss() != null) {
            update.set("miss", video.getMiss());
        }
        update.set("update_time", (int) (System.currentTimeMillis() / 1000));
        UpdateResults result = datastore.update(query, update, true);
        return result.getUpdatedExisting() ? result.getUpdatedCount() : result.getInsertedCount();
    }
}
