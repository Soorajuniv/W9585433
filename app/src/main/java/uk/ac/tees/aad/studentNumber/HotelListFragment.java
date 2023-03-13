package uk.ac.tees.aad.studentNumber;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HotelListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HotelListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotelListFragment newInstance(String param1, String param2) {
        HotelListFragment fragment = new HotelListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private HotelListAdapter mAdapter;
    private List<Hotel> mHotels;


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
        View view = inflater.inflate(R.layout.fragment_hotel_list, container, false);

        mRecyclerView = view.findViewById(R.id.hotel_list_recycler_view);

        // set layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // create hotel list
        mHotels = new ArrayList<Hotel>();
        mHotels.add(new Hotel("The Ritz-Carlton", "Los Angeles", "$$$", "4.7", R.drawable.hotel1));
        mHotels.add(new Hotel("The Plaza", "New York City", "$$$", "4.5", R.drawable.hotel1));
        mHotels.add(new Hotel("The Langham", "Chicago", "$$$", "4.3", R.drawable.hotel1));
        mHotels.add(new Hotel("Wynn Las Vegas", "Las Vegas", "$$$", "4.6", R.drawable.hotel1));
        mHotels.add(new Hotel("The Savoy", "London", "$$$", "4.5", R.drawable.hotel1));
        mHotels.add(new Hotel("Burj Al Arab", "Dubai", "$$$$", "4.9", R.drawable.hotel1));
        mHotels.add(new Hotel("The Peninsula", "Paris", "$$$$", "4.7", R.drawable.hotel1));
        mHotels.add(new Hotel("Park Hyatt", "Sydney", "$$$$", "4.8", R.drawable.hotel1));

        // create adapter and set it to recycler view
        mAdapter = new HotelListAdapter(mHotels);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}