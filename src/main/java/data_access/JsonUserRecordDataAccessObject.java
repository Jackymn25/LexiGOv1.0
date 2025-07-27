package data_access;

import entity.LearnRecord;
import use_case.finish_checkin.UserSaveRecordDataAccessInterface;
import use_case.start_checkin.UserRecordDataAccessInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * Persist user learning records in JSON using Gson.
 * File location: E:\LexiGOv1.1\data\learn_record.json
 */
public class JsonUserRecordGsonDao
        implements UserRecordDataAccessInterface, UserSaveRecordDataAccessInterface {

    private static final Path FILE =
            Paths.get("E:", "LexiGOv1.1", "data", "learn_record.json");

    private static final DateTimeFormatter ISO =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final Gson gson;
    private final Type mapType =
            new TypeToken<Map<String, List<LearnRecord>>>() {}.getType();
    private final Map<String, List<LearnRecord>> cache;

    public JsonUserRecordGsonDao() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (com.google.gson.JsonSerializer<LocalDateTime>)
                                (src, t, ctx) -> ctx.serialize(src.format(ISO)))
                .registerTypeAdapter(LocalDateTime.class,
                        (com.google.gson.JsonDeserializer<LocalDateTime>)
                                (json, t, ctx) -> LocalDateTime.parse(json.getAsString(), ISO))
                .setPrettyPrinting()
                .create();
        this.cache = loadFromDisk();
    }

    @Override
    public synchronized List<LearnRecord> get(String username) {
        return cache.getOrDefault(username, List.of());
    }

    @Override
    public synchronized void save(LearnRecord record) {
        cache.computeIfAbsent(record.getUsername(), k -> new ArrayList<>())
                .add(record);
        flush();
    }

    /* ---------- private helpers ---------- */

    private Map<String, List<LearnRecord>> loadFromDisk() {
        try {
            if (Files.notExists(FILE)) {
                Files.createDirectories(FILE.getParent());
                Files.writeString(FILE, "{}");
            }
            String json = Files.readString(FILE);
            Map<String, List<LearnRecord>> map = gson.fromJson(json, mapType);
            return map != null ? map : new HashMap<>();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read learn_record.json", e);
        }
    }

    private void flush() {
        try {
            Files.writeString(FILE, gson.toJson(cache, mapType));
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write learn_record.json", e);
        }
    }
}
