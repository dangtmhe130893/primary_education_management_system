package com.primary_education_system.test_service;

import com.primary_education_system.service.TuitionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TuitionServiceTest {

    @Autowired
    private TuitionService tuitionService;

    @Test
    public void testRepo() {
        Assert.assertNull(tuitionService.detail(1L));
    }

}
