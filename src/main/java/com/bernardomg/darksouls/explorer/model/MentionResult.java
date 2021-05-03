/**
 * Copyright 2020 the original author or authors
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

package com.bernardomg.darksouls.explorer.model;

public final class MentionResult implements Mention {

    private String mention;

    private String mentioned;

    private String mentioner;

    public MentionResult() {
        super();
    }

    @Override
    public String getMention() {
        return mention;
    }

    @Override
    public String getMentioned() {
        return mentioned;
    }

    @Override
    public String getMentioner() {
        return mentioner;
    }

    public void setMention(final String mention) {
        this.mention = mention;
    }

    public void setMentioned(final String name) {
        this.mentioned = name;
    }

    public void setMentioner(final String source) {
        this.mentioner = source;
    }

}
