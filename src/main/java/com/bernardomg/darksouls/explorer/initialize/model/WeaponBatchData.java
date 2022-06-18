
package com.bernardomg.darksouls.explorer.initialize.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class WeaponBatchData {

    @NonNull
    private String  attacks             = "";

    @NonNull
    private Double  critical_dmg        = 0d;

    @NonNull
    private String  description         = "";

    @NonNull
    private Integer dexterity           = 0;

    @NonNull
    private String  dexterity_bonus     = "";

    @NonNull
    private Integer durability          = 0;

    @NonNull
    private Integer faith               = 0;

    @NonNull
    private String  faith_bonus         = "";

    @NonNull
    private Double  fire_dmg            = 0d;

    @NonNull
    private Double  fire_reduction      = 0d;

    @NonNull
    private Integer intelligence        = 0;

    @NonNull
    private String  intelligence_bonus  = "";

    @NonNull
    private Double  lightning_dmg       = 0d;

    @NonNull
    private Double  lightning_reduction = 0d;

    @NonNull
    private Double  magic_dmg           = 0d;

    @NonNull
    private Double  magic_reduction     = 0d;

    @NonNull
    private String  name                = "";

    @NonNull
    private Double  physical_dmg        = 0d;

    @NonNull
    private Double  physical_reduction  = 0d;

    @NonNull
    private Double  stability           = 0d;

    @NonNull
    private Integer strength            = 0;

    @NonNull
    private String  strength_bonus      = "";

    @NonNull
    private String  type                = "";

    @NonNull
    private Double  weight              = 0d;

}
