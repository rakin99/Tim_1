package com.example.mojprojekat.adapteri;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mojprojekat.R;
import com.example.mojprojekat.model.Contact;

import java.util.List;

/*
 * Adapteri unutar Android-a sluze da prikazu unapred nedefinisanu kolicinu podataka
 * pristigle sa interneta ili ucitane iz baze ili filesystem-a uredjaja.
 * Da bi napravili adapter treba da napraivmo klasu, koja nasledjuje neki od postojecih adaptera.
 * Za potrebe ovih vezbi koristicemo BaseAdapter koji je sposoban da kao izvor podataka iskoristi listu ili niz.
 * Nasledjivanjem bilo kog adaptera, dobicemo
 * nekolkko metoda koje moramo da referinisemo da bi adapter ispravno radio.
 * */

public class ContactAdapter extends BaseAdapter {

    private Activity activity;
    private List<Contact> contacts;


    public ContactAdapter(Activity activity,List<Contact> contacts) {
        this.activity = activity;
        this.contacts = contacts;
    }

    public void updateResults(List<Contact> results) {
        contacts = results;
        //Triggers the list update
        notifyDataSetChanged();
    }

    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return contacts.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }


    /*
     * Ova metoda vraca jedinstveni identifikator, za adaptere koji prikazuju
     * listu ili niz, pozicija je dovoljno dobra. Naravno mozemo iskoristiti i
     * jedinstveni identifikator objekta, ako on postoji.
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * Ova metoda popunjava pojedinacan element ListView-a podacima.
     * Ako adapter cuva listu od n elemenata, adapter ce u petlji ici
     * onoliko puta koliko getCount() vrati. Prilikom svake iteracije
     * uzece java objekat sa odredjene poziciuje (model) koji cuva podatke,
     * i layout koji treba da prikaze te podatke (view) npr R.layout.cinema_list.
     * Kada adapter ima model i view, prosto ce uzeti podatke iz modela,
     * popuniti view podacima i poslati listview da prikaze, i nastavice
     * sledecu iteraciju.
     * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        Contact contact = contacts.get(position);

        if(convertView==null)
            view = activity.getLayoutInflater().inflate(R.layout.contacts_list, null);

        TextView firstName = (TextView)view.findViewById(R.id.firstName);
        TextView lastName = (TextView)view.findViewById(R.id.lastName);
        TextView email = (TextView)view.findViewById(R.id.email);
        System.out.println("\n"+contact.getId()+"Kontakt je: ");
        System.out.println(contact.isActive());

            System.out.println("\n\nKontakt ucitan!<-----------------\n\n");

            firstName.setText(contact.getFirst());
            lastName.setText(contact.getLast());
            email.setText(contact.getEmail());

        return  view;
    }
}
