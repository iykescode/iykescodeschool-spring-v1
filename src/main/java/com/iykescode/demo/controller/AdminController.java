package com.iykescode.demo.controller;

import com.iykescode.demo.model.Class;
import com.iykescode.demo.model.Course;
import com.iykescode.demo.model.Person;
import com.iykescode.demo.repository.ClassRepository;
import com.iykescode.demo.repository.CoursesRepository;
import com.iykescode.demo.repository.PersonRepository;
import com.iykescode.demo.service.GetModelAndViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final ClassRepository classRepository;

    private final PersonRepository personRepository;

    private final CoursesRepository coursesRepository;

    private final GetModelAndViewService getModelAndViewService;

    @Autowired
    public AdminController(ClassRepository classRepository, PersonRepository personRepository, CoursesRepository coursesRepository, GetModelAndViewService getModelAndViewService) {
        this.classRepository = classRepository;
        this.personRepository = personRepository;
        this.coursesRepository = coursesRepository;
        this.getModelAndViewService = getModelAndViewService;
    }

    @RequestMapping(value = "/displayClasses", method = { RequestMethod.GET })
    public ModelAndView displayClasses(@RequestParam(value = "deleteSuccess", required = false) String deleteSuccess,
                                       @RequestParam(value = "addSuccess", required = false) String addSuccess) {
        List<Class> classes = classRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("user/classes");
        modelAndView.addObject("class", new Class());
        modelAndView.addObject("classes", classes);

        if (deleteSuccess != null) {
            modelAndView.addObject("message", "Student deleted successfully!!");
            modelAndView.addObject("cssClass", "alert alert-info");
        } else if (addSuccess != null) {
            modelAndView.addObject("message", "Student added successfully!!");
            modelAndView.addObject("cssClass", "alert alert-success");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/addNewClass", method = { RequestMethod.POST })
    public ModelAndView addNewClass(@ModelAttribute("class") Class aClass) {
        classRepository.save(aClass);
        return new ModelAndView("redirect:/admin/displayClasses?addSuccess=true");
    }

    @RequestMapping(value = "/deleteClass/{classId}", method = { RequestMethod.DELETE })
    public ModelAndView deleteClass(@PathVariable int classId) {
        Optional<Class> aClass = classRepository.findById(classId);
        for(Person person : aClass.get().getPersons()){
            person.setAClass(null);
            personRepository.save(person);
        }
        classRepository.deleteById(classId);
        return new ModelAndView("redirect:/admin/displayClasses?deleteSuccess=true");
    }

    @RequestMapping(value = "/displayStudents/{classId}", method = { RequestMethod.GET })
    public ModelAndView displayStudents(@PathVariable int classId,
                                        HttpSession session,
                                        @RequestParam(value = "error", required = false) String error,
                                        @RequestParam(value = "deleteSuccess", required = false) String deleteSuccess,
                                        @RequestParam(value = "addSuccess", required = false) String addSuccess) {

        ModelAndView modelAndView = new ModelAndView("user/students");
        Optional<Class> aClass = classRepository.findById(classId);
        modelAndView.addObject("class", aClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("class", aClass.get());

        return getModelAndViewService.adminController(error, deleteSuccess, addSuccess, modelAndView);
    }

    @RequestMapping(value = "/addStudent", method = { RequestMethod.POST })
    public ModelAndView addStudent(@ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Class aClass = (Class) session.getAttribute("class");
        Person personEntity = personRepository.readByEmail(person.getEmail());

        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayStudents/"+aClass.getClassId() +"?error=true");
            return modelAndView;
        }

        personEntity.setAClass(aClass);
        personRepository.save(personEntity);

        aClass.getPersons().add(personEntity);
        classRepository.save(aClass);

        modelAndView.setViewName("redirect:/admin/displayStudents/"+aClass.getClassId()+"?addSuccess=true");
        return modelAndView;
    }

    @RequestMapping(value = "/deleteStudent/{personId}", method = { RequestMethod.DELETE })
    public ModelAndView deleteStudent(@PathVariable int personId, HttpSession session) {
        Class aClass = (Class) session.getAttribute("class");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setAClass(null);
        aClass.getPersons().remove(person.get());
        Class aClassSaved = classRepository.save(aClass);
        session.setAttribute("class",aClassSaved);
        return new ModelAndView("redirect:/admin/displayStudents/"+aClass.getClassId()+"?deleteSuccess=true");
    }

    @RequestMapping(value = "/displayCourses", method = { RequestMethod.GET })
    public ModelAndView displayCourses() {
        List<Course> courses = coursesRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("user/courses");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Course());
        return modelAndView;
    }

    @RequestMapping(value = "/addNewCourse", method = { RequestMethod.POST })
    public ModelAndView addNewCourse(@ModelAttribute("course") Course course) {
        ModelAndView modelAndView = new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @RequestMapping(value = "/viewStudents/{id}", method = { RequestMethod.GET })
    public ModelAndView viewStudents(@PathVariable int id,
                                     HttpSession session,
                                     @RequestParam(value = "error", required = false) String error,
                                     @RequestParam(value = "deleteSuccess", required = false) String deleteSuccess,
                                     @RequestParam(value = "addSuccess", required = false) String addSuccess) {
        ModelAndView modelAndView = new ModelAndView("user/course_students");
        Optional<Course> courses = coursesRepository.findById(id);
        modelAndView.addObject("courses", courses.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("courses", courses.get());

        return getModelAndViewService.adminController(error, deleteSuccess, addSuccess, modelAndView);
    }

    @RequestMapping(value = "/addStudentToCourse", method = { RequestMethod.POST })
    public ModelAndView addStudentToCourse(@ModelAttribute("person") Person person,
                                           HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Course courses = (Course) session.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());

        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/viewStudents/"+courses.getCourseId()+"?error=true");
            return modelAndView;
        }

        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses", courses);

        modelAndView.setViewName("redirect:/admin/viewStudents/"+courses.getCourseId()+"?addSuccess=true");
        return modelAndView;
    }

    @RequestMapping(value = "/deleteStudentFromCourse/{personId}", method = { RequestMethod.DELETE })
    public ModelAndView deleteStudentFromCourse(@PathVariable int personId,
                                                HttpSession session) {
        Course courses = (Course) session.getAttribute("courses");
        Optional<Person> person = personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);
        personRepository.save(person.get());
        session.setAttribute("courses",courses);
        return new ModelAndView("redirect:/admin/viewStudents/"+courses.getCourseId()+"?deleteSuccess=true");
    }
}
