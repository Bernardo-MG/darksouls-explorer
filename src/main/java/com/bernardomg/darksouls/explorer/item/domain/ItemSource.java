
package com.bernardomg.darksouls.explorer.item.domain;

public interface ItemSource {

    public String getItem();

    public String getRelationship();

    public String getSource();

    public void setItem(final String item);

    public void setRelationship(final String relationship);

    public void setSource(final String source);

}
