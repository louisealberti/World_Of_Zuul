
/**
 * Criar itens para cumprir as missões.
 *
 * @author Louise
 * @version 09.05.2020
 */
public class Item
{
    private String name;
    private String description;
    private int weight;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, int weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }
    
    /**
     * Informar o nome do item.
     * @return name O nome do item.
     */
    public String  getName()
    {
        return name;
    }
    
    /**
     * Determinar nome do item.
     * @param name O nome do item.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Informar o peso do item.
     * @return weight O peso do item
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Determinar o peso do item.
     * @return weight O peso do item.
     */
    public void setWeight(int weight)
    {
        this.weight = weight;
    }
    
    /**
     * Informar a descrição do item.
     * @return description A descrição do item.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Determinar descrição do item.
     * @param description A descrição do item.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
}
