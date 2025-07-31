package im.fooding.core.global.elasticsearch;

import java.util.Map;

public class IndexSettingMapping {
    private final Map<String, Object> settings;
    private final Map<String, Object> mappings;

    public IndexSettingMapping(Map<String, Object> settings, Map<String, Object> mappings) {
        this.settings = settings;
        this.mappings = mappings;
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public Map<String, Object> getMappings() {
        return mappings;
    }
}
