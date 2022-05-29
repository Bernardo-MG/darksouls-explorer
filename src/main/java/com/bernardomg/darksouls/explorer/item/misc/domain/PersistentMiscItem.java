
package com.bernardomg.darksouls.explorer.item.misc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity(name = "MiscItem")
@Table(name = "misc_items")
public final class PersistentMiscItem implements MiscItem {

    @NonNull
    @Column(name = "description", nullable = false)
    private String description = "";

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long   id          = -1l;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name        = "";

}
