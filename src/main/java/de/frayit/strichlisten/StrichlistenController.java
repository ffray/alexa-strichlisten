package de.frayit.strichlisten;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/strichlisten/api")
public class StrichlistenController {

    private Strichliste strichliste;

    public StrichlistenController(Strichliste strichliste) {
        this.strichliste = Objects.requireNonNull(strichliste);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Strichliste getAlleStriche() {
        return strichliste;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{name}")
    public Integer getStricheVon(@PathVariable("name") String name) {
        return strichliste.stricheVon(name);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{name}")
    public Integer setzeStrich(@PathVariable("name") String name) {
        strichliste.setzeStrichBei(name);
        return strichliste.stricheVon(name);
    }
}
