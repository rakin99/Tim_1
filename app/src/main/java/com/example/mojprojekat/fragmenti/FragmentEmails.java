package com.example.mojprojekat.fragmenti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.fragment.app.ListFragment;

import com.example.mojprojekat.R;
import com.example.mojprojekat.adapteri.MessageAdapter;
import com.example.mojprojekat.aktivnosti.EmailActivity;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.tools.Data;

import java.util.Comparator;

public class FragmentEmails extends ListFragment {

    public static String USER_KEY = "com.example.mojprojekat.USER_KEY";
    public MessageAdapter adapter;
    private String sort;
    private SharedPreferences sharedPreferences;

	public static FragmentEmails newInstance() {
        return new FragmentEmails();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        setHasOptionsMenu(true);
		return inflater.inflate(R.layout.map_layout, vg, false);
	}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Message message = Data.messages.get(position);

        /*
        * Ako nasoj aktivnosti zelimo da posaljemo nekakve podatke
        * za to ne treba da koristimo konstruktor. Treba da iskoristimo
        * identicnu strategiju koju smo koristili kda smo radili sa
        * fragmentima! Koristicemo Bundle za slanje podataka. Tacnije
        * intent ce formirati Bundle za nas, ali mi treba da pozovemo
        * odgovarajucu putExtra metodu.
        * */
        Intent intent = new Intent(getActivity(), EmailActivity.class);
        //Uri todoUri = Uri.parse(DBContentProviderEmail.CONTENT_URI_EMAIL + "/" + id);
        intent.putExtra("id", message.getId());
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sort = sharedPreferences.getString(getString(R.string.sort), "DESC");
        //Toast.makeText(getActivity(), "onActivityCreated()", Toast.LENGTH_SHORT).show();

        //Dodaje se adapter
       /* getLoaderManager().initLoader(0, null,  this);
        String[] from = new String[] { ReviewerSQLiteHelper.COLUMN_FROM, ReviewerSQLiteHelper.COLUMN_SUBJECT, ReviewerSQLiteHelper.COLUMN_CONTENT,ReviewerSQLiteHelper.COLUMN_DATE_TIME};
        int[] to = new int[] {R.id.from, R.id.subject,R.id.date};*/
        adapter = new MessageAdapter(getActivity(),Data.messages);
        setListAdapter(adapter);
        adapter.setNotifyOnChange(true);
        if(sort.equals("DESC")){
            adapter.sort(new Comparator<Message>() {
                @Override
                public int compare(Message a, Message b) {
                    return (b.getDateTime()).compareTo(a.getDateTime());
                }
            });
        }else if(sort.equals("ASC")){
            adapter.sort(new Comparator<Message>() {
                @Override
                public int compare(Message a, Message b) {
                    return (a.getDateTime()).compareTo(b.getDateTime());
                }
            });
        }
        Data.listView=getListView();
        Data.messageAdapter=adapter;
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
        SearchView searchView=(SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText){
                Data.messageAdapter.getFilter().filter(newText);
                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String query){
                Log.d("Final text: ",query);
                return false;
            }
        });
    }

    /*
    * Da bi znali na koju ikonicu je korisnik kliknuo
    * treba da iskoristimo jedinstveni identifikator
    * za svaki element u listi. Metoda onOptionsItemSelected
    * ce vratiti MenuItem na koji je korisnik kliknuo. Ako znamo
    * da ce on vratiti i id, tacno znamo na koji element je korisnik
    * kliknuo, i shodno tome reagujemo.
    * */
   /* @Override
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
    }*/

    /*@NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] allColumns = { ReviewerSQLiteHelper.COLUMN_ID,
                ReviewerSQLiteHelper.COLUMN_FROM, ReviewerSQLiteHelper.COLUMN_TO, ReviewerSQLiteHelper.COLUMN_CC, ReviewerSQLiteHelper.COLUMN_BCC,
                ReviewerSQLiteHelper.COLUMN_DATE_TIME, ReviewerSQLiteHelper.COLUMN_SUBJECT, ReviewerSQLiteHelper.COLUMN_CONTENT};

        return new CursorLoader(getActivity(), DBContentProviderEmail.CONTENT_URI_EMAIL,
                allColumns, null, null, null);
    }*/

   /* @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }*/
}