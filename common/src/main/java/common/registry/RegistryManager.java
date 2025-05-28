package common.registry;

import common.Category;
import common.Menu;
import common.Option;
import common.OptionGroup;

/**
 * Serializable 순서
 * 1. Options
 * 2. OptioGroups
 * 3. MENUS
 * 4. CATEGORIES
 */
public class RegistryManager {
    public static Registry<?> getAsId(String id) {
        // TODO : IMPLEMENTATION
        return null;
    }
    public static final Registry<Option> OPTIONS;
    public static final Registry<OptionGroup> OPTION_GROUPS;
    public static final Registry<Menu> MENUS = new SimpleRegistry<>("menus", Menu.SYNC_CODEC);
    public static final Registry<Category> CATEGORIES = new SimpleRegistry<>("categories", Category.SYNC_CODEC);
}
