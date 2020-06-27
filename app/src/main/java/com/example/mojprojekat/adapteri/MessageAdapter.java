package com.example.mojprojekat.adapteri;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mojprojekat.R;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.DateUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/*
* Adapteri unutar Android-a sluze da prikazu unapred nedefinisanu kolicinu podataka
* pristigle sa interneta ili ucitane iz baze ili filesystem-a uredjaja.
* Da bi napravili adapter treba da napraivmo klasu, koja nasledjuje neki od postojecih adaptera.
* Za potrebe ovih vezbi koristicemo BaseAdapter koji je sposoban da kao izvor podataka iskoristi listu ili niz.
* Nasledjivanjem bilo kog adaptera, dobicemo
* nekolkko metoda koje moramo da referinisemo da bi adapter ispravno radio.
* */
public class MessageAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Message> messages;
    private Filter filter;
    private ArrayList<Message> filterMessages;

    public MessageAdapter(@NonNull Context context, ArrayList<Message> messages) {
        super(context,0, messages);
        this.context=context;
        this.messages= messages;
        this.filterMessages= new ArrayList(messages);
    }

    public List<Message> getMessages() {
        return messages;
    }

    /*
    * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
    * */
    @Override
    public int getCount() {
        return messages.size();
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

    @Override
    public Object getItem(int position) {
        return messages.get(position);
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
        Log.d("U messageAdapteru sam","<----------------------------------------");
        Log.d("Broj poruka: ",String.valueOf(getCount())
        );
        View vi=convertView;
        Message message = messages.get(position);

        if(convertView==null)
            vi = LayoutInflater.from(context).inflate(R.layout.messages_list,parent,false);

        TextView from = (TextView)vi.findViewById(R.id.from);
        TextView subject = (TextView)vi.findViewById(R.id.subject);
        TextView content = (TextView)vi.findViewById(R.id.content);
        TextView date = (TextView)vi.findViewById(R.id.date);
        //System.out.println("------------------------------------------------------------------------------------\n");
        //System.out.println("\n"+message.getId()+"Poruka je: ");
        //System.out.println(message.isUnread()+"u messageAdapter");
        if(message.isUnread()){
            // System.out.println("\n\nPoruka nije procitana!<-----------------\n\n");
            from.setTextColor(Color.BLUE);
            from.setTypeface(null, Typeface.BOLD);
            subject.setTypeface(null, Typeface.BOLD);
            subject.setTextColor(Color.BLUE);
            content.setTypeface(null, Typeface.BOLD);
            content.setTextColor(Color.BLUE);
            date.setTypeface(null, Typeface.BOLD);
            date.setTextColor(Color.BLUE);

            from.setText(message.getFrom());
            subject.setText(String.valueOf(message.getSubject()));
            if(message.getContent().length()>30){
                content.setText(message.getContent().substring(0,30)+"...");
            }else {
                content.setText(message.getContent());
            }
            try {
                date.setText(DateUtil.formatTimeWithSecond(message.getDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(!message.isUnread()){
            //System.out.println("\n\nPoruka procitana!<-----------------\n\n");

            from.setTextColor(Color.rgb(9,83,66));
            subject.setTextColor(Color.DKGRAY);
            content.setTextColor(Color.BLACK);
            date.setTextColor(Color.LTGRAY);

            from.setText(message.getFrom());
            subject.setText(String.valueOf(message.getSubject()));
            if(message.getContent().length()>30){
                content.setText(message.getContent().substring(0,30)+"...");
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

    @Override
    public Filter getFilter()
    {
        if (filter == null)
            filter = new MessageFilter();

        return filter;
    }

    private class MessageFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix == null || prefix.length() == 0)
            {
                Activity activity=(Activity) context;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String username=sharedPreferences.getString(context.getString(R.string.login1),"Nema ulogovanog!");
                String sort = sharedPreferences.getString(context.getString(R.string.sort), "DESC");
                try {
                    Data.readMessages(activity,sort,username);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ArrayList<Message> list = new ArrayList<Message>(messages);
                results.values = list;
                results.count = list.size();
            }
            else
            {
                final ArrayList<Message> list = new ArrayList<Message>(messages);
                final ArrayList<Message> nlist = new ArrayList<Message>();
                int count = list.size();

                for (int i=0; i<count; i++)
                {
                    final Message m = list.get(i);

                    if (m.getContent().contains(constraint) || m.getSubject().contains(constraint) || m.getFrom().contains(constraint) ||
                            m.getTo().contains(constraint) || m.getCc().contains(constraint) || m.getCc().contains(constraint)) {
                        System.out.println("Nasao sam poruku, stavljam je u listu!");
                        nlist.add(m);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterMessages = (ArrayList<Message>)results.values;

            clear();
            int count = filterMessages.size();
            for (int i=0; i<count; i++)
            {
                Message message = (Message)filterMessages.get(i);
                add(message);
            }
            if (filterMessages.size() > 0)
                notifyDataSetChanged();
            else
                notifyDataSetInvalidated();
        }

    }
}
