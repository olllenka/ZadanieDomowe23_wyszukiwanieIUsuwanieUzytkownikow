package pl.javastart.firstmvcapp;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
        users.add(new User("Adam", "Kowalski", 20));
        users.add(new User("Basia", "Abacka", 22));
        users.add(new User("Władek", "Wczorajszy", 55));
    }

    public List<User> getAll() {

        return new ArrayList<>(users);
    }

    public void add(User user) {
        users.add(user);
    }

    public void remove(User user){ users.remove(user);}
}
