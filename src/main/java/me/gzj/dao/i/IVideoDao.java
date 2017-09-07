package me.gzj.dao.i;

import me.gzj.entity.dao.Video;

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
}
