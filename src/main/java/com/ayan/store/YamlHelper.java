package com.ayan.store;

import java.io.File;
import java.util.Objects;

public class YamlHelper {
    public static File getFile(String path) {
        String resourceFolder = new File(Objects.requireNonNull(ConfigManager.class.getClassLoader().getResource("")).getFile()).getPath();
        return new File(resourceFolder + File.separator + path);
    }
}
