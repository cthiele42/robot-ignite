package org.roboscratch.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.roboscratch.ignite.keywords.CacheKeywords;
import org.roboscratch.ignite.keywords.ClusterKeywords;

/**
 * Created by cthiele on 02.06.16.
 */
public class CacheKeywordsTest {
    private static ClusterKeywords clusterKeywordsKeywords;
    private static CacheKeywords cacheKeywords;

    @BeforeClass
    public static void setup() throws Exception {
        clusterKeywordsKeywords = new ClusterKeywords();
        cacheKeywords = new CacheKeywords();

        IgniteConfiguration cfg = new IgniteConfiguration();
        CacheConfiguration<String, String> cacheCfg = new CacheConfiguration<>("testCache");
        cfg.setCacheConfiguration(cacheCfg);

        Ignition.start(cfg.setGridName("testCacheCount"));

        clusterKeywordsKeywords.connectToIgnite();
    }

    @AfterClass
    public static void teardown() {
        for(Ignite ignite: Ignition.allGrids()) {
            ignite.close();
        }
    }

    @Test
    public void testCacheNodeCountPass() throws Exception {
        cacheKeywords.nodeCountWithCacheShouldBe("testCache", 1);
    }

    @Test
    public void testCacheNodeCountPassNoCache() throws Exception {
        cacheKeywords.nodeCountWithCacheShouldBe("fooBar", 0);
    }

    @Test(expected = RuntimeException.class)
    public void testCacheNodeCountFailWrongCount() throws Exception {
        cacheKeywords.nodeCountWithCacheShouldBe("testCache", 3);
    }

    @Test(expected = RuntimeException.class)
    public void testCacheNodeCountFailWrongName() throws Exception {
        cacheKeywords.nodeCountWithCacheShouldBe("fooBar", 1);
    }
}
