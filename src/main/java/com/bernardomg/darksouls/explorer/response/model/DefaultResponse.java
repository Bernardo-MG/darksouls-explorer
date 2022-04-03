/**
 * Copyright 2021 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.darksouls.explorer.response.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Default implementation of the response.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 * @param <T>
 *            response content type
 */
@Data
public class DefaultResponse<T> implements Response<T> {

    /**
     * Response content.
     */
    @NonNull
    private T content;

    /**
     * Default constructor.
     */
    public DefaultResponse() {
        super();
    }

    /**
     * Constructs a response with the specified content.
     *
     * @param cont
     *            content
     */
    public DefaultResponse(@NonNull final T cont) {
        super();

        content = cont;
    }

}
