/**
 * Retorna uma sala aleatória, independente da direção
 *
 * @author Louise
 * @version 13.06.2020
 */
public class TransporterRoom extends Room
{
    private RoomRandomizer randomRoom;

    /**
     * Create a transporter room
     * @param description The room's description.
     */
    public TransporterRoom(String description, RoomRandomizer random)
    {
        super(description);
        randomRoom = random;
    }

    /**
     * Retorna uma sala aleatória, independente da direção
     * @param  direction ignorada.
     * @return    Uma sala aleatória.
     */
    public Room getExit(String direction)
    {
        return randomRoom.findRandomRoom();
    }

}
