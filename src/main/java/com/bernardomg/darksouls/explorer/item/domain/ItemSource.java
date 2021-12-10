
package com.bernardomg.darksouls.explorer.item.domain;

public interface ItemSource {

    public String getItem();

    public String getLocation();

    public String getRelationship();

    public String getSource();

    public void setItem(final String item);

    public void setLocation(final String location);

    public void setRelationship(final String relationship);

    public void setSource(final String source);

}
