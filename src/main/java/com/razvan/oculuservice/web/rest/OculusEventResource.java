package com.razvan.oculuservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.razvan.oculuservice.domain.OculusEvent;
import com.razvan.oculuservice.repository.OculusEventRepository;
import com.razvan.oculuservice.repository.OculusUserRepository;
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
 * REST controller for managing OculusEvent.
 */
@RestController
@RequestMapping("/api")
public class OculusEventResource {

    private final Logger log = LoggerFactory.getLogger(OculusEventResource.class);

    @Inject
    private OculusEventRepository oculusEventRepository;

    @Inject
    private OculusUserRepository oculusUserRepository;

    /**
     * POST  /oculusEvents -> Create a new oculusEvent.
     */
    @RequestMapping(value = "/oculusEvents",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OculusEvent oculusEvent) throws URISyntaxException {
        log.debug("REST request to save OculusEvent : {}", oculusEvent);
        if (oculusEvent.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new oculusEvent cannot already have an ID").build();
        }

        oculusEventRepository.save(oculusEvent);

        return ResponseEntity.created(new URI("/api/oculusEvents/" + oculusEvent.getId())).build();
    }

    /**
     * PUT  /oculusEvents -> Updates an existing oculusEvent.
     */
    @RequestMapping(value = "/oculusEvents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OculusEvent oculusEvent) throws URISyntaxException {
        log.debug("REST request to update OculusEvent : {}", oculusEvent);
        if (oculusEvent.getId() == null) {
            return create(oculusEvent);
        }
        oculusEventRepository.save(oculusEvent);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /oculusEvents -> get all the oculusEvents.
     */
    @RequestMapping(value = "/oculusEvents",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OculusEvent>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OculusEvent> page = oculusEventRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oculusEvents", offset, limit);
        return new ResponseEntity<List<OculusEvent>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oculusEvents/:id -> get the "id" oculusEvent.
     */
    @RequestMapping(value = "/oculusEvents/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OculusEvent> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get OculusEvent : {}", id);
        OculusEvent oculusEvent = oculusEventRepository.findOne(id);
        if (oculusEvent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(oculusEvent, HttpStatus.OK);
    }

    /**
     * DELETE  /oculusEvents/:id -> delete the "id" oculusEvent.
     */
    @RequestMapping(value = "/oculusEvents/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OculusEvent : {}", id);
        oculusEventRepository.delete(id);
    }
}
