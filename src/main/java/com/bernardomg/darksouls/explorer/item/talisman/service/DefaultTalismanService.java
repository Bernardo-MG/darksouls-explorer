
package com.bernardomg.darksouls.explorer.item.talisman.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.talisman.domain.DtoTalisman;
import com.bernardomg.darksouls.explorer.item.talisman.domain.PersistentTalisman;
import com.bernardomg.darksouls.explorer.item.talisman.domain.Talisman;
import com.bernardomg.darksouls.explorer.item.talisman.domain.TalismanSummary;
import com.bernardomg.darksouls.explorer.item.talisman.repository.TalismanRepository;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponBonus;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamage;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamageReduction;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponRequirements;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

@Service
public final class DefaultTalismanService implements TalismanService {

    private final TalismanRepository repository;

    @Autowired
    public DefaultTalismanService(final TalismanRepository repo) {
        super();

        repository = Objects.requireNonNull(repo);
    }

    @Override
    public final PageIterable<? extends TalismanSummary>
            getAll(final Pagination pagination, final Sort sort) {
        final Pageable pageable;
        final Page<TalismanSummary> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAllSummaries(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Talisman> getOne(final Long id) {
        final Optional<PersistentTalisman> read;
        final PersistentTalisman entity;
        final Optional<? extends Talisman> result;
        final DtoTalisman weapon;
        final DtoWeaponRequirements requirements;
        final DtoWeaponDamage damage;
        final DtoWeaponDamageReduction damageReduction;
        final DtoWeaponBonus bonus;

        read = repository.findById(id);

        if (read.isPresent()) {
            weapon = new DtoTalisman();
            entity = read.get();

            weapon.setId(id);
            weapon.setName(entity.getName());
            weapon.setDescription(entity.getDescription());
            weapon.setDurability(entity.getDurability());
            weapon.setWeight(entity.getWeight());
            weapon.setType(entity.getType());

            requirements = new DtoWeaponRequirements();
            requirements.setDexterity(entity.getDexterity());
            requirements.setFaith(entity.getFaith());
            requirements.setIntelligence(entity.getIntelligence());
            requirements.setStrength(entity.getStrength());
            weapon.setRequirements(requirements);

            damage = new DtoWeaponDamage();
            damage.setFire(entity.getFireDamage());
            damage.setLightning(entity.getLightningDamage());
            damage.setMagic(entity.getMagicDamage());
            damage.setPhysical(entity.getPhysicalDamage());
            damage.setCritical(entity.getCriticalDamage());
            weapon.setDamage(damage);

            damageReduction = new DtoWeaponDamageReduction();
            damageReduction.setFire(entity.getFireReduction());
            damageReduction.setLightning(entity.getLightningReduction());
            damageReduction.setMagic(entity.getMagicReduction());
            damageReduction.setPhysical(entity.getPhysicalReduction());
            weapon.setDamageReduction(damageReduction);

            bonus = new DtoWeaponBonus();
            bonus.setDexterity(entity.getDexterityBonus());
            bonus.setFaith(entity.getFaithBonus());
            bonus.setIntelligence(entity.getIntelligenceBonus());
            bonus.setStrength(entity.getStrengthBonus());
            weapon.setBonus(bonus);

            result = Optional.of(weapon);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
