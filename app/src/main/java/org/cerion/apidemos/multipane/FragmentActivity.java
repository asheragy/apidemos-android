package org.cerion.apidemos.multipane;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.cerion.apidemos.R;

/* - Split screen using Fragments, using interface to communicate list->details
   - Activity handles fragment communication and multi vs single pane code
   - Based on http://developer.android.com/guide/components/fragments.html
          and http://android-kaizen.com/2015/01/04/tutorial-multi-pane-fragments/
 */
public class FragmentActivity extends Activity implements OnListItemSelectedListener {

    private boolean mDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_layout);

        // DualPane is based on layout having details fragment (portrait vs landscape orientation)
        View detailsFrame = findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if(mDualPane) //Load first item
            onListItemSelected(0);
    }

    @Override
    public void onListItemSelected(int position) {

        Log.d("Main","onListItemSelected = " + position);
        if(!mDualPane) //No fragment just start new activity
        {
            Intent intent = new Intent();
            intent.setClass(this, DetailsActivity.class);
            intent.putExtra("index", position);
            startActivity(intent);
            return;
        }

        DetailsFragment descriptionFragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);

        if (descriptionFragment != null){
            // If description is available, we are in two pane layout
            // so we call the method in DescriptionFragment to update its content
            Log.d("Main", "Updating current");
            descriptionFragment.updateView(position);
        } else {
            Log.d("Main", "Adding new");
            DetailsFragment detailsFragment = DetailsFragment.newInstance(position);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.details, detailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }



    @Override
    public boolean isDualPane() {
        return mDualPane;
    }
}
