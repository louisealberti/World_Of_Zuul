import java.util.ArrayList;

/**
 * Guardar as informações sobre o jogador.
 *
 * @author Louise
 * @version 09.05.2020
 */
public class Player
{
    private String name; // nome do jogador
    private int weight; // peso do jogador
    private ArrayList<Item> items; // lista de itens que o jogador carrega
    private Room currentRoom; // sala em que o jogador de encontra
    private int maximumWeight; // peso máximo que o jogador pode carregar

    /**
     * Constructor for objects of class Player
     */
    public Player (Room room) {
        name = "Unknown";
        weight = 0;
        currentRoom = room;
        items = new ArrayList<Item>();
        maximumWeight = 50;
    }
    
    /**
     * Informar o nome jogador
     * 
     * @return name O nome do jogador
     */
    public String getName () {
        return name;
    }
    
    /**
     * Determinar o nome do jogador
     * 
     * @param name O nome do jogador
     */
    public void setName (String name) {
        this.name = name;
    }
    
    /**
     * Informar o peso do jogador.
     * 
     * @return weight O peso do jogador.
     */
    public int getWeight () {
        return weight;
    }
    
    /**
     * Determinar o peso do jogador.
     * 
     * @param weight O peso do jogador.
     */
    public void setWeight (int weight) {
        this.weight = weight;
    }
    
    /**
     * Retorna um ArrayList com os itens carregados pelo jogador.
     * 
     * @return items O ArrayList com os itens carregados pelo 
     * jogador.
     */
    public ArrayList<Item> getItems () {
        return items;
    }
    
    /**
     * Adiciona item à lista.
     * 
     * @param  item Item coletado.
     */
    public int take(Item item)
    {
        if ((weight + item.getWeight()) < maximumWeight) {
            items.add(item);
            weight += item.getWeight();
            System.out.println("Item added");
            return 0;
        } else {
            System.out.println("You cannot take it! You will exceed your maximum weight!!");
            return 1;
        }
    }
    
    /**
     * Colocar o objeto carregado pelo jogador em uma sala.
     * 
     * @param item O item que será deixado na sala.
     */
    public void drop(Item item)
    {
        for (Item name : items) {
            if (name.equals(item)) {
                weight -= item.getWeight();
                items.remove(name);
                currentRoom.addItem(item);
            }
        }
    }
    
    /**
     * Comer item.
     * 
     * @param item O item que será comido.
     */
    public void eat(Item item) {
        weight -= item.getWeight();
        if (item.getName().equals("cookie")) {
            maximumWeight = 100;
            System.out.println("One have eaten a magic cookie!");
            System.out.println("Now one may carry more items!");
            System.out.println("One maximum weight is currently " 
            + maximumWeight);
        } else {
            System.out.println("One ate a delicious " + 
            item.getName() + ", now one does not feel hungry.");
        }
        removeItem(item);
    }
    
    /**
     * Remover um item utilizado.
     * 
     * @param item O item que será removido.
     */
    public void removeItem(Item item) {
            for (Item playerItem : items) {
                
                if (playerItem.getName().equals(item.getName())) {
                    items.remove(item);
                    return;
                }
        }
    }
    
    /**
     * Informar a sala atual do jogador
     * 
     * @return room A sala onde está o jogador
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    /**
     * Determinar a sala onde o jogador está
     * 
     * @param room A sala onde o jogador está
     */
    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }
    
    /**
     * Informar o peso máximo do jogador
     * 
     * @return maximumWeight O peso máximo do jogador
     */
    public int getMaximumWeight() {
        return maximumWeight;
    }
    
    /**
     * Determinar o peso máximo do jogador
     * 
     * @param weight O peso máximo do jogador
     */
    public void setMaximumWeight(int weight) {
        maximumWeight = weight;
    }
}
