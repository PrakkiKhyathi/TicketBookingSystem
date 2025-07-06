package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserBookingService {
    private User user1;
    private ObjectMapper objectMapper=new ObjectMapper();
    private List<User> userList;
    private static final String USER_PATH="app/src/main/java/ticket/booking/localdb/users.json";
    public UserBookingService(User user) throws IOException {
        this.user1=user;
        loadUsers();
    }
    public UserBookingService() throws IOException {
        loadUsers();
    }
    public List<User> loadUsers() throws IOException {
        File users=new File(USER_PATH);
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }
    public Boolean loginUser()
    {
        Optional<User> optional= userList.stream().filter(user->{
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user1.getPassword(),user.getHashedPassword());
        }).findFirst();
        return optional.isPresent();
    }
    public Boolean signUp(User user1)
    {
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }
        catch (IOException ex)
        {
            return Boolean.FALSE;
        }

    }

    private void saveUserListToFile() throws IOException {
        File userFile=new File(USER_PATH);
        objectMapper.writeValue(userFile,userList);
    }
    // Json --> object (User) --> Deserialize
    //Object ---> Json --> Serialize
    public void fetchBooking()
    {
        user1.printTickets();
    }
    public Boolean cancelBooking(Ticket ticketId) {
        try {
            List<Ticket> tickets = user1.getTicketsBooked();
            List<Ticket> filteredTickets = tickets.stream().filter(ticket -> !(ticket.getTicketId().equals(ticketId)))
                    .collect(Collectors.toList());
            user1.setTicketsBooked(filteredTickets);
            Optional<User> optional= userList.stream().filter(user -> user.getUserId().equals(user1.getUserId())).findFirst();
            if(optional.isPresent())
            {
                User user=optional.get();
                user.setTicketsBooked(filteredTickets);
                saveUserListToFile();
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        catch (IOException ex)
        {
            return Boolean.FALSE;
        }
    }
}

