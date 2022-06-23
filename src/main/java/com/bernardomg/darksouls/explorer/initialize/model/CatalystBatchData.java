
package com.bernardomg.darksouls.explorer.initialize.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class CatalystBatchData {

    @NonNull
    private String  attacks                  = "";

    @NonNull
    private Double  critical_damage          = 0d;

    @NonNull
    private String  description              = "";

    @NonNull
    private Integer dexterity_requirement    = 0;

    @NonNull
    private String  dexterity_bonus          = "";

    @NonNull
    private Integer durability               = 0;

    @NonNull
    private Integer faith_requirement        = 0;

    @NonNull
    private String  faith_bonus              = "";

    @NonNull
    private Double  fire_damage              = 0d;

    @NonNull
    private Double  fire_reduction           = 0d;

    @NonNull
    private Integer intelligence_requirement = 0;

    @NonNull
    private String  intelligence_bonus       = "";

    @NonNull
    private Double  lightning_damage         = 0d;

    @NonNull
    private Double  lightning_reduction      = 0d;

    @NonNull
    private Double  magic_damage             = 0d;

    @NonNull
    private Double  magic_reduction          = 0d;

    @NonNull
    private String  name                     = "";

    @NonNull
    private Double  physical_damage          = 0d;

    @NonNull
    private Double  physical_reduction       = 0d;

    @NonNull
    private Double  stability                = 0d;

    @NonNull
    private Integer strength_requirement     = 0;

    @NonNull
    private String  strength_bonus           = "";

    @NonNull
    private String  type                     = "";

    @NonNull
    private Double  weight                   = 0d;

}
