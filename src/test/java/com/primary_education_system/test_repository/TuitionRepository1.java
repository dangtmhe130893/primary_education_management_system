package com.primary_education_system.test_repository;

import com.primary_education_system.repository.TuitionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TuitionRepository1 {

    @Autowired
    private TuitionRepository tuitionRepository;

    @Test
    public void testRepo() {
        Assert.assertEquals(5, tuitionRepository.findAll().size());
    }

}
