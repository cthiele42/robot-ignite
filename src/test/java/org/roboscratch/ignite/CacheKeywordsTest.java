package org.roboscratch.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.roboscratch.ignite.keywords.CacheKeywords;
import org.roboscratch.ignite.keywords.ClusterKeywords;

import java.util.TreeSet;

import static org.junit.Assert.*;

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
        CacheConfiguration<String, TreeSet> cacheCfg2 = new CacheConfiguration<>("testCache2");
        cfg.setCacheConfiguration(cacheCfg, cacheCfg2);

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
        cacheKeywords.nodeCountWithCacheShouldBe("testCache", 2);
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

    @Test
    public void testCacheSizePass() throws Exception {
        cacheKeywords.cacheSizeShouldBe("testCache", 0);
    }

    @Test(expected = RuntimeException.class)
    public void testCacheSizeFailWrongSize() throws Exception {
        cacheKeywords.cacheSizeShouldBe("testCache", 1);
    }

    @Test(expected = RuntimeException.class)
    public void testCacheSizeFailWrongName() throws Exception {
        cacheKeywords.cacheSizeShouldBe("fooBar", 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCacheClear() throws Exception {
        IgniteCache cache = IgniteLibrary.ignite.cache("testCache");
        assertNotNull(cache);
        cache.put("foo", "bar");
        assertEquals(1, cache.size());

        cacheKeywords.clearCache("testCache");
        assertEquals(0, cache.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGet() throws Exception {
        IgniteCache cache = IgniteLibrary.ignite.cache("testCache2");
        assertNotNull(cache);
        TreeSet<String> value = new TreeSet();
        value.add("bar");

        cache.put("foo", value);
        assertEquals(1, cache.size());

        Object valueGot = cacheKeywords.getFromCache("testCache2", "foo");

        assertEquals("bar", ((TreeSet<String>)valueGot).first());
    }
}
