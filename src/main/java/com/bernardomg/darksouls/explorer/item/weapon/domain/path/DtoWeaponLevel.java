
package com.bernardomg.darksouls.explorer.item.weapon.domain.path;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponLevel implements WeaponLevel {

    @NonNull
    private Integer critical=0;

    @NonNull
    private String  dexterityBonus="";

    @NonNull
    private String  faithBonus="";

    @NonNull
    private Integer fireDamage=0;

    @NonNull
    private Float   fireReduction=0f;

    @NonNull
    private String  intelligenceBonus="";

    @NonNull
    private Integer level=0;

    @NonNull
    private Integer lightningDamage=0;

    @NonNull
    private Float   lightningReduction=0f;

    @NonNull
    private Integer magicDamage=0;

    @NonNull
    private Float   magicReduction=0f;

    @NonNull
    private String  name="";

    @NonNull
    private String  path="";

    @NonNull
    private Integer physicalDamage=0;

    @NonNull
    private Float   physicalReduction=0f;

    @NonNull
    private Integer stability=0;

    @NonNull
    private String  strengthBonus="";

}
