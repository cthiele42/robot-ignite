package org.roboscratch.ignite.keywords;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.roboscratch.ignite.IgniteLibrary;
import org.robotframework.javalib.annotation.*;

/**
 * Created by cthiele on 30.05.16.
 */
@RobotKeywords
public class ClusterKeywords {
    @RobotKeyword("Connect to an Ignite cluster via client node.\n" +
            "Examples:\n" +
            "| Connect To Ignite | # Connects to a cluster using the standard configuration |\n" +
            "| Connect To Ignite | ignite-config.xml | # Connects to a cluster using the ignite-config.xml |\n")
    @ArgumentNames({"configXml="})
    public void connectToIgnite(String configXml) {
        if(IgniteLibrary.ignite != null) return;
        Ignition.setClientMode(true);
        if(configXml == null) {
            IgniteLibrary.ignite = Ignition.start();

        } else {
            IgniteLibrary.ignite = Ignition.start(configXml);
        }
    }

    @RobotKeywordOverload
    public void connectToIgnite() {
        connectToIgnite(null);
    }

    @RobotKeyword("Start an Ignite node with a given name and an optional configuration file.\n" +
            "If the configuration file is omitted, the standard configuration will be used.\n" +
            "The config.xml has to hold a Spring bean definition for class org.apache.ignite.configuration.IgniteConfiguration and" +
            "with id equals name\n" +
            "Examples:\n" +
            "| Start ClusterKeywords Node | testnode1 | # starts a node with name testnode1 using the standard configuration |\n" +
            "| Start ClusterKeywords Node | testnode2 | config.xml | # starts a node with name testnode2 using the config.xml")
    @ArgumentNames({"name", "configXml="})
    public void startClusterNode(String name, String configXml) {
        IgniteConfiguration cfg = new IgniteConfiguration();
        if(configXml != null) {
            cfg = Ignition.loadSpringBean(configXml, name);
        }
        Ignition.getOrStart(cfg.setGridName(name));
    }

    @RobotKeywordOverload
    public void startClusterNode(String name) {
        startClusterNode(name, null);
    }

    @RobotKeyword("Fails if the node count of the Ignite cluster does not match the expected.\n" +
            "Example:\n" +
            "| ClusterKeywords Node Count Should Be | 4 |")
    @ArgumentNames({"count"})
    public void clusterNodeCountShouldBe(int count) {
        if(IgniteLibrary.ignite == null) {
            throw new IllegalStateException("Not connected - please connect to a Ignite cluster first using the keyword 'Connect To Ignite'");
        }
        int nodeCount = IgniteLibrary.ignite.cluster().nodes().size();
        if(nodeCount != count) {
            throw new RuntimeException("Awaited node count " + count + ", but was " + nodeCount);
        }
    }
}
