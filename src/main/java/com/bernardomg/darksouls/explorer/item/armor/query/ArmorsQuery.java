
package com.bernardomg.darksouls.explorer.item.armor.query;

import java.util.Arrays;
import java.util.Map;

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;
import com.bernardomg.darksouls.explorer.item.persistence.GenericQuery;

public final class ArmorsQuery extends GenericQuery<Armor> {

    public ArmorsQuery() {
        super("Armor",
            Arrays.asList("id", "name", "description", "durability", "weight"));
    }

    @Override
    public final Armor getOutput(final Map<String, Object> record) {
        final Long id;
        final String name;
        final Iterable<String> description;
        final DtoArmor armor;
        final Long weight;
        final Integer durability;

        id = (Long) record.getOrDefault("id", -1l);
        name = (String) record.getOrDefault("name", "");
        description = Arrays.asList(
            ((String) record.getOrDefault("description", "")).split("\\|"));

        weight = (Long) record.getOrDefault("weight", -1l);
        durability = ((Long) record.getOrDefault("durability", -1l)).intValue();

        armor = new DtoArmor();
        armor.setId(id);
        armor.setName(name);
        armor.setDescription(description);
        armor.setWeight(weight);
        armor.setDurability(durability);

        return armor;
    }

}
