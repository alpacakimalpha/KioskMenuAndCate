package common;

import java.nio.file.Path;
import java.util.List;

public record Menu(
        String id,
        String name,
        int price,
        String cateId,
        Path imagePath,
        String description,
        List<OptionGroup> optionGroup
) {}
