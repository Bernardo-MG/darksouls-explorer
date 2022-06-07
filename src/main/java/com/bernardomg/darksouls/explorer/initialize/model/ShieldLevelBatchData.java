
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
    private String  dexterity;

    @NonNull
    private String  faith;

    @NonNull
    private Integer fire;

    @NonNull
    private Float   fire_reduction;

    @NonNull
    private String  intelligence;

    @NonNull
    private Integer level;

    @NonNull
    private Integer lightning;

    @NonNull
    private Float   lightning_reduction;

    @NonNull
    private Integer magic;

    @NonNull
    private Float   magic_reduction;

    @NonNull
    private String  name;

    @NonNull
    private String  path;

    @NonNull
    private Integer physical;

    @NonNull
    private Float   physical_reduction;

    @NonNull
    private Integer stability;

    @NonNull
    private String  strength;

}
