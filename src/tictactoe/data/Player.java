/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.data;

/**
 *
 * The Player class.
 *
 * @author czhiller
 */
public class Player {

    private String name;

    private char identifier;

    private int order;

    /**
     *
     * The player constructor.
     *
     * @param name
     * @param identifier
     * @param order
     */
    public Player(String name, char identifier, int order) {
        this.name = name;
        this.identifier = identifier;
        this.order = order;
    }

    /**
     * 
     * Default constructor.
     * 
     */
    public Player(int order) {
        this.name = "DefaultName";
        this.identifier = ' ';
        this.order = order;
    }

    /**
     *
     * The name getter.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * The name setter.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * The identifier getter.
     *
     * @return
     */
    public char getIdentifier() {
        return identifier;
    }

    /**
     *
     * The identifier setter.
     *
     * @param identifier
     */
    public void setIdentifier(char identifier) {
        this.identifier = identifier;
    }

    /**
     *
     * The order getter.
     *
     * @return
     */
    public int getOrder() {
        return order;
    }

    /**
     *
     * The order setter.
     *
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
    }

}
