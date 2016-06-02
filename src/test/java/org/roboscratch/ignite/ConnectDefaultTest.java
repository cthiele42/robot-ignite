package org.roboscratch.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.*;
import org.roboscratch.ignite.keywords.ClusterKeywords;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by cthiele on 31.05.16.
 */
public class ConnectDefaultTest {
    private static ClusterKeywords keywords;
    private static String configPath;

    @BeforeClass
    public static void setup() throws Exception {
        keywords = new ClusterKeywords();

        URL configUrl = ConnectDefaultTest.class.getResource("/local-ignite-config.xml");
        assertNotNull(configUrl);
        File configFile = new File(configUrl.toURI());
        IgniteConfiguration config = new IgniteConfiguration();

        Ignition.start(config.setGridName("testserver"));
    }

    @AfterClass
    public static void teardown() {
        for(Ignite ignite: Ignition.allGrids()) {
            ignite.close();
        }
    }

    @Test
    public void testIgniteConnectionDefault() throws Exception {
        keywords.connectToIgnite();
    }
}
