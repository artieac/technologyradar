package com.alwaysmoveforward.technologyradar.data.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by acorrea on 10/18/2016.
 */
@Entity
@Table(name = "Technology", schema = "dbo", catalog = "TechnologyRadar")
public class TechnologyDTO
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    @Column(name = "CreateDate", nullable = false)
    private Date createDate;

    @Column(name = "Creator", nullable = false, length = 255)
    private String creator;

    @Column(name = "Description", nullable = false, length = 255)
    private String description;

    @Column(name = "Url", nullable = false, length = 255)
    private String url;

    public TechnologyDTO()
    {
        this.createDate = new Date();
    }

    public Long getId(){ return this.id;}

    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}

    public void setName(String value) { this.name = value;}

    public Date getCreateDate() { return this.createDate;}

    public void setCreateDate(Date value) { this.createDate = value;}

    public String getCreator() { return this.creator;}

    public void setCreator(String value) { this.creator = value;}

    public String getDescription() { return this.description;}

    public void setDescription(String value) { this.description = value;}

    public String getUrl() { return this.url;}

    public void setUrl(String value) { this.creator = value;}
}

