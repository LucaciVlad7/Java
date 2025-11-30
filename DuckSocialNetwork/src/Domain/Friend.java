package Domain;
import Domain.entities.User;

import java.time.LocalDateTime;
import java.util.*;

public class Friend extends Entity<Tuple<Long,Long>>{

    LocalDateTime date;

    public Friend(User user1, User user2){
        super.setId(new Tuple<>(user1.getId(),user2.getId()));
        date = LocalDateTime.now();
    }

    public LocalDateTime getDate(){
        return date;
    }
}