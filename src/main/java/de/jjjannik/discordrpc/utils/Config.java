package de.jjjannik.discordrpc.utils;

import de.jcm.discordgamesdk.activity.ActivityButton;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Getter
@Setter
public class Config {
    private long applicationId;
    private long startTimestamp;
    private long endTimestamp;
    private String state = "";
    private String details = "";
    private String largeImageKey = "";
    private String largeImageText = "";
    private String smallImageKey = "";
    private String smallImageText = "";
    private ActivityButton button1;
    private ActivityButton button2;
    private int partySize;
    private int maxPartySize;

    public void dumpConfig(String path) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);
        try {
            yaml.dump(this, new FileWriter(path));
        } catch (IOException e) {
            log.error("Failed creating FileWriter", e);
        }
    }
}