package com.example.thomas.menu;

/**
 * Created by thomas on 01/05/17.
 */

public class BDDContact {

  private String pseudo;

  private String num;



  public BDDContact(String pseudo, String num) {

    this.pseudo = pseudo;

    this.num = num;


  }


  public String getPseudo() {

    return pseudo;

  }


  public void setPseudo(String pseudo) {

    this.pseudo = pseudo;

  }


  public String getNum() {

    return num;

  }


  public void setNum(String num) {

    this.num = num;

  }
}
