package de.frayit.strichlisten;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/strichlisten/api")
@Slf4j
public class StrichlistenController {

    private Strichliste strichliste;

    public StrichlistenController(Strichliste strichliste) {
        this.strichliste = Objects.requireNonNull(strichliste);
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map<String, Integer>> getAlleStriche() throws JsonProcessingException {
        log.info("getAlleStriche(): {}", new ObjectMapper().writeValueAsString(strichliste));
        return ResponseEntity.ok(strichliste.getStricheProPerson());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{name}")
    @ResponseBody
    public Integer getStricheVon(@PathVariable("name") String name) {
        log.info("getStricheVon({})", name);
        return strichliste.stricheVon(name);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{name}")
    @ResponseBody
    public Integer setzeStrich(@PathVariable("name") String name) {
        log.info("setzeStrich({})", name);
        strichliste.setzeStrichBei(name);
        return strichliste.stricheVon(name);
    }

}
