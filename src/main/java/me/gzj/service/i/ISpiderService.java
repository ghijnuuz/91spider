package me.gzj.service.i;

import me.gzj.entity.service.ServiceResult;
import me.gzj.entity.service.VideoInfo;

import java.util.List;

public interface ISpiderService {
    /**
     * 获取视频信息列表
     * @param page 页码
     * @return
     */
    ServiceResult<List<VideoInfo>> getVideoInfoList(int page);

    /**
     * 获取视频详细信息
     * @param viewkey
     * @return
     * @throws Exception
     */
    ServiceResult<VideoInfo> getVideoInfo(String viewkey);

    /**
     * 获取所有视频信息，并保存到数据库
     * @return
     */
    ServiceResult<Integer> getAllVideoAndSaveToDatabase();
}
