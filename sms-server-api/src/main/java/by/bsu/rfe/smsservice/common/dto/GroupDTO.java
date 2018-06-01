package by.bsu.rfe.smsservice.common.dto;

import java.util.List;

/**
 * Created by pluhin on 11/6/16.
 */
public class GroupDTO {
    private Integer id;
    private String name;
    private List<PersonDTO> persons;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PersonDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }
}
