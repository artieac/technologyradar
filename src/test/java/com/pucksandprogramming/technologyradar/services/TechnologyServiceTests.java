package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.TechnologyRepository;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration (locations = "classpath*:/spring/applicationContext*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TechnologyServiceTests {

    @Mock
    TechnologyRepository technologyRepository;

    TechnologyService technologyService;

    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "TestTechnology";
    private static final String TEST_NAME_ROOT = "NameRoot";

    private Technology generateTechnology(Long id, String name){
        Technology retVal = new Technology();
        retVal.setId(id);
        retVal.setCreateDate(new Date());
        retVal.setCreator("");
        retVal.setName(name);
        retVal.setUrl("");
        return retVal;
    }

    private List<Technology> generateTechnologyList(Long listSize, String nameRoot) {
        List<Technology> retVal = new ArrayList<>();

        for(Long i = 0L; i < listSize; i++) {
            retVal.add(this.generateTechnology(i, nameRoot + i));
        }

        return retVal;
    }

    @Before
    public void setUp(){
        this.technologyService = new TechnologyService(this.technologyRepository);

        Mockito.when(this.technologyRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(this.generateTechnology(TEST_ID, TEST_NAME)));
        Mockito.when(this.technologyRepository.findByFilters(TEST_NAME_ROOT, "", -1L, -1L)).thenReturn(Optional.ofNullable(this.generateTechnologyList(3L, TEST_NAME_ROOT)));
    }

    @Test
    public void test_findById(){
        Optional<Technology> foundItem = this.technologyService.findById(TEST_ID);

        assertNotNull(foundItem);
        assertTrue(foundItem.isPresent());
        assertTrue(foundItem.get().getId().equals(TEST_ID));
    }

    @Test
    public void test_searchTechnology_name(){
        List<Technology> foundItem = this.technologyService.searchTechnology(TEST_NAME_ROOT, "", -1L, -1L);

        assertNotNull(foundItem);
        assertTrue(foundItem.size() > 0);
    }
}
