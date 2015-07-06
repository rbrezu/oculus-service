package com.razvan.oculuservice.web.rest;

import com.razvan.oculuservice.Application;
import com.razvan.oculuservice.domain.OculusEvent;
import com.razvan.oculuservice.repository.OculusEventRepository;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OculusEventResource REST controller.
 *
 * @see OculusEventResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OculusEventResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_TYPE = "UPDATED_TEXT";
    private static final String DEFAULT_LOCATION = "SAMPLE_TEXT";
    private static final String UPDATED_LOCATION = "UPDATED_TEXT";

    //private static final DateTime DEFAULT_ENTERED_TIME = new DateTime(0L, DateTimeZone.UTC);
    //private static final DateTime UPDATED_ENTERED_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    //private static final String DEFAULT_ENTERED_TIME_STR = dateTimeFormatter.print(DEFAULT_ENTERED_TIME);

    //private static final DateTime DEFAULT_EXIT_TIME = new DateTime(0L, DateTimeZone.UTC);
    //private static final DateTime UPDATED_EXIT_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    //private static final String DEFAULT_EXIT_TIME_STR = dateTimeFormatter.print(DEFAULT_EXIT_TIME);

    private static final Integer DEFAULT_DURATION = 0;
    private static final Integer UPDATED_DURATION = 1;

    @Inject
    private OculusEventRepository oculusEventRepository;

    private MockMvc restOculusEventMockMvc;

    private OculusEvent oculusEvent;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OculusEventResource oculusEventResource = new OculusEventResource();
        ReflectionTestUtils.setField(oculusEventResource, "oculusEventRepository", oculusEventRepository);
        this.restOculusEventMockMvc = MockMvcBuilders.standaloneSetup(oculusEventResource).build();
    }

    @Before
    public void initTest() {
        oculusEvent = new OculusEvent();
        oculusEvent.setName(DEFAULT_NAME);
        oculusEvent.setType(DEFAULT_TYPE);
        oculusEvent.setLocation(DEFAULT_LOCATION);
        //oculusEvent.setEntered_time(DEFAULT_ENTERED_TIME);
        //oculusEvent.setExit_time(DEFAULT_EXIT_TIME);
        oculusEvent.setDuration(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createOculusEvent() throws Exception {
        int databaseSizeBeforeCreate = oculusEventRepository.findAll().size();

        // Create the OculusEvent
        restOculusEventMockMvc.perform(post("/api/oculusEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusEvent)))
                .andExpect(status().isCreated());

        // Validate the OculusEvent in the database
        List<OculusEvent> oculusEvents = oculusEventRepository.findAll();
        assertThat(oculusEvents).hasSize(databaseSizeBeforeCreate + 1);
        OculusEvent testOculusEvent = oculusEvents.get(oculusEvents.size() - 1);
        assertThat(testOculusEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOculusEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOculusEvent.getLocation()).isEqualTo(DEFAULT_LOCATION);
        //assertThat(testOculusEvent.getEntered_time().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_ENTERED_TIME);
        //assertThat(testOculusEvent.getExit_time().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_EXIT_TIME);
        assertThat(testOculusEvent.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(oculusEventRepository.findAll()).hasSize(0);
        // set the field null
        oculusEvent.setName(null);

        // Create the OculusEvent, which fails.
        restOculusEventMockMvc.perform(post("/api/oculusEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusEvent)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OculusEvent> oculusEvents = oculusEventRepository.findAll();
        assertThat(oculusEvents).hasSize(0);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(oculusEventRepository.findAll()).hasSize(0);
        // set the field null
        oculusEvent.setType(null);

        // Create the OculusEvent, which fails.
        restOculusEventMockMvc.perform(post("/api/oculusEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusEvent)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OculusEvent> oculusEvents = oculusEventRepository.findAll();
        assertThat(oculusEvents).hasSize(0);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(oculusEventRepository.findAll()).hasSize(0);
        // set the field null
        oculusEvent.setLocation(null);

        // Create the OculusEvent, which fails.
        restOculusEventMockMvc.perform(post("/api/oculusEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusEvent)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OculusEvent> oculusEvents = oculusEventRepository.findAll();
        assertThat(oculusEvents).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllOculusEvents() throws Exception {
        // Initialize the database
        oculusEventRepository.saveAndFlush(oculusEvent);

        // Get all the oculusEvents
        restOculusEventMockMvc.perform(get("/api/oculusEvents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(oculusEvent.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                //.andExpect(jsonPath("$.[*].entered_time").value(hasItem(DEFAULT_ENTERED_TIME_STR)))
                //.andExpect(jsonPath("$.[*].exit_time").value(hasItem(DEFAULT_EXIT_TIME_STR)))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)));
    }

    @Test
    @Transactional
    public void getOculusEvent() throws Exception {
        // Initialize the database
        oculusEventRepository.saveAndFlush(oculusEvent);

        // Get the oculusEvent
        restOculusEventMockMvc.perform(get("/api/oculusEvents/{id}", oculusEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(oculusEvent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            //.andExpect(jsonPath("$.entered_time").value(DEFAULT_ENTERED_TIME_STR))
            //.andExpect(jsonPath("$.exit_time").value(DEFAULT_EXIT_TIME_STR))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION));
    }

    @Test
    @Transactional
    public void getNonExistingOculusEvent() throws Exception {
        // Get the oculusEvent
        restOculusEventMockMvc.perform(get("/api/oculusEvents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOculusEvent() throws Exception {
        // Initialize the database
        oculusEventRepository.saveAndFlush(oculusEvent);

		int databaseSizeBeforeUpdate = oculusEventRepository.findAll().size();

        // Update the oculusEvent
        oculusEvent.setName(UPDATED_NAME);
        oculusEvent.setType(UPDATED_TYPE);
        oculusEvent.setLocation(UPDATED_LOCATION);
        //oculusEvent.setEntered_time(UPDATED_ENTERED_TIME);
        //oculusEvent.setExit_time(UPDATED_EXIT_TIME);
        oculusEvent.setDuration(UPDATED_DURATION);
        restOculusEventMockMvc.perform(put("/api/oculusEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oculusEvent)))
                .andExpect(status().isOk());

        // Validate the OculusEvent in the database
        List<OculusEvent> oculusEvents = oculusEventRepository.findAll();
        assertThat(oculusEvents).hasSize(databaseSizeBeforeUpdate);
        OculusEvent testOculusEvent = oculusEvents.get(oculusEvents.size() - 1);
        assertThat(testOculusEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOculusEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOculusEvent.getLocation()).isEqualTo(UPDATED_LOCATION);
        //assertThat(testOculusEvent.getEntered_time().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_ENTERED_TIME);
        //assertThat(testOculusEvent.getExit_time().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_EXIT_TIME);
        assertThat(testOculusEvent.getDuration()).isEqualTo(UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void deleteOculusEvent() throws Exception {
        // Initialize the database
        oculusEventRepository.saveAndFlush(oculusEvent);

		int databaseSizeBeforeDelete = oculusEventRepository.findAll().size();

        // Get the oculusEvent
        restOculusEventMockMvc.perform(delete("/api/oculusEvents/{id}", oculusEvent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OculusEvent> oculusEvents = oculusEventRepository.findAll();
        assertThat(oculusEvents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
