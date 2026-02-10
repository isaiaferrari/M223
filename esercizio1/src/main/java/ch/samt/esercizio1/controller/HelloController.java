package ch.samt.esercizio1.controller;

import ch.samt.esercizio1.domain.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class HelloController {
    /*
    private final List<User> users = new ArrayList<>(
            Arrays.asList(
                    new User(1L, "Mario", "Rossi")
            ));
    */

    private final Map<Long, User> users = new HashMap<>();

    @GetMapping("/")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/{name}")
    public String helloWithName(Model model,
                                @PathVariable("name") String UserName){
        model.addAttribute("name", UserName);

        return "Hello";
    }

    @GetMapping("/user/insert")
    public String loadinsertPage(@ModelAttribute User user){
        return "insertUser";
    }
    /*
    @PostMapping("/user/insert")
    public String insertUser(@ModelAttribute User user, Model model) {
        users.add(user);
        model.addAttribute("users", users);
        return "userList";
    }
    */
    @PostMapping("/user/insert")
    public String insertUser(@ModelAttribute User user, Model model) {
        //hasmap
        model.addAttribute("users", users);
        return "userList";
    }
}
