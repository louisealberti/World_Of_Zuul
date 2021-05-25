import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;

    /**
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
    }
    
    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Define as saídas dessa sala. Cada direção ou
     * leva a outra sala ou é nula (nenhuma saída é encontrada).
     */
    public void setExits(Room north, Room east, Room south, Room west)
    {
        if (north != null)
            exits.put("north", north);
        if (east != null)
            exits.put("east", east);
        if (south != null)
            exits.put("south", south);
        if (west!= null)
            exits.put("west", west);
    }
    
    /**
     * Retorna a sala que é alcançada se sairmos desta
     * sala na direção, retorna nulo.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * Define uma saída da sala atual.
     * @param direction A direção da saída.
     * @param neighbor A classe room na direção especificada.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Retorna uma descrição das saídas da sala,
     * por exemplo, "Exits: north west".
     * @return Uma descrição das saídas disponíveis.
     */
    public String getExitsString()
    {
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
     * Retorna uma descrição longa desse quarto, na forma:
     *     You are in the kitchen.
     *     Exits: north west
     * @return Uma descrição do quarto, incluido saídas.
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitsString();
    }
    
    /**
     * Define itens na sala atual.
     * 
     * @param name O nome do item.
     * @param description A descrição do item.
     * @param weight O peso do item.
     */
    public void setItem(String name, String description, int weight) 
    {
        Item item = new Item(name, description, weight);
        items.add(item);
    }
    
    /**
     * Retornar um ArrayList com os itens da sala
     * 
     * @return items O ArrayList com os itens existentes na sala.
     */
    public ArrayList<Item> getItems()
    {
        return items;
    }
    
    /**
     * Adicionar item ao ArrayList.
     * 
     * @param item O item que será adicionado.
     */
    public void addItem(Item item) {
        items.add(item);
    }
    
    /**
     * Remover item  do ArrayList.
     * 
     * @param item O item que será removido.
     */
    public void removeItem(Item item) {
        items.remove(item);
    }
}
