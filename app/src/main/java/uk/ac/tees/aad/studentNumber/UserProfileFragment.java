package uk.ac.tees.aad.studentNumber;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class UserProfileFragment extends Fragment {
    private TextView userNameTextView;
    private TextView emailTextView;
    private ImageView walletIcon;
    private TextView currentBalanceTextView;
    private RecyclerView bookingHistoryRecyclerView;
//    private List<Booking> bookingList;
    private double currentBalance = 0;


    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        initViews(view);
        setData(user);
        setRecyclerView();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // Initialize Firebase database reference
//        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // Set click listener for "Add Amount" button
        walletIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a dialog box to ask for the amount to be added
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Amount");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int amountToAdd = Integer.parseInt(input.getText().toString());
//                        Toast.makeText(getContext(), " "+currentBalance,Toast.LENGTH_LONG).show();
                        Map<String, Object> map=new HashMap<>();
                        map.put("Balance",amountToAdd+currentBalance);
                        userRef.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
//                                currentBalanceTextView.setText(getString(R.string.current_balance_label,
//                                        String.valueOf(amountToAdd+currentBalance)));
                                currentBalance+=amountToAdd;
                                Toast.makeText(getContext(), "Amount added successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        return view;
    }
    private void initViews(View view) {
        userNameTextView = view.findViewById(R.id.user_name_textview);
        emailTextView = view.findViewById(R.id.email_textview);
        walletIcon = view.findViewById(R.id.wallet_icon);
        currentBalanceTextView = view.findViewById(R.id.current_balance_textview);
        bookingHistoryRecyclerView = view.findViewById(R.id.booking_history_recyclerview);
    }



    private void setData(FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(user!=null){
            DocumentReference userRef = db.collection("users").document(user.getUid());
            userNameTextView.setText(user.getDisplayName());
            emailTextView.setText(user.getEmail());
            userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        if (snapshot.getDouble("Balance") != null) {
                            currentBalance = snapshot.getDouble("Balance");
                        }
                        currentBalanceTextView.setText(getString(R.string.current_balance_label,String.valueOf(currentBalance)));
                    }
                }
            });
        }
    }


    private void setRecyclerView() {
        // set up the booking history recycler view
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(new Booking("Hotel A", 2, 150.0, "2023-03-15", "2023-03-18"));
        bookingList.add(new Booking("Hotel B", 1, 100.0, "2023-03-20", "2023-03-22"));
        bookingList.add(new Booking("Hotel C", 3, 200.0, "2023-03-25", "2023-03-28"));
        bookingList.add(new Booking("Hotel A", 2, 150.0, "2023-03-15", "2023-03-18"));
        bookingList.add(new Booking("Hotel B", 1, 100.0, "2023-03-20", "2023-03-22"));
        bookingList.add(new Booking("Hotel C", 3, 200.0, "2023-03-25", "2023-03-28"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        BookingAdapter bookingAdapter = new BookingAdapter(bookingList);
        bookingHistoryRecyclerView.setLayoutManager(layoutManager);
        bookingHistoryRecyclerView.setAdapter(bookingAdapter);
    }

}
