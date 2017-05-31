package com.example.thomas.menu;

import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
/**
 * Created by thomas on 01/05/17.
 */
//Cette classe est un Adapter pour afficher une liste de Contacts
public class ContactAdapter extends  ArrayAdapter<Contact> {

    public ContactAdapter(Context context, List<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_items,parent, false);
        }

        ContactViewHolder viewHolder = (ContactViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ContactViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Contact> contacts
        Contact contact = getItem(position);
        //Pour afficher le pseudo
        viewHolder.pseudo.setText(contact.getPseudo());


        return convertView;
    }

    private class ContactViewHolder{
        public TextView pseudo;


    }
}

