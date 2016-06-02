package org.roboscratch.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.roboscratch.ignite.keywords.ClusterKeywords;

import static org.junit.Assert.assertEquals;

/**
 * Created by cthiele on 02.06.16.
 */
public class NodeCountTest {
    private static ClusterKeywords keywords;

    @BeforeClass
    public static void setup() throws Exception {
        keywords = new ClusterKeywords();

        keywords.startClusterNode("testserverForNodeCountTest");
        keywords.connectToIgnite();
    }

    @AfterClass
    public static void teardown() {
        for(Ignite ignite: Ignition.allGrids()) {
            ignite.close();
        }
    }

    @Test
    public void testNodeCountPass() throws Exception {
        keywords.clusterNodeCountShouldBe(2);
    }

    @Test(expected = RuntimeException.class)
    public void testNodeCountFail() throws Exception {
        keywords.clusterNodeCountShouldBe(3);
    }
}
