package me.gzj.dao;

import me.gzj.configuration.AppConfiguration;
import me.gzj.dao.i.IVideoDao;
import me.gzj.entity.Video;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
public class VideoDaoTest {
    @Autowired
    private IVideoDao videoDao;

    @Test
    public void t1_saveVideo() {
        Video video = new Video();
        video.setViewkey("viewkey1");
        video.setTitle("title1");
        int result = videoDao.saveVideo(video);
        Assert.assertEquals(1, result);
    }

    @Test
    public void t2_getVideo() {
        Video video = videoDao.getVideo("viewkey1");
        Assert.assertNotNull(video);
        Assert.assertEquals("title1", video.getTitle());
    }
}
