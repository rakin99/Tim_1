package com.example.mojprojekat.adapteri;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mojprojekat.R;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.DateUtil;

import java.text.ParseException;

/*
* Adapteri unutar Android-a sluze da prikazu unapred nedefinisanu kolicinu podataka
* pristigle sa interneta ili ucitane iz baze ili filesystem-a uredjaja.
* Da bi napravili adapter treba da napraivmo klasu, koja nasledjuje neki od postojecih adaptera.
* Za potrebe ovih vezbi koristicemo BaseAdapter koji je sposoban da kao izvor podataka iskoristi listu ili niz.
* Nasledjivanjem bilo kog adaptera, dobicemo
* nekolkko metoda koje moramo da referinisemo da bi adapter ispravno radio.
* */
public class MessageAdapter extends BaseAdapter{
    private Activity activity;

    public MessageAdapter(Activity activity) {
        this.activity = activity;
    }

    /*
    * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
    * */
    @Override
    public int getCount() {
        return Data.messages.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Override
    public Object getItem(int position) {
        return Data.messages.get(position);
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
        View vi=convertView;
        Message message = Data.messages.get(position);

        if(convertView==null)
            vi = activity.getLayoutInflater().inflate(R.layout.messages_list, null);

        TextView from = (TextView)vi.findViewById(R.id.from);
        TextView subject = (TextView)vi.findViewById(R.id.subject);
        TextView content = (TextView)vi.findViewById(R.id.content);
        TextView date = (TextView)vi.findViewById(R.id.date);
        System.out.println("\n"+message.getId()+"Poruka je: ");
        System.out.println(message.isActive());
        if(message.isUnread()){
            System.out.println("\n\nPoruka nije procitana!<-----------------\n\n");
            from.setTypeface(null, Typeface.BOLD_ITALIC);
            subject.setTypeface(null, Typeface.BOLD_ITALIC);
            content.setTypeface(null, Typeface.BOLD_ITALIC);
            date.setTypeface(null, Typeface.BOLD_ITALIC);

            from.setText(message.getFrom());
            subject.setText(String.valueOf(message.getSubject()));
            if(message.getContent().length()>30){
                content.setText(message.getContent().toString().substring(0,30)+"...");
            }else {
                content.setText(message.getContent());
            }
            try {
                date.setText(DateUtil.formatTimeWithSecond(message.getDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(!message.isUnread()){
            System.out.println("\n\nPoruka procitana!<-----------------\n\n");
            from.setTypeface(null, Typeface.NORMAL);
            subject.setTypeface(null, Typeface.NORMAL);
            content.setTypeface(null, Typeface.NORMAL);
            date.setTypeface(null, Typeface.NORMAL);

            from.setText(message.getFrom());
            subject.setText(String.valueOf(message.getSubject()));
            if(message.getContent().length()>30){
                content.setText(message.getContent().toString().substring(0,30)+"...");
            }else {
                content.setText(message.getContent());
            }
            try {
                date.setText(DateUtil.formatTimeWithSecond(message.getDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



        return  vi;
    }
}
