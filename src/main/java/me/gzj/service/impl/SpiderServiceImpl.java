package me.gzj.service.impl;

import me.gzj.dao.i.IVideoDao;
import me.gzj.entity.dao.Video;
import me.gzj.entity.service.ServiceResult;
import me.gzj.entity.service.VideoInfo;
import me.gzj.service.i.ISpiderService;
import me.gzj.utils.ConfigUtil;
import me.gzj.utils.JsonUtil;
import me.gzj.utils.http.HttpUtil;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

@Service
public class SpiderServiceImpl implements ISpiderService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int pageSize = 20;

    @Autowired
    private IVideoDao videoDao;

    /**
     * 获取字符串头部的数字
     * @param str
     * @return
     */
    private Integer getIntFromString(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        str = str.trim();
        int numberCount = 0;
        for (int i = 0; i < str.length(); i++) {
            if (i == 0 && str.charAt(i) == '-') {
                numberCount++;
            }
            if (Character.isDigit(str.charAt(i))) {
                numberCount++;
            }
        }
        if (numberCount <= 0) {
            return null;
        }
        Integer result = null;
        try {
            String numberStr = str.substring(0, numberCount);
            result = Integer.parseInt(numberStr);
        } catch (Exception ex) {
            // ignore
        }
        return result;
    }

    /**
     * 获取视频信息列表
     * @param page 页码
     * @return
     */
    public ServiceResult<List<VideoInfo>> getVideoInfoList(int page) {
        if (page <= 0) {
            return ServiceResult.createParameterErrorResult();
        }

        try {
            String pageUrl = String.format("%s%s", ConfigUtil.SiteHost(), ConfigUtil.SiteVideoListPath());
            Map<String, String> param = new LinkedHashMap<String, String>() {{
                put("next", "watch");
                put("page", String.valueOf(page));
            }};
            URL url = HttpUtil.getUrl(pageUrl, param);

            try (Response response = HttpUtil.get(url)) {
                if (!response.isSuccessful()) {
                    return ServiceResult.createFailResult();
                }

                Document document = Jsoup.parse(response.body().string());
                List<VideoInfo> videoInfoList = new ArrayList<>();

                Elements elementsVideo = document.select("#videobox .listchannel");
                if (!elementsVideo.isEmpty()) {
                    for (Element element : elementsVideo) {
                        VideoInfo videoInfo = new VideoInfo();

                        Elements elementsLink = element.select("a");
                        if (!elementsLink.isEmpty()) {
                            Element elementLink = elementsLink.first();

                            HttpUrl httpUrl = HttpUrl.parse(elementLink.attr("href").trim());
                            if (httpUrl == null) {
                                break;
                            }
                            String viewkey = httpUrl.queryParameter("viewkey");
                            if (StringUtils.isNotEmpty(viewkey)) {
                                videoInfo.setViewkey(viewkey);
                            }

                            Elements elementsName = elementsLink.select("img");
                            String name = elementsName.attr("title").trim();
                            if (StringUtils.isNotEmpty(name)) {
                                videoInfo.setName(name);
                            }
                        }

                        Elements elementsInfo = element.select(".info");
                        if (elementsInfo.size() >= 7) {
                            String runtime = elementsInfo.get(0).nextSibling().toString().trim();
                            if (StringUtils.isNotEmpty(runtime)) {
                                videoInfo.setRuntime(runtime);
                            }
                            Integer views = getIntFromString(elementsInfo.get(3).nextSibling().toString());
                            videoInfo.setViews(views);
                            Integer favorites = getIntFromString(elementsInfo.get(4).nextSibling().toString());
                            videoInfo.setFavorites(favorites);
                            Integer comments = getIntFromString(elementsInfo.get(5).nextSibling().toString());
                            videoInfo.setComments(comments);
                            Integer point = getIntFromString(elementsInfo.get(6).nextSibling().toString());
                            videoInfo.setPoint(point);
                        }

                        videoInfoList.add(videoInfo);
                    }
                }

                return ServiceResult.createSuccessResult(videoInfoList);
            }
        } catch (Exception ex) {
            logger.error(String.format("get video info list error, page:%d", page), ex);
        }

        return ServiceResult.createFailResult();
    }

    /**
     * 获取视频详细信息
     * @param viewkey
     * @return
     * @throws Exception
     */
    public ServiceResult<VideoInfo> getVideoInfo(String viewkey) {
        if (StringUtils.isEmpty(viewkey)) {
            return ServiceResult.createParameterErrorResult();
        }

        try {
            String pageUrl = String.format("%s%s", ConfigUtil.SiteHost(), ConfigUtil.SiteVideoPath());
            Map<String, String> param = new LinkedHashMap<String, String>() {{
                put("viewkey", viewkey);
            }};
            URL url = HttpUtil.getUrl(pageUrl, param);

            try (Response response = HttpUtil.get(url)) {
                if (!response.isSuccessful()) {
                    return ServiceResult.createFailResult();
                }

                if (!response.request().url().url().equals(url)) {
                    return ServiceResult.create(false, -1000, "视频不存在");
                }

                Document document = Jsoup.parse(response.body().string());
                VideoInfo videoInfo = new VideoInfo();
                videoInfo.setViewkey(viewkey);

                Elements elementsName = document.select("#viewvideo-title");
                if (!elementsName.isEmpty()) {
                    String name = elementsName.text().trim();
                    if (StringUtils.isNotEmpty(name)) {
                        videoInfo.setName(name);
                    }
                }

                Elements elementsInfo = document.select("#useraction .info");
                if (elementsInfo.size() >= 5) {
                    String runtime = elementsInfo.get(0).nextSibling().toString().trim();
                    if (StringUtils.isNotEmpty(runtime)) {
                        videoInfo.setRuntime(runtime);
                    }
                    Integer views = getIntFromString(elementsInfo.get(1).nextSibling().toString().trim());
                    videoInfo.setViews(views);
                    Integer comments = getIntFromString(elementsInfo.get(2).nextSibling().toString().trim());
                    videoInfo.setComments(comments);
                    Integer favorites = getIntFromString(elementsInfo.get(3).nextSibling().toString().trim());
                    videoInfo.setFavorites(favorites);
                    Integer point = getIntFromString(elementsInfo.get(4).nextSibling().toString().trim());
                    videoInfo.setPoint(point);
                }

                Elements elementsDay = document.select("#videodetails-content .title");
                if (elementsDay.size() > 2) {
                    try {
                        Date addDate = DateUtils.parseDate(elementsDay.get(1).text().trim(), "yyyy-MM-dd");
                        videoInfo.setAddDate(addDate);
                    } catch (Exception ex) {
                        // ignore
                    }
                }

                Elements elementsVideo = document.select("video source");
                if (!elementsVideo.isEmpty()) {
                    String downloadUrl = elementsVideo.attr("src").trim();
                    if (StringUtils.isNotEmpty(downloadUrl)) {
                        videoInfo.setDownloadUrl(downloadUrl);
                    }
                }

                return ServiceResult.createSuccessResult(videoInfo);
            }
        } catch (Exception ex) {
            logger.error(String.format("get video info %s error", viewkey), ex);
        }

        return ServiceResult.createFailResult();
    }

    /**
     * 获取所有视频信息，并保存到数据库
     * @return
     */
    public ServiceResult<Integer> getAllVideoAndSaveToDatabase() {
        int page = 1;
        boolean hasNextPage = true;
        int saveCount = 0;

        while (hasNextPage) {
            ServiceResult<List<VideoInfo>> videoInfoListResult = getVideoInfoList(page);
            if (!videoInfoListResult.isSuccess()) {
                logger.info(String.format("page %d get video fail", page));
            } else {
                int videoCount = videoInfoListResult.getData().size();
                logger.info(String.format("page %d get %d video", page, videoCount));

                for (VideoInfo videoInfo : videoInfoListResult.getData()) {
                    try {
                        Video video = new Video();
                        PropertyUtils.copyProperties(video, videoInfo);

                        if (StringUtils.equals(video.getViewkey(), videoInfo.getViewkey())) {
                            int saveResult = videoDao.saveVideo(video);
                            if (saveResult > 0) {
                                saveCount++;
                            } else {
                                logger.error(String.format("save video:%s to database fail", JsonUtil.convertObject2String(videoInfo)));
                            }
                        } else {
                            logger.error(String.format("copy video:%s properties fail", JsonUtil.convertObject2String(videoInfo)));
                        }
                    } catch (Exception ex) {
                        logger.error(String.format("save video:%s to database error", JsonUtil.convertObject2String(videoInfo)));
                    }
                }

                if (videoCount < pageSize) {
                    hasNextPage = false;
                } else {
                    page++;
                }
            }
        }

        logger.info(String.format("total get %d page, %d video", page, saveCount));

        return ServiceResult.createSuccessResult(saveCount);
    }

    /**
     * 下载视频
     * @return
     */
    public ServiceResult<Integer> downloadVideo() {
        try {
            int downloadCount = 0;
            List<Video> videoList = videoDao.getNotDownloadMaxViewVideoList(pageSize);
            if (CollectionUtils.isNotEmpty(videoList)) {
                for (Video video : videoList) {
                    ServiceResult<VideoInfo> videoInfoResult = getVideoInfo(video.getViewkey());
                    if (videoInfoResult.isSuccess()) {
                        VideoInfo videoInfo = videoInfoResult.getData();

                        // 更新视频信息
                        Video newVideo = new Video();
                        PropertyUtils.copyProperties(newVideo, videoInfo);
                        if (StringUtils.equals(video.getViewkey(), newVideo.getViewkey())) {
                            videoDao.saveVideo(newVideo);
                        }

                        // 下载视频
                        if (StringUtils.isNotEmpty(videoInfo.getDownloadUrl())) {
                            String filename = String.format("%s_%s_%s.mp4",
                                    DateFormatUtils.format(videoInfo.getAddDate(), "yyyyMMdd"),
                                    video.getViewkey(),
                                    videoInfo.getName());
                            long byteCount = HttpUtil.download(videoInfo.getDownloadUrl(), String.format("%s%s", ConfigUtil.DownloadPath(), filename));

                            // 大于100KB的才是正常视频
                            if (byteCount >= 100 * 1024) {
                                downloadCount++;

                                Video updateVideo = new Video();
                                updateVideo.setViewkey(video.getViewkey());
                                int downloads = video.getDownloads() == null ? 1 : video.getDownloads() + 1;
                                updateVideo.setDownloads(downloads);
                                videoDao.saveVideo(updateVideo);
                            }

                            logger.info(String.format("download %s length %d", filename, byteCount));
                        }
                    } else if (videoInfoResult.getCode() == -1000) {
                        // 视频不存在
                        Video updateVideo = new Video();
                        updateVideo.setViewkey(video.getViewkey());
                        updateVideo.setMiss(true);
                        videoDao.saveVideo(updateVideo);
                        logger.info(String.format("video %s miss", video.getViewkey()));
                    }
                }
            }

            logger.info(String.format("download %d video", downloadCount));
            return ServiceResult.createSuccessResult(downloadCount);
        } catch (Exception ex) {
            logger.error("downlaod video error", ex);
        }
        return ServiceResult.createFailResult();
    }
}
