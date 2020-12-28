package com.nls.util;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MimeTypes {
    private static final Logger LOGGER = LoggerFactory.getLogger(MimeTypes.class);
    private static final List<MimeTypeEntry> ENTRIES = load();
    private static final Map<String, MimeTypeEntry> EXTENSION_MAP = buildExtensionMap();
    private static final Map<String, MimeTypeEntry> MIME_TYPE_MAP = buildMimeTypeMap();

    private MimeTypes() {

    }

    public static MimeTypeEntry fromMimeType(String mimeType) {
        return MIME_TYPE_MAP.get(mimeType);
    }

    public static MimeTypeEntry fromExtension(String extension) {
        return extension == null ? null : EXTENSION_MAP.get(extension.startsWith(".") ? extension : "." + extension);
    }

    public static MimeTypeEntry fromExtensionOrMimeType(String extension, String mimeType) {
        MimeTypeEntry entry = EXTENSION_MAP.get(extension.startsWith(".") ? extension : "." + extension);
        return entry == null ? fromMimeType(mimeType) : entry;
    }

    public static MimeTypeEntry fromFilename(String filename) {
        return fromExtension(FilenameUtils.getExtension(filename));
    }

    public static MimeTypeEntry fromFilenameOrMimeType(String filename, String mimeType) {
        return fromExtensionOrMimeType(FilenameUtils.getExtension(filename), mimeType);
    }

    private static List<MimeTypeEntry> load() {
        List<MimeTypeEntry> entries = new ArrayList<>();
        URL data = MimeTypes.class.getResource("/data/mime-types.txt");
        try (LineNumberReader stream = new LineNumberReader(new InputStreamReader(data.openStream()))) {
            String line;
            while ((line = stream.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] tokens = line.split(";");
                entries.add(new MimeTypeEntry(
                        tokens[0].trim(),
                        tokens[1].trim(),
                        tokens[2].trim(),
                        tokens.length == 3
                                ? Collections.emptyList()
                                : Arrays.stream(tokens[3].split(",")).map(String::trim).collect(Collectors.toList())));
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't load mime types file, error=" + e.getMessage());
        }
        return Collections.unmodifiableList(entries);
    }

    private static Map<String, MimeTypeEntry> buildMimeTypeMap() {
        Map<String, MimeTypeEntry> map = new HashMap<>();
        for (MimeTypeEntry entry : MimeTypes.ENTRIES) {
            map.put(entry.getType(), entry);
            for (String alias : entry.getAliases()) {
                map.put(alias, entry);
            }
        }
        return map;
    }

    private static Map<String, MimeTypeEntry> buildExtensionMap() {
        return Collections.unmodifiableMap(MimeTypes.ENTRIES
                .stream()
                .collect(Collectors.toMap(MimeTypeEntry::getExtension, Function.identity())));
    }
}
