
package com.bernardomg.darksouls.explorer.item.request;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DefaultItemRequest implements ItemRequest {

    @NonNull
    private String             name      = "";

    @NonNull
    private Collection<String> selectors = new ArrayList<>();

}
