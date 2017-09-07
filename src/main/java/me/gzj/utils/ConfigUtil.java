package me.gzj.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.PropertiesBuilderParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ghijnuuz on 2017/7/31.
 */
public class ConfigUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

    private final static Configuration CONFIG;

    static  {
        Configuration configuration = null;
        try {
            Parameters params = new Parameters();
            PropertiesBuilderParameters builderParams = params.properties().setFileName("config.properties").setEncoding("UTF-8");
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class).configure(builderParams);
            configuration = builder.getConfiguration();
        } catch (Exception ex) {
            LOGGER.error("init error", ex);
        }
        CONFIG = configuration;
    }

    //region MongoDB
    public static String MongoUri() {
        return CONFIG.getString("MongoUri");
    }

    public static String MongoDatabaseName() {
        return CONFIG.getString("MongoDatabaseName");
    }
    //endregion

    //region Site
    public static String SiteHost() {
        return CONFIG.getString("SiteHost");
    }

    public static String SiteVideoPath() {
        return CONFIG.getString("SiteVideoPath");
    }

    public static String SiteVideoListPath() {
        return CONFIG.getString("SiteVideoListPath");
    }
    //endregion
}
