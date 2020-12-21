package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import com.pucksandprogramming.technologyradar.security.TechRadarSecurityPrincipal;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarAccessManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration (locations = "classpath*:/spring/applicationContext*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RadarAccessManagerTests {

    @Spy
    @InjectMocks
    private RadarAccessManager radarAccessManager = new RadarAccessManager();

    public RadarAccessManagerTests(){}

    private RadarUser generateRadarUser(Long radarUserId, int roleId) {
        RadarUser retVal = new RadarUser();
        retVal.setRoleId(roleId);
        retVal.setAuthenticationId("");
        retVal.setAuthority("");
        retVal.setEmail("");
        retVal.setId(radarUserId);
        return retVal;
    }

    @Before
    public void setUp(){
        Mockito.when(this.radarAccessManager.getAuthenticatedUser()).thenReturn(Optional.of(new AuthenticatedUser(this.generateRadarUser(1L, 1))));
    }

    @Test
    public void test_sameUserAdmin(){
        assertTrue(this.radarAccessManager.canModifyRadar(this.generateRadarUser(1L, 1)));
    }

    @Test
    public void test_sameUserNonAdmin() {
        Mockito.when(this.radarAccessManager.getAuthenticatedUser()).thenReturn(Optional.of(new AuthenticatedUser(this.generateRadarUser(2L, 0))));
        assertTrue(this.radarAccessManager.canModifyRadar(this.generateRadarUser(2L, 0)));
    }

    @Test
    public void test_differentUserWithAdmin(){
        assertTrue(this.radarAccessManager.canModifyRadar(this.generateRadarUser(2L, 0)));
    }

    @Test
    public void test_differentUser(){
        Mockito.when(this.radarAccessManager.getAuthenticatedUser()).thenReturn(Optional.of(new AuthenticatedUser(this.generateRadarUser(1L, 0))));
        assertFalse(this.radarAccessManager.canModifyRadar(this.generateRadarUser(2L, 0)));
    }
}
