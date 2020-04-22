package pl.javastart.firstmvcapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @ResponseBody
    @GetMapping("/users")
    public String users() {
        List<User> users = userRepository.getAll();

        String result = "";

        for (User user : users) {
            result += user.toString() + "<br/>";
        }

        return result;
    }

    @RequestMapping("/add")
    public String add(@RequestParam(defaultValue = "Anonim", required = false) String imie,
                      @RequestParam String nazwisko,
                      @RequestParam Integer wiek) {
        if ("".equals(imie)) {
            return "redirect:/err.html";
        } else {
            User user = new User(imie, nazwisko, wiek);
            userRepository.add(user);
            return "redirect:/success.html";
        }
    }

    @ResponseBody
    @RequestMapping("/search")
    public String search(@RequestParam(required = false) String imie, @RequestParam(required = false) String nazwisko, @RequestParam(required = false) String wiek) {
        List<User> usersToDisplay = new ArrayList<>();

        if (!"".equals(imie)) {
            usersToDisplay = findUsersByName(imie);
        } else if (!"".equals(nazwisko)) {
            usersToDisplay = findUsersByLastname(nazwisko);
        } else if (!"".equals(wiek)) {
            usersToDisplay = findUsersByAge(wiek);
        } else if ("".equals(imie) && "".equals(nazwisko) && "".equals(wiek))
            return "Nie podano kryteriów wyszukiwania!";

        if (usersToDisplay.isEmpty())
            return "Użytkownik nie istnieje!";
        else {
            return "Znaleziono użytkowników: " + usersToDisplay;
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String delete(@RequestParam(required = false) String imie, @RequestParam(required = false) String nazwisko, @RequestParam(required = false) String wiek) {
        List<User> usersToRemove = new ArrayList<>();

        if (!"".equals(imie)) {
            usersToRemove = findUsersByName(imie);
            removeUsers(usersToRemove);
        } else if (!"".equals(nazwisko)) {
            usersToRemove = findUsersByLastname(nazwisko);
            removeUsers(usersToRemove);
        } else if (!"".equals(wiek)) {
            usersToRemove = findUsersByAge(wiek);
            removeUsers(usersToRemove);
        } else if ("".equals(imie) && "".equals(nazwisko) && "".equals(wiek))
            return "Nie podano kryteriów wyszukiwania!";

        if (usersToRemove.isEmpty())
            return "Użytkownik nie istnieje!";
        else {
            return "Usunięto użytkowników: " + usersToRemove;
        }
    }

    private void removeUsers(List<User> usersToRemove) {
        for (User user : usersToRemove) {
            userRepository.remove(user);
        }
    }

    private List<User> findUsersByName(String name) {
        List<User> users = userRepository.getAll();
        List<User> usersFound = new ArrayList<>();

        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name))
                usersFound.add(user);
        }
        return usersFound;
    }

    private List<User> findUsersByLastname(String lastname) {
        List<User> users = userRepository.getAll();
        List<User> usersFound = new ArrayList<>();

        for (User user : users) {
            if (user.getLastName().equalsIgnoreCase(lastname))
                usersFound.add(user);
        }
        return usersFound;
    }

    private List<User> findUsersByAge(String age) {
        List<User> users = userRepository.getAll();
        List<User> usersFound = new ArrayList<>();

        Integer ageInt = Integer.valueOf(age);
        for (User user : users) {
            if (user.getAge() == (ageInt))
                usersFound.add(user);
        }
        return usersFound;
    }
}

