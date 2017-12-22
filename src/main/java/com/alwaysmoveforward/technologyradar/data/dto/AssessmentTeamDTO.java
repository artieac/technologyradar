package com.alwaysmoveforward.technologyradar.data.dto;

import javax.persistence.*;

/**
 * Created by acorrea on 10/19/2016.
 */
@Entity
@Table(name = "AssessmentTeams", schema = "dbo", catalog = "TechnologyRadar")
public class AssessmentTeamDTO
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    public AssessmentTeamDTO()
    {

    }

    public Long getId(){ return this.id;}

    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}

    public void setName(String value) { this.name = value;}
}
