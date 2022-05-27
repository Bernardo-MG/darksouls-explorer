
package com.bernardomg.darksouls.explorer.item.shield.domain.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DefaultShieldRequest implements ShieldRequest {

    @NonNull
    private String name = "";

}
