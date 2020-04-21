package com.example.mojprojekat.fragmenti;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.example.mojprojekat.R;
import com.example.mojprojekat.adapteri.MessageAdapter;
import com.example.mojprojekat.aktivnosti.EmailsActivity;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.tools.Mokap;

public class MyFragment extends ListFragment {

	public static MyFragment newInstance() {
        return new MyFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        setHasOptionsMenu(true);
		return inflater.inflate(R.layout.map_layout, vg, false);
	}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Message message = Mokap.getMesagges().get(position);

        /*
        * Ako nasoj aktivnosti zelimo da posaljemo nekakve podatke
        * za to ne treba da koristimo konstruktor. Treba da iskoristimo
        * identicnu strategiju koju smo koristili kda smo radili sa
        * fragmentima! Koristicemo Bundle za slanje podataka. Tacnije
        * intent ce formirati Bundle za nas, ali mi treba da pozovemo
        * odgovarajucu putExtra metodu.
        * */
        Intent intent = new Intent(getActivity(), EmailsActivity.class);
        intent.putExtra("from", message.getFrom());
        intent.putExtra("subject", message.getSubject());
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getActivity(), "onActivityCreated()", Toast.LENGTH_SHORT).show();

        //Dodaje se adapter
        MessageAdapter adapter = new MessageAdapter(getActivity());
        setListAdapter(adapter);
    }

    /*
    * Ako zelimo da nasa aktivnost/fragment prikaze ikonice unutar toolbar-a
    * to se odvija u nekoliko koraka. potrebno je da napravimo listu koja
    * specificira koje sve ikonice imamo unutar menija, koje ikone koristimo
    * i da li se uvek prikazuju ili ne. Nakon toga koristeci metdu onCreateOptionsMenu
    * postavljamo ikone na toolbar.
    * */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // ovo korostimo ako je nasa arhitekrura takva da imamo jednu aktivnost
        // i vise fragmentaa gde svaki od njih ima svoj menu unutar toolbar-a
        menu.clear();
        inflater.inflate(R.menu.activity_itememails, menu);
    }

    /*
    * Da bi znali na koju ikonicu je korisnik kliknuo
    * treba da iskoristimo jedinstveni identifikator
    * za svaki element u listi. Metoda onOptionsItemSelected
    * ce vratiti MenuItem na koji je korisnik kliknuo. Ako znamo
    * da ce on vratiti i id, tacno znamo na koji element je korisnik
    * kliknuo, i shodno tome reagujemo.
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_search){
            Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.action_new){
            Toast.makeText(getActivity(), "New", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}