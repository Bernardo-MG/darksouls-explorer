
package com.bernardomg.darksouls.explorer.item.catalyst.domain.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DefaultCatalystRequest implements CatalystRequest {

    @NonNull
    private String name = "";

}
