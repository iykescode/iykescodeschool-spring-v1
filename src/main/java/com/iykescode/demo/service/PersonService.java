package com.iykescode.demo.service;

import com.iykescode.demo.constants.IykescodeSchoolConstants;
import com.iykescode.demo.model.Person;
import com.iykescode.demo.model.Role;
import com.iykescode.demo.repository.PersonRepository;
import com.iykescode.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createNewPerson(Person person){
        boolean isSaved = false;
        Role role = roleRepository.getByRoleName(IykescodeSchoolConstants.STUDENT_ROLE);
        person.setRole(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);

        if (person.getPersonId() > 0)
        {
            isSaved = true;
        }

        return isSaved;
    }
}
