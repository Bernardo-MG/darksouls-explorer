
package com.bernardomg.darksouls.explorer.item.armor.domain.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DefaultArmorRequest implements ArmorRequest {

    @NonNull
    private String name = "";

}
