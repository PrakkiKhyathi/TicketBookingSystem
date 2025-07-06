package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UserBookingService {
    private User user;
    private ObjectMapper objectMapper=new ObjectMapper();
    private List<User> userList;
    private static final String USER_PATH="app/src/main/java/ticket/booking/localdb/users.json";
    public UserBookingService(User user) throws IOException {
        this.user=user;
        File users=new File(USER_PATH);
        userList=objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }
}
