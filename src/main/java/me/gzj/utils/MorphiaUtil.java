package me.gzj.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MorphiaUtil {
    private final static Datastore datastore;

    public static Datastore getDatastore() {
        return datastore;
    }

    static {
        final Morphia morphia = new Morphia();

        // tell Morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("me.gzj.entity");

        // create the Datastore connecting
        MongoClient mongoClient = new MongoClient(new MongoClientURI(ConfigUtil.MongoUri()));
        datastore = morphia.createDatastore(mongoClient, ConfigUtil.MongoDatabaseName());
        datastore.ensureIndexes();
    }
}
