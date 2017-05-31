package com.example.thomas.menu;

/**
 * Created by thomas on 01/05/17.
 */
//Classe permettant de créer l'objet Contact, afin d'en faire une liste par la suite
public class Contact {
    private String pseudo;
    private String num;
    public Contact(String pseudo, String num){
        this.pseudo=pseudo;
        this.num= num;
    }
    public String getPseudo(){return this.pseudo;}
    public String getNum(){
        return this.num;
    }
}