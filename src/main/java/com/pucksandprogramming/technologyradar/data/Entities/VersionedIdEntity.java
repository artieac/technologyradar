package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VersionedIdEntity implements Serializable
{
    @Column(name = "Id")
    private String id;

    @Column(name = "Version")
    private Long version;

    public VersionedIdEntity()
    {

    }

    public VersionedIdEntity(String id, Long version)
    {
        this.id = id;
        this.version = version;
    }

    public String getId() { return this.id;}
    public void setId(String value) { this.id = value;}

    public Long getVersion() { return this.version;}
    public void setVersion(Long value) { this.version = value;}

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (!(o instanceof VersionedIdEntity))
            return false;

        VersionedIdEntity that = (VersionedIdEntity) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getVersion(), that.getVersion());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getVersion());
    }
}