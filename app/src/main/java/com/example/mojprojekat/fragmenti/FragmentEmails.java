package com.example.mojprojekat.fragmenti;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.mojprojekat.R;
import com.example.mojprojekat.aktivnosti.EmailActivity;
import com.example.mojprojekat.database.DBContentProviderEmail;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;

public class FragmentEmails extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static String USER_KEY = "com.example.mojprojekat.USER_KEY";
    private SimpleCursorAdapter adapter;

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

       // Message message = Mokap.getMesagges().get(position);

        /*
        * Ako nasoj aktivnosti zelimo da posaljemo nekakve podatke
        * za to ne treba da koristimo konstruktor. Treba da iskoristimo
        * identicnu strategiju koju smo koristili kda smo radili sa
        * fragmentima! Koristicemo Bundle za slanje podataka. Tacnije
        * intent ce formirati Bundle za nas, ali mi treba da pozovemo
        * odgovarajucu putExtra metodu.
        * */
        Intent intent = new Intent(getActivity(), EmailActivity.class);
        Uri todoUri = Uri.parse(DBContentProviderEmail.CONTENT_URI_EMAIL + "/" + id);
        intent.putExtra("id", todoUri);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Toast.makeText(getActivity(), "onActivityCreated()", Toast.LENGTH_SHORT).show();

        //Dodaje se adapter
        getLoaderManager().initLoader(0, null,  this);
        String[] from = new String[] { ReviewerSQLiteHelper.COLUMN_FROM, ReviewerSQLiteHelper.COLUMN_SUBJECT};
        int[] to = new int[] {R.id.from, R.id.subject};
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.messages_list, null, from,
                to, 0);
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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] allColumns = { ReviewerSQLiteHelper.COLUMN_ID,
                ReviewerSQLiteHelper.COLUMN_FROM, ReviewerSQLiteHelper.COLUMN_TO, ReviewerSQLiteHelper.COLUMN_CC, ReviewerSQLiteHelper.COLUMN_BCC,
                ReviewerSQLiteHelper.COLUMN_DATE_TIME, ReviewerSQLiteHelper.COLUMN_SUBJECT, ReviewerSQLiteHelper.COLUMN_CONTENT};

        return new CursorLoader(getActivity(), DBContentProviderEmail.CONTENT_URI_EMAIL,
                allColumns, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}