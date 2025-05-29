package dev.qf.server;

import common.Category;
import common.Menu;
import common.Option;
import common.OptionGroup;
import common.registry.RegistryManager;
import org.jetbrains.annotations.TestOnly;

import java.nio.file.Path;
import java.util.List;

/**
 * this must be executed test scenarios. this will ruins system when runtime.
 */
@TestOnly
public class DebugData {

    DebugData() {

    }

    public void generateDebugData() {
        Option testOption1 = new Option("testOption1", "테스트", 1000);
        OptionGroup testOptionGroup = new OptionGroup("testOptionGroup1", "테스트옵션그룹", true, List.of(testOption1));
        Menu testMenu = new Menu("testMenu", "테스트메뉴", 5000, Path.of(""), "테스트", List.of(testOptionGroup));
        Category testCategory = new Category("testCategory1", "대충 커피", List.of(testMenu));

        RegistryManager.CATEGORIES.add(testCategory.cateId(), testCategory);
        RegistryManager.MENUS.add(testMenu.id(), testMenu);
        RegistryManager.OPTION_GROUPS.add(testOptionGroup.name(), testOptionGroup);
        RegistryManager.OPTIONS.add(testOption1.name(), testOption1);
    }
}
