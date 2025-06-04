package dev.qf.client;

import common.Menu;
import common.Option;
import common.OptionGroup;
import common.OrderItem;

import java.util.List;
import java.util.Map;

public class OptionSelectionController {
    public boolean isValidOption(Map<OptionGroup, String> selectedOptions, List<OptionGroup> rules) {
        for (OptionGroup group : rules) {
            if (group.required() && !selectedOptions.containsKey(group)) {
                return false;
            }
        }
        return true;
    }

    public OrderItem createOrderItem(Menu menu, Map<OptionGroup, Option> selectedOptions, int quantity) {
        return new OrderItem(menu, selectedOptions, quantity);
    }
}