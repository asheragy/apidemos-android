package org.cerion.apidemos.multipane;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.cerion.apidemos.ListData;
import org.cerion.apidemos.R;

import java.util.List;

public class FragmentA extends ListFragment {

    private List<ListData> mItems;
    private OnListItemSelectedListener mListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Populate with fake data
        mItems = ListData.getList(10);
        //getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
        //getListView().clearChoices();
        //getListView().invalidateViews();

        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, mItems));

        mListener = (OnListItemSelectedListener)getActivity();

        if (mListener.isDualPane()) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            //Workaround to clear previous checked position when reoading
            getListView().clearFocus();
            getListView().post(new Runnable() {
                @Override
                public void run() {
                    //getListView().setSelection(0);
                    getListView().setItemChecked(0, true);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        Log.d("FragmentA","onDestroy()");
        super.onDestroy();


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mListener.onListItemSelected(position);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */

    /*
    void showDetails(int index) {

        if (mDualPane) {
            //Set current item selected
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);

            if (details == null) {
                //Create new fragment instance
                details = DetailsFragment.newInstance(index);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            } else {
                //Update view on existing fragment
                details.updateView(index);
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }
    */
}