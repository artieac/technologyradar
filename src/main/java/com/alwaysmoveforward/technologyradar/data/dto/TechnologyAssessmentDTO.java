package com.alwaysmoveforward.technologyradar.data.dto;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
@Entity
@Table(name = "TechnologyAssessments", schema = "dbo", catalog = "TechnologyRadar")
public class TechnologyAssessmentDTO
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    @Column(name = "AssessmentDate", nullable = false)
    private Date assessmentDate;

    @OneToOne
    @JoinColumn(name = "AssessmentTeamId", nullable = false)
    private AssessmentTeamDTO assessmentTeam;

    @OneToMany(mappedBy = "technologyAssessment")
    private List<TechnologyAssessmentItemDTO> technologyAssessments;

    public TechnologyAssessmentDTO()
    {

    }

    public Long getId(){ return this.id;}

    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}

    public void setName(String value) { this.name = value;}

    public Date getAssessmentDate() { return this.assessmentDate;}

    public void setAssessmentDate(Date value) { this.assessmentDate = value;}

    public AssessmentTeamDTO getAssessmentTeam() { return this.assessmentTeam;}

    public void setAssessmentTeam(AssessmentTeamDTO value) { this.assessmentTeam = value;}

    public List<TechnologyAssessmentItemDTO> getTechnologyAssessmentItems() { return this.technologyAssessments;}

    public void setTechnologyAssessmentItems(List<TechnologyAssessmentItemDTO> value) { this.technologyAssessments = value;}

    public TechnologyAssessmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        TechnologyAssessmentDTO retVal = new TechnologyAssessmentDTO();
        retVal.setId(rs.getLong("Id"));
        retVal.setName(rs.getString("Name"));
        retVal.setAssessmentDate(rs.getDate("AssessmentDate"));
        return retVal;
    }
}
