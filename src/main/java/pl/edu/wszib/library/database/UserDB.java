package pl.edu.wszib.library.database;

import pl.edu.wszib.library.models.Role;
import pl.edu.wszib.library.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDB {
    private User[] users = new User[2];
    private static final UserDB instance = new UserDB();

    private UserDB() {
    }



    public User findByLogin(String login) {
        for(User user : this.users) {
            if(user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        User[] newUsers = new User[this.users.length + 1];
        for(int i = 0; i < this.users.length; i++) {
            newUsers[i] = this.users[i];
        }
        newUsers[newUsers.length - 1] = user;
        this.users = newUsers;
    }

    public String checkRoleToAdmin(String login) {
        if (this.findByLogin(login) != null) {
            if (this.findByLogin(login).getRole() == Role.ADMIN) {
                return "1";
            }
            this.findByLogin(login).setRole(Role.ADMIN);
            return "0";
        }
        return "2";
    }

    public static UserDB getInstance() {
        return instance;
    }
}
