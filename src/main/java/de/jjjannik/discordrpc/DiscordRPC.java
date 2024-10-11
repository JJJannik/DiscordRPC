package de.jjjannik.discordrpc;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.*;
import de.jjjannik.discordrpc.utils.Config;
import de.jjjannik.discordrpc.utils.Utils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class DiscordRPC { // https://github.com/JnCrMx/discord-game-sdk4j/issues/71
    private Config config;

    public DiscordRPC() throws IOException {
        initFiles();
    }

    private void initFiles() throws IOException {
        final File file = new File("./DiscordRPC.yml");
        if (!file.exists()) {
            Files.copy(Utils.getResourceAsFile("DiscordRPC.yml").toPath(), file.toPath());
        }

        config = new Yaml(new Constructor(Config.class)).load(new FileInputStream(file));
        if (config == null) {
            config = new Config();
            config.dumpConfig(file.getPath());
        }
        String applicationId = System.getProperty("applicationId");

        if (config.getApplicationId() == 0) {
            if (applicationId == null) {
                log.error("Application ID may not be empty!");
                System.exit(1);
            }

            config.setApplicationId(Long.parseLong(applicationId));
            config.dumpConfig(file.getPath());
        }
    }

    public void runActivity() {
        try (Activity activity = new Activity(); CreateParams params = new CreateParams()) {
            params.setClientID(config.getApplicationId());
            params.setFlags(CreateParams.getDefaultFlags());
            params.setFlags(CreateParams.Flags.NO_REQUIRE_DISCORD);
            activity.setActivityButtonsMode(ActivityButtonsMode.BUTTONS);

            Core core = initCore(params);

            if (!Strings.isEmpty(config.getDetails())) {
                activity.setDetails(config.getDetails());
            }
            if (!Strings.isEmpty(config.getState())) {
                activity.setState(config.getState());
            }

            boolean warnParty = loadParty(activity);

            if (config.getEndTimestamp() > 0) {
                ActivityTimestamps timestamps = activity.timestamps();
                timestamps.setStart(Instant.ofEpochMilli(config.getStartTimestamp()));
                timestamps.setEnd(Instant.ofEpochMilli(config.getEndTimestamp()));
            }

            loadAssets(activity);
            loadButtons(activity);

            core.activityManager().updateActivity(activity);

            new Timer("Discord Rich Presence").schedule(new TimerTask() {
                @Override
                public void run() {
                    core.runCallbacks();
                }}, 0, 16);

            new Timer("Warnings").schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println();
                    log.info("--------------------------");
                    log.info("<< Activity started >>");
                    log.info("--------------------------");
                    System.out.println();
                    if (warnParty) {
                        log.warn("If you want to display a party, you have to provide partySize AND maxPartySize.\n");
                    }
                }}, 500);
        } catch (Exception e) {
            log.error("Something went wrong", e);
        }
    }

    private Core initCore(CreateParams params) {
        try {
            return new Core(params);
        } catch (RuntimeException e) {
            log.error("\nThe application id: \"{}\" is invalid.", config.getApplicationId());
            System.exit(1);
        }
        return null;
    }

    private boolean loadParty(Activity activity) {
        ActivityParty party = activity.party();
        if (config.getPartySize() > 0 && config.getMaxPartySize() > 0) {
            party.setID("AReallyNiceRandomPartyIdWhichNobodyNeeds");
            ActivityPartySize partySize = party.size();
            partySize.setCurrentSize(config.getPartySize());
            partySize.setMaxSize(config.getMaxPartySize());
        } else {
            return true;
        }
        return false;
    }

    private void loadButtons(Activity activity) {
        ActivityButton button1 = config.getButton1();
        if (button1 != null && !Strings.isEmpty(button1.getLabel())) {
            activity.addButton(button1);
        }

        ActivityButton button2 = config.getButton1();
        if (button2 != null && !Strings.isEmpty(button2.getLabel())) {
            activity.addButton(button2);
        }
    }

    private void loadAssets(Activity activity) {
        ActivityAssets assets = activity.assets();
        if (!Strings.isEmpty(config.getLargeImageKey())) {
            assets.setLargeImage(config.getLargeImageKey());
        }
        if (!Strings.isEmpty(config.getLargeImageText())) {
            assets.setLargeText(config.getLargeImageText());
        }
        if (!Strings.isEmpty(config.getSmallImageKey())) {
            assets.setSmallImage(config.getSmallImageKey());
        }
        if (!Strings.isEmpty(config.getSmallImageText())) {
            assets.setSmallText(config.getSmallImageText());
        }
    }
}