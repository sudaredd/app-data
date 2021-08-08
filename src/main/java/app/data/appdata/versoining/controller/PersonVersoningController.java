package app.data.appdata.versoining.controller;

import app.data.appdata.versoning.model.Name;
import app.data.appdata.versoning.model.PersonV1;
import app.data.appdata.versoning.model.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersoningController {

    @GetMapping("v1/person")
    public PersonV1 personv1() {
        return new PersonV1("Sudar Kasi");
    }

    //this method is for the second version that returns firstName and lastName separately
    @GetMapping("v2/person")
    public PersonV2 personv2() {
        PersonV2 personV2 = new PersonV2(new Name("Sudar", "Kasi"));
        return personV2;

    }

    //this method is for first version that returns the entire name
    @GetMapping(value = "/person/param", params = "version=1")
    public PersonV1 personV1() {
        return new PersonV1("Tom Cruise");
    }

    //this method is for second version that returns firstName and lastName separately
    @GetMapping(value = "/person/param", params = "version=2")
    public PersonV2 personV2() {
        return new PersonV2(new Name("Tom", "Cruise"));
    }
}
