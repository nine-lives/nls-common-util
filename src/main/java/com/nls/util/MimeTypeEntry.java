package com.nls.util;

import java.util.List;

public class MimeTypeEntry {

    private final String extension;
    private final String description;
    private final String type;
    private final List<String> aliases;

    public MimeTypeEntry(String extension, String type, String description, List<String> aliases) {
        this.extension = extension;
        this.description = description;
        this.type = type;
        this.aliases = aliases;
    }

    public String getExtension() {
        return extension;
    }

    public String getExtensionWithoutDot() {
        return extension.substring(1);
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public boolean isImage() {
        return type != null && type.startsWith("image");
    }

    @Override
    public String toString() {
        return "MimeTypeEntry{" +
                "extension='" + extension + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", aliases=" + aliases +
                '}';
    }
}
