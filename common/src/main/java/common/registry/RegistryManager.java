package common.registry;

import common.Category;
import common.Menu;
import common.Option;
import common.OptionGroup;

import java.util.HashMap;

/**
 * Serializable 순서
 * 1. Options
 * 2. OptioGroups
 * 3. MENUS
 * 4. CATEGORIES
 */
public class RegistryManager {
    private static final HashMap<String, Registry<?>> REGISTRY_MAP = new HashMap<>();
    public static Registry<?> getAsId(String id) {
        return REGISTRY_MAP.get(id);
    }

    public static final Registry<Option> OPTIONS = new SimpleRegistry<>("options", Option.SYNC_CODEC, Option.class);
    public static final Registry<OptionGroup> OPTION_GROUPS = new SimpleRegistry<>("option_groups", OptionGroup.SYNC_CODEC, OptionGroup.class);
    public static final Registry<Menu> MENUS = new SimpleRegistry<>("menus", Menu.SYNC_CODEC, Menu.class);
    public static final Registry<Category> CATEGORIES = new SimpleRegistry<>("categories", Category.SYNC_CODEC, Category.class);

    static {
        REGISTRY_MAP.put("options", OPTIONS);
        REGISTRY_MAP.put("option_groups", OPTION_GROUPS);
        REGISTRY_MAP.put("menus", MENUS);
        REGISTRY_MAP.put("categories", CATEGORIES);
    }
}
