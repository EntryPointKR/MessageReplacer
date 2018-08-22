package com.github.entrypointkr.messagereplacer.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by JunHyeong on 2018-08-22
 */
public class UpdateChecker implements Runnable, Listener {
    private static final URL RELEASE_URL = getReleaseUrl();
    private final Plugin plugin;
    private final Version pluginVersion;
    private Version latest = null;

    private static URL getReleaseUrl() {
        try {
            return new URL("https://github.com/EntryPointKR/MessageReplacer/releases/latest");
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static URL getFinalUrl(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            connection.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            connection.addRequestProperty("Referer", "https://www.google.com/");
            connection.connect();
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_SEE_OTHER
                    || code == HttpURLConnection.HTTP_MOVED_PERM
                    || code == HttpURLConnection.HTTP_MOVED_TEMP) {
                String location = connection.getHeaderField("Location");
                if (location.startsWith("/")) {
                    location = url.getProtocol() + "://" + url.getHost() + location;
                }
                return getFinalUrl(new URL(location));
            }
        } catch (Exception e) {
            // Ignore
        }
        return url;
    }

    public static Version getLatestVersion() {
        URL finalUrl = getFinalUrl(RELEASE_URL);
        String url = finalUrl.toExternalForm();
        String target = "tag/";
        int position = url.indexOf(target);
        if (position != -1) {
            String tag = url.substring(position + target.length());
            return new Version(tag);
        }
        return null;
    }

    public static void init(Plugin plugin) {
        UpdateChecker checker = new UpdateChecker(plugin, new Version(plugin.getDescription().getVersion()));
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, checker, 0, 24L * 3600L * 20L);
        Bukkit.getPluginManager().registerEvents(checker, plugin);
    }

    public UpdateChecker(Plugin plugin, Version pluginVersion) {
        this.plugin = plugin;
        this.pluginVersion = pluginVersion;
    }

    public void sendUpdateMessage(CommandSender sender, Version newVersion) {
        sender.sendMessage("New release found.");
        sender.sendMessage("Latest: " + newVersion);
        sender.sendMessage("Current: " + pluginVersion);
        sender.sendMessage("Download: " + RELEASE_URL);
    }

    public boolean isOutdated() {
        return latest != null && pluginVersion.before(latest);
    }

    @Override
    public void run() {
        this.latest = getLatestVersion();
        if (isOutdated()) {
            sendUpdateMessage(Bukkit.getConsoleSender(), latest);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.isOp() && isOutdated()) {
            Bukkit.getScheduler().runTask(plugin, () -> sendUpdateMessage(player, latest));
        }
    }
}
