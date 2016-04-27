package com.myfragmentapplication_test;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import utils.UtilityClass;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoaderSampleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoaderSampleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoaderSampleFragment extends ListFragment implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    String mCurFilter;
    SimpleCursorAdapter simpleCursorAdapter;
    Context mContext;

    public LoaderSampleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoaderSampleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoaderSampleFragment newInstance(String param1, String param2) {
        LoaderSampleFragment fragment = new LoaderSampleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loader_sample, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachWithContext(context);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        onAttachWithContext(context);
    }

    private void onAttachWithContext(Context context) {
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //setEmptyText("No phone numbers");

        setHasOptionsMenu(true);

        simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2, null,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.CONTACT_STATUS},
                new int[]{android.R.id.text1,android.R.id.text2},0);
        setListAdapter(simpleCursorAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
      /*  MenuItem item = menu.add("Search");
        item.setIcon(android.R.drawable.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView searchView = new SearchView(getActivity());
        searchView.setOnQueryTextListener(this);
        item.setActionView(searchView);

        MenuItem itemadd = menu.add("Add");
        itemadd.setIcon(android.R.drawable.ic_menu_add);
        itemadd.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/



        //To remove clear the options menu item from the activity
        //menu.clear();
        //To infalte the new options menu
        // inflater.inflate(R.menu.loader_menu,menu);

        //restoreActionBar();

        MenuItem searchItem = menu.findItem(R.id.item_search_action);
       // SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) searchItem.getActionView();
      //  SearchableInfo searchableInfo = searchManager.getSearchableInfo(getActivity().getComponentName());
        //searchView.setSearchableInfo(searchableInfo);
        searchView.setOnQueryTextListener(this);
      //  searchView.setFocusable(true);

        // searchView.setIconifiedByDefault(false);
       MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //searchView.setQuery("An",true);
                return true;

            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchView.setIconified(true);

                return true;
            }
        });

    }


    private void restoreActionBar() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Loader Contacts");



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_add){
            UtilityClass.showToastMessage(getActivity(),"Add Action button");
        }
        return super.onOptionsItemSelected(item);
    }

    //static final String[] CONTACTS_SUMMARY_PROJECTION

    @Override
    public CursorLoader onCreateLoader(int id, Bundle args) {
        UtilityClass.showToastMessage(getActivity(),"onCreateLoader");
        Uri baseUri;
        if(mCurFilter != null){
            baseUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI,Uri.encode(mCurFilter));
        }else{
            baseUri = ContactsContract.Contacts.CONTENT_URI;
        }

        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + ContactsContract.Contacts.DISPLAY_NAME +" != ''))";
        CursorLoader loader = new CursorLoader(getActivity(),baseUri,null,select,null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

        return loader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        UtilityClass.showToastMessage(getActivity(),"onLoadFinished");
        simpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        UtilityClass.showToastMessage(getActivity(),"onLoaderReset");
        simpleCursorAdapter.swapCursor(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;
        getLoaderManager().restartLoader(0,null,this);
        return true;
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
