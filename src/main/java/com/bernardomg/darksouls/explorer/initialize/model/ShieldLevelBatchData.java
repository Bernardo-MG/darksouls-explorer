
package com.bernardomg.darksouls.explorer.initialize.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class ShieldLevelBatchData {

    @NonNull
    private Integer critical;

    @NonNull
    private String  dexterity_bonus;

    @NonNull
    private String  faith_bonus;

    @NonNull
    private Integer fire_damage;

    @NonNull
    private Float   fire_reduction;

    @NonNull
    private String  intelligence_bonus;

    @NonNull
    private Integer level;

    @NonNull
    private Integer lightning_damage;

    @NonNull
    private Float   lightning_reduction;

    @NonNull
    private Integer magic_damage;

    @NonNull
    private Float   magic_reduction;

    @NonNull
    private String  name;

    @NonNull
    private String  path;

    @NonNull
    private Integer physical_damage;

    @NonNull
    private Float   physical_reduction;

    @NonNull
    private Integer stability;

    @NonNull
    private String  strength_bonus;

}
