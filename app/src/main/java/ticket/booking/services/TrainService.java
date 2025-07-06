package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {
    private List<Train> trainList;
    private ObjectMapper objectMapper=new ObjectMapper();
    private static final String TRAIN_PATH="app/src/main/java/ticket/booking/localdb/trains.json";
    TrainService() throws IOException {
        trainList=loadTrains();
    }
    public List<Train> loadTrains() throws IOException {
        File trains=new File(TRAIN_PATH);
        return objectMapper.readValue(trains, new TypeReference<List<Train>>() {});

    }
    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream().filter(train -> validTrain(train,source,destination))
                .collect(Collectors.toList());
    }
    public Boolean validTrain(Train train, String source, String destination) {
       List<String> stations=train.getStations();
       int sourceIdx=stations.indexOf(source.toLowerCase());
       int destinationIdx=destination.indexOf(destination.toUpperCase());
       return sourceIdx!=-1 && destinationIdx!=-1 && sourceIdx<destinationIdx;
    }
}
