
package com.bernardomg.darksouls.explorer.item.model;

import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DefaultItemRequest implements ItemRequest {

    @NonNull
    private String       name = "";

    @NonNull
    private List<String> tags = Collections.emptyList();

}
