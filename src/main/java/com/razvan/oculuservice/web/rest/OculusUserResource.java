package com.razvan.oculuservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.razvan.oculuservice.domain.OculusEvent;
import com.razvan.oculuservice.domain.OculusUser;
import com.razvan.oculuservice.repository.OculusEventRepository;
import com.razvan.oculuservice.repository.OculusUserRepository;
import com.razvan.oculuservice.web.rest.dto.OculusUserEventsDTO;
import com.razvan.oculuservice.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing OculusUser.
 */
@RestController
@RequestMapping("/api")
public class OculusUserResource {

    private final Logger log = LoggerFactory.getLogger(OculusUserResource.class);

    @Inject
    private OculusUserRepository oculusUserRepository;

    @Inject
    private OculusEventRepository oculusEventRepository;

    /**
     * POST  /oculusUsers -> Create a new oculusUser.
     */
    @RequestMapping(value = "/oculusUsers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OculusUser oculusUser) throws URISyntaxException {
        log.debug("REST request to save OculusUser : {}", oculusUser);
        if (oculusUser.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new oculusUser cannot already have an ID").build();
        }
        oculusUserRepository.save(oculusUser);
        return ResponseEntity.created(new URI("/api/oculusUsers/" + oculusUser.getId())).build();
    }

    /**
     * PUT  /oculusUsers -> Updates an existing oculusUser.
     */
    @RequestMapping(value = "/oculusUsers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OculusUser oculusUser) throws URISyntaxException {
        log.debug("REST request to update OculusUser : {}", oculusUser);
        if (oculusUser.getId() == null) {
            return create(oculusUser);
        }
        oculusUserRepository.save(oculusUser);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /oculusUsers -> get all the oculusUsers.
     */
    @RequestMapping(value = "/oculusUsers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OculusUser>> getAll(@RequestParam(value = "page", required = false) Integer offset,
                                                   @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OculusUser> page = oculusUserRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oculusUsers", offset, limit);
        return new ResponseEntity<List<OculusUser>>(page.getContent(), headers, HttpStatus.OK);
    }

    /*
     POST /oculusUsersWithEvents -> set some events by an oculus user.
     */
    @RequestMapping(value = "/oculusUsersWithEvents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> createOrUpdateUserWithEvents(@Valid @RequestBody OculusUserEventsDTO oculusUserEvents)
        throws URISyntaxException {
        OculusUser oculusUser = oculusUserEvents.getOculusUser();

        if (oculusUser.getFirst_name() == null || oculusUser.getFamily_name() == null)
            return ResponseEntity.badRequest().header("Failure", "A oculusUser must have a first name and family name").build();

        OculusUser oculusUserAux = oculusUserRepository.findOneByFirstNameAndFamilyName
            (oculusUser.getFirst_name(), oculusUser.getFamily_name());

        if (oculusUserAux == null) {
            oculusUserAux = oculusUserRepository.save(oculusUser);
        } else {
            oculusUser.setId(oculusUserAux.getId());
            oculusUserRepository.save(oculusUserAux);
        }

        for (OculusEvent oculusEvent : oculusUserEvents.getOculusEventList()) {
            oculusEvent.setOculusUser(oculusUserAux);
            oculusEventRepository.save(oculusEvent);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET  /oculusUsers/:id -> get the "id" oculusUser.
     */
    @RequestMapping(value = "/oculusUsers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OculusUser> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get OculusUser : {}", id);
        OculusUser oculusUser = oculusUserRepository.findOne(id);
        if (oculusUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(oculusUser, HttpStatus.OK);
    }

    /**
     * DELETE  /oculusUsers/:id -> delete the "id" oculusUser.
     */
    @RequestMapping(value = "/oculusUsers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OculusUser : {}", id);
        oculusUserRepository.delete(id);
    }
}
