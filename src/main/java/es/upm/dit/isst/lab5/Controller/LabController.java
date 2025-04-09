package es.upm.dit.isst.lab5.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LabController {
    @GetMapping("/")
    public String inicio() {
        return "index.html";
    }

    @GetMapping("/alumnos")
    public String alumnos() {
        return "alumnos.html";
    }

    @GetMapping("/profesores")
    public String profesores() {
        return "profesores.html";
    }

    @GetMapping("/todos")
    public String todos() {
        return "todos.html";
    }
}