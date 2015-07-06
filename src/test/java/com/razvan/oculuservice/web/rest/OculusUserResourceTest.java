package com.razvan.oculuservice.web.rest;

import com.razvan.oculuservice.Application;
import com.razvan.oculuservice.domain.OculusUser;
import com.razvan.oculuservice.repository.OculusUserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OculusUserResource REST controller.
 *
 * @see OculusUserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OculusUserResourceTest {

    private static final String DEFAULT_FIRST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_FAMILY_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FAMILY_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SEX = "SAMPLE_TEXT";
    private static final String UPDATED_SEX = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Integer DEFAULT_SMOKING_TIME = 0;
    private static final Integer UPDATED_SMOKING_TIME = 1;

    private static final Integer DEFAULT_TIME_SINCE_LAST_CIGARETTE = 0;
    private static final Integer UPDATED_TIME_SINCE_LAST_CIGARETTE = 1;

    @Inject
    private OculusUserRepository oculusUserRepository;

    private MockMvc restOculusUserMockMvc;

    private OculusUser oculusUser;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OculusUserResource oculusUserResource = new OculusUserResource();
        ReflectionTestUtils.setField(oculusUserResource, "oculusUserRepository", oculusUserRepository);
        this.restOculusUserMockMvc = MockMvcBuilders.standaloneSetup(oculusUserResource).build();
    }

    @Before
    public void initTest() {
        oculusUser = new OculusUser();
        oculusUser.setFirst_name(DEFAULT_FIRST_NAME);
        oculusUser.setFamily_name(DEFAULT_FAMILY_NAME);
        oculusUser.setSex(DEFAULT_SEX);
        oculusUser.setAge(DEFAULT_AGE);
        oculusUser.setSmoking_time(DEFAULT_SMOKING_TIME);
        oculusUser.setTime_since_last_cigarette(DEFAULT_TIME_SINCE_LAST_CIGARETTE);
    }

    @Test
    @Transactional
    public void createOculusUser() throws Exception {
        int databaseSizeBeforeCreate = oculusUserRepository.findAll().size();

        // Create the OculusUser
        restOculusUserMockMvc.perform(post("/api/oculusUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusUser)))
                .andExpect(status().isCreated());

        // Validate the OculusUser in the database
        List<OculusUser> oculusUsers = oculusUserRepository.findAll();
        assertThat(oculusUsers).hasSize(databaseSizeBeforeCreate + 1);
        OculusUser testOculusUser = oculusUsers.get(oculusUsers.size() - 1);
        assertThat(testOculusUser.getFirst_name()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testOculusUser.getFamily_name()).isEqualTo(DEFAULT_FAMILY_NAME);
        assertThat(testOculusUser.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testOculusUser.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testOculusUser.getSmoking_time()).isEqualTo(DEFAULT_SMOKING_TIME);
        assertThat(testOculusUser.getTime_since_last_cigarette()).isEqualTo(DEFAULT_TIME_SINCE_LAST_CIGARETTE);
    }

    @Test
    @Transactional
    public void checkFirst_nameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(oculusUserRepository.findAll()).hasSize(0);
        // set the field null
        oculusUser.setFirst_name(null);

        // Create the OculusUser, which fails.
        restOculusUserMockMvc.perform(post("/api/oculusUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusUser)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OculusUser> oculusUsers = oculusUserRepository.findAll();
        assertThat(oculusUsers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkFamily_nameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(oculusUserRepository.findAll()).hasSize(0);
        // set the field null
        oculusUser.setFamily_name(null);

        // Create the OculusUser, which fails.
        restOculusUserMockMvc.perform(post("/api/oculusUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusUser)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OculusUser> oculusUsers = oculusUserRepository.findAll();
        assertThat(oculusUsers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkSexIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(oculusUserRepository.findAll()).hasSize(0);
        // set the field null
        oculusUser.setSex(null);

        // Create the OculusUser, which fails.
        restOculusUserMockMvc.perform(post("/api/oculusUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusUser)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OculusUser> oculusUsers = oculusUserRepository.findAll();
        assertThat(oculusUsers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(oculusUserRepository.findAll()).hasSize(0);
        // set the field null
        oculusUser.setAge(null);

        // Create the OculusUser, which fails.
        restOculusUserMockMvc.perform(post("/api/oculusUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusUser)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OculusUser> oculusUsers = oculusUserRepository.findAll();
        assertThat(oculusUsers).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllOculusUsers() throws Exception {
        // Initialize the database
        oculusUserRepository.saveAndFlush(oculusUser);

        // Get all the oculusUsers
        restOculusUserMockMvc.perform(get("/api/oculusUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(oculusUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].first_name").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].family_name").value(hasItem(DEFAULT_FAMILY_NAME.toString())))
                .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].smoking_time").value(hasItem(DEFAULT_SMOKING_TIME)))
                .andExpect(jsonPath("$.[*].time_since_last_cigarette").value(hasItem(DEFAULT_TIME_SINCE_LAST_CIGARETTE)));
    }

    @Test
    @Transactional
    public void getOculusUser() throws Exception {
        // Initialize the database
        oculusUserRepository.saveAndFlush(oculusUser);

        // Get the oculusUser
        restOculusUserMockMvc.perform(get("/api/oculusUsers/{id}", oculusUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(oculusUser.getId().intValue()))
            .andExpect(jsonPath("$.first_name").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.family_name").value(DEFAULT_FAMILY_NAME.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.smoking_time").value(DEFAULT_SMOKING_TIME))
            .andExpect(jsonPath("$.time_since_last_cigarette").value(DEFAULT_TIME_SINCE_LAST_CIGARETTE));
    }

    @Test
    @Transactional
    public void getNonExistingOculusUser() throws Exception {
        // Get the oculusUser
        restOculusUserMockMvc.perform(get("/api/oculusUsers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOculusUser() throws Exception {
        // Initialize the database
        oculusUserRepository.saveAndFlush(oculusUser);

		int databaseSizeBeforeUpdate = oculusUserRepository.findAll().size();

        // Update the oculusUser
        oculusUser.setFirst_name(UPDATED_FIRST_NAME);
        oculusUser.setFamily_name(UPDATED_FAMILY_NAME);
        oculusUser.setSex(UPDATED_SEX);
        oculusUser.setAge(UPDATED_AGE);
        oculusUser.setSmoking_time(UPDATED_SMOKING_TIME);
        oculusUser.setTime_since_last_cigarette(UPDATED_TIME_SINCE_LAST_CIGARETTE);
        restOculusUserMockMvc.perform(put("/api/oculusUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusUser)))
                .andExpect(status().isOk());

        // Validate the OculusUser in the database
        List<OculusUser> oculusUsers = oculusUserRepository.findAll();
        assertThat(oculusUsers).hasSize(databaseSizeBeforeUpdate);
        OculusUser testOculusUser = oculusUsers.get(oculusUsers.size() - 1);
        assertThat(testOculusUser.getFirst_name()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testOculusUser.getFamily_name()).isEqualTo(UPDATED_FAMILY_NAME);
        assertThat(testOculusUser.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testOculusUser.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testOculusUser.getSmoking_time()).isEqualTo(UPDATED_SMOKING_TIME);
        assertThat(testOculusUser.getTime_since_last_cigarette()).isEqualTo(UPDATED_TIME_SINCE_LAST_CIGARETTE);
    }

    @Test
    @Transactional
    public void deleteOculusUser() throws Exception {
        // Initialize the database
        oculusUserRepository.saveAndFlush(oculusUser);

		int databaseSizeBeforeDelete = oculusUserRepository.findAll().size();

        // Get the oculusUser
        restOculusUserMockMvc.perform(delete("/api/oculusUsers/{id}", oculusUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OculusUser> oculusUsers = oculusUserRepository.findAll();
        assertThat(oculusUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
