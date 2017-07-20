package de.frayit.strichlisten;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Strichliste {

    private Map<String, Integer> stricheProPerson;

    public Strichliste() {
        stricheProPerson = new HashMap<>();
    }

    public Integer stricheVon(String name) {
        return stricheProPerson.get(name);
    }

    public void setzeStrichBei(String name) {
        stricheProPerson.compute(name, (String einName, Integer alteStriche) -> {
            return alteStriche == null ? 1 : alteStriche + 1;
        });
    }

    public Integer alleStriche() {
        AtomicInteger summe = new AtomicInteger();
        stricheProPerson.forEach((name, striche) -> {
            summe.addAndGet(striche);
        });

        return summe.get();
    }
}
