package com.magmaguy.resourcepackmanager.config;

import com.magmaguy.magmacore.util.Logger;
import com.magmaguy.magmacore.util.ZipFile;
import com.magmaguy.resourcepackmanager.ResourcePackManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BlueprintFolder {
    private BlueprintFolder() {
    }

    public static void initialize() {
        Logger.info("Creating blueprint folder");
        File blueprintDirectory = new File(ResourcePackManager.plugin.getDataFolder().getAbsolutePath() + File.separatorChar + "blueprint");
        if (!blueprintDirectory.exists()) blueprintDirectory.mkdir();
        Logger.info("Copying image");
        File imageFile = new File(blueprintDirectory.getAbsolutePath() + File.separatorChar + "pack.png");
        if (!imageFile.exists()) {
            try (InputStream inputStream = ResourcePackManager.plugin.getResource("pack.png")) {
                Files.copy(inputStream, imageFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Logger.info("Copying mcmeta");
        File mcmetaFile = new File(blueprintDirectory.getAbsolutePath() + File.separatorChar + "pack.mcmeta");
        if (!mcmetaFile.exists()) {
            try (InputStream inputStream = ResourcePackManager.plugin.getResource("pack.mcmeta")) {
                Files.copy(inputStream, mcmetaFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Logger.info("Copying uniformchange folder");
        try {
            java.net.URL jarLocation = ResourcePackManager.class.getProtectionDomain().getCodeSource().getLocation();
            if (jarLocation != null) {
                File jarFile = new File(jarLocation.toURI());
                if (jarFile.isFile()) {
                    try (java.util.jar.JarFile jar = new java.util.jar.JarFile(jarFile)) {
                        java.util.Enumeration<java.util.jar.JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            java.util.jar.JarEntry entry = entries.nextElement();
                            if (entry.getName().startsWith("uniformchange/") && !entry.isDirectory()) {
                                File target = new File(blueprintDirectory, entry.getName());
                                target.getParentFile().mkdirs();
                                if (!target.exists()) {
                                    try (InputStream in = jar.getInputStream(entry)) {
                                        Files.copy(in, target.toPath());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.warn("Failed to copy uniformchange folder: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            ZipFile.ZipUtility.zip(blueprintDirectory, blueprintDirectory.getAbsolutePath() + File.separatorChar + "blueprint.zip");
        } catch (Exception e) {
            Logger.warn("Failed to zip blueprint resource pack!");
        }
    }
}
