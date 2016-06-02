package org.roboscratch.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.roboscratch.ignite.keywords.ClusterKeywords;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.*;

/**
 * Created by cthiele on 31.05.16.
 */
public class StartNodeWithConfigFileTest {
    private static ClusterKeywords keywords;
    private static String configPath;

    @BeforeClass
    public static void setup() throws Exception {
        keywords = new ClusterKeywords();

        URL configUrl = StartNodeWithConfigFileTest.class.getResource("/local-ignite-config.xml");
        assertNotNull(configUrl);
        File configFile = new File(configUrl.toURI());
        configPath = configFile.getAbsolutePath();
    }

    @AfterClass
    public static void teardown() {
        for(Ignite ignite: Ignition.allGrids()) {
            ignite.close();
        }
    }

    @Test
    public void testStartServerNodeWithConfigFile() throws Exception {
        keywords.startClusterNode("testserver", configPath);

        assertEquals(1, Ignition.allGrids().size());
        assertEquals("testserver", Ignition.allGrids().get(0).configuration().getGridName());
    }
}
