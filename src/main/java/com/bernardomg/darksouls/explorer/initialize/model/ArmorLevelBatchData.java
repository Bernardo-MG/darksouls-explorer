
package com.bernardomg.darksouls.explorer.initialize.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class ArmorLevelBatchData {

    @NonNull
    private Float   bleed;

    @NonNull
    private Float   curse;

    @NonNull
    private Float   fire;

    @NonNull
    private Integer level;

    @NonNull
    private Float   lightning;

    @NonNull
    private Float   magic;

    @NonNull
    private String  name;

    @NonNull
    private Float   poise;

    @NonNull
    private Float   poison;

    @NonNull
    private Float   regular;

    @NonNull
    private Float   slash;

    @NonNull
    private Float   strike;

    @NonNull
    private Float   thrust;

}
