package org.roboscratch.ignite.keywords;

import org.apache.ignite.IgniteCache;
import org.roboscratch.ignite.IgniteLibrary;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

/**
 * Created by cthiele on 02.06.16.
 */
@RobotKeywords
public class CacheKeywords {

    @RobotKeyword("Fails if the node count holding a cache with given name is not the expected.\n" +
            "Example:\n" +
            "| Node Count With CacheKeywords Should Be | myCache | 2 |")
    @ArgumentNames({"cacheName", "count"})
    public void nodeCountWithCacheShouldBe(String cacheName, int count) {
        if(IgniteLibrary.ignite == null) {
            throw new IllegalStateException("Not connected - please connect to a Ignite cluster first using the keyword 'Connect To Ignite'");
        }
        int nodeCount = IgniteLibrary.ignite.cluster().forCacheNodes(cacheName).nodes().size();
        if(nodeCount != count) {
            throw new RuntimeException("Awaited count of nodes with cache " + cacheName + ": " + count + ", but was " + nodeCount);
        }
    }

    @RobotKeyword("Fails if the size of the given cache does not match the expected size.\n" +
            "Example:\n" +
            "| Cache Size Should Be | 2 |")
    @ArgumentNames({"cacheName", "size"})
    public void cacheSizeShouldBe(String cacheName, int size) {
        if(IgniteLibrary.ignite == null) {
            throw new IllegalStateException("Not connected - please connect to a Ignite cluster first using the keyword 'Connect To Ignite'");
        }
        IgniteCache<?,?> cache = IgniteLibrary.ignite.cache(cacheName);
        if(cache == null) {
            throw new RuntimeException("Cache with name " + cacheName + " does not exist");
        }

        int cacheSize = cache.size();
        if(cacheSize != size) {
            throw new RuntimeException("Awaited size of cache " + cacheName + ": " + size + ", but was " + cacheSize);
        }
    }

    @RobotKeyword("Clear the cache with given name.\n" +
            "Example:\n" +
            "| Clear Cache | myCache |")
    public void clearCache(String cacheName) {
        if(IgniteLibrary.ignite == null) {
            throw new IllegalStateException("Not connected - please connect to a Ignite cluster first using the keyword 'Connect To Ignite'");
        }
        IgniteCache<?,?> cache = IgniteLibrary.ignite.cache(cacheName);
        if(cache == null) {
            throw new RuntimeException("Cache with name " + cacheName + " does not exist");
        }
        cache.clear();
    }
}
