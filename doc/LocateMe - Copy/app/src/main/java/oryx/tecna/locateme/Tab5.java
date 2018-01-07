package oryx.tecna.locateme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Hasan Yousef on 12/05/2017.
 */

public class Tab5 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


  //  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
    //                         @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.tab5, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        return rootView;
    }
}
