package com.pucksandprogramming.technologyradar.domainmodel;

import java.util.Comparator;

public class MostRecentRadarComparator implements Comparator<Radar>
{
    public int compare(Radar a, Radar b)
    {
        return a.getAssessmentDate().compareTo(b.getAssessmentDate());
    }
}

