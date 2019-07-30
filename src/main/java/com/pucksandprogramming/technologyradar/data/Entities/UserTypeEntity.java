package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;

@Entity
@Table(name = "UserTypes")
public class UserTypeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    public UserTypeEntity()
    {

    }

    public Integer getId(){ return this.id;}
    public void setId(Integer value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}
}
