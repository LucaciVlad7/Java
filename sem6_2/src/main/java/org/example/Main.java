package org.example;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        String url="jdbc:postgresql://localhost:5432/"; // lipseste baza de date
        String username="postgres";
        String password="BazaDateVlad7";
        UserDBRepository userDBRepository = new UserDBRepository(url,username,password);
        List<User> users=userDBRepository.getAll();
        for(User user:users){
            System.out.println(user);
        }
    }
}
