
package com.bernardomg.darksouls.explorer.item.armor.query;

import java.util.Arrays;
import java.util.Map;

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;
import com.bernardomg.darksouls.explorer.item.persistence.GenericQuery;
import com.bernardomg.darksouls.explorer.persistence.utils.Maps;

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

        id = Maps.getOrDefault(record, "id", -1l);
        name = Maps.getOrDefault(record, "name", "");
        description = Arrays.asList(Maps.getOrDefault(record, "description", "")
            .split("\\|"));

        weight = Maps.getOrDefault(record, "weight", -1l);
        durability = Maps.getOrDefault(record, "durability", -1l)
            .intValue();

        armor = new DtoArmor();
        armor.setId(id);
        armor.setName(name);
        armor.setDescription(description);
        armor.setWeight(weight);
        armor.setDurability(durability);

        return armor;
    }

}
