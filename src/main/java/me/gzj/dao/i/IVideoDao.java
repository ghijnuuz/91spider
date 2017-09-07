package me.gzj.dao.i;

import me.gzj.entity.dao.Video;

import java.util.List;

public interface IVideoDao {
    /**
     * 获取视频
     * @param viewkey
     * @return
     */
    Video getVideo(String viewkey);

    /**
     * 保存视频
     * @param video
     * @return
     */
    int saveVideo(Video video);

    /**
     * 获取未下载、观看次数最多的视频信息列表
     * @param limit
     * @return
     */
    List<Video> getNotDownloadMaxViewVideoList(int limit);
}
