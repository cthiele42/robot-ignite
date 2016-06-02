package org.roboscratch.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.roboscratch.ignite.keywords.ClusterKeywords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by cthiele on 31.05.16.
 */
public class StartNodeTest {
    private static ClusterKeywords keywords;

    @BeforeClass
    public static void setup() throws Exception {
        keywords = new ClusterKeywords();
    }

    @AfterClass
    public static void teardown() {
        for(Ignite ignite: Ignition.allGrids()) {
            ignite.close();
        }
    }

    @Test
    public void testStartServerNodeWithConfigFile() throws Exception {
        keywords.startClusterNode("testserverNoConfig");

        assertEquals(1, Ignition.allGrids().size());
        assertEquals("testserverNoConfig", Ignition.allGrids().get(0).configuration().getGridName());
    }
}
