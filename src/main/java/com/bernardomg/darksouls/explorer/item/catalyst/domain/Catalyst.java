
package com.bernardomg.darksouls.explorer.item.catalyst.domain;

import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponRequirements;

public interface Catalyst {

    public String getDescription();

    public String getDexterityBonus();

    public Integer getDurability();

    public String getFaithBonus();

    public Integer getFireDamage();

    public Float getFireReduction();

    public Long getId();

    public String getIntelligenceBonus();

    public Integer getLightningDamage();

    public Float getLightningReduction();

    public Integer getMagicDamage();

    public Float getMagicReduction();

    public String getName();

    public Integer getPhysicalDamage();

    public Float getPhysicalReduction();

    public WeaponRequirements getRequirements();

    public String getStrengthBonus();

    public Long getWeight();

}
