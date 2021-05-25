import java.util.Random;
import java.util.ArrayList;
/**
 * Gera números aleatórios.
 *
 * @author Louise
 * @version 1
 */
public class RoomRandomizer
{
    private Random randomGenerator;
    private ArrayList<Room> roomList;

    /**
     * Construtor para objetos da classe RandomNumber
     */
    public RoomRandomizer()
    {
        // Cria uma nova instância da classe Random
        // e a armazena na variável randomGenerator
        randomGenerator = new Random();
        roomList = new ArrayList<Room>();
    }
    
    /**
     * Adiciona a sala ao ArrayList
     */
    public void addRoom(Room room)
    {
        roomList.add(room);
    }
    
    /**
     * Encontra uma sala aleatória.
     */
    public Room findRandomRoom()
    {
        return roomList.get(randomGenerator.nextInt(roomList.size()));
    }
}
