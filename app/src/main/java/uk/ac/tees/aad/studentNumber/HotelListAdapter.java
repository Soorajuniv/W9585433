package uk.ac.tees.aad.studentNumber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.ViewHolder> {

    private List<Hotel> mHotels;
    private OnItemClickListener mListener;
    static String API_KEY="EdyUycAcjqv2nYua4GPAHHBnAVzVV56t";
    static String API_Secret="4xWCpddwLf39KMkd";

    public HotelListAdapter(List<Hotel> hotels) {
        mHotels = hotels;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;
        public TextView mAddressTextView;
        public ImageView mImageView;
        public TextView mRatingTextView;
        public TextView mPriceTextView;


        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.hotel_name);
            mAddressTextView = itemView.findViewById(R.id.hotel_address);
            mImageView = itemView.findViewById(R.id.hotel_image);
            mRatingTextView=itemView.findViewById(R.id.hotel_rating);
            mPriceTextView=itemView.findViewById(R.id.hotel_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotel currentItem = mHotels.get(position);

        holder.mNameTextView.setText(currentItem.getName());
        holder.mAddressTextView.setText(currentItem.getAddress());
        holder.mRatingTextView.setText(currentItem.getRating());
        holder.mPriceTextView.setText(currentItem.getPricePerNight());
        holder.mImageView.setImageResource(currentItem.getImageResource());
    }

    @Override
    public int getItemCount() {
        return mHotels.size();
    }

    public static void uploadData(View view){



        //        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody.create(mediaType, "client_id=EdyUycAcjqv2nYua4GPAHHBnAVzVV56t&client_secret=4xWCpddwLf39KMkd&grant_type=client_credentials");
//        Request request = new Request.Builder()
//                .url("https://test.api.amadeus.com/v1/security/oauth2/token")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//        Response response = client.newCall(request).execute();

//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody.create(mediaType, "client_id=EdyUycAcjqv2nYua4GPAHHBnAVzVV56t&client_secret=4xWCpddwLf39KMkd&grant_type=client_credentials");
//        Request request = new Request.Builder()
//                .url("https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-city?cityCode=PAR")
//                .method("GET", body)
//                .addHeader("Authorization", "Bearer cduCIrTX3mXB0Z8LOFfC3kHh17Ik")
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//        Response response = client.newCall(request).execute();


//        DocumentReference docref=fstore.collection("data").document(fUser.getUid()).collection("myFood").document();
//        Map<String, Object> map=new HashMap<>();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        Date date = new Date();
//        String url="https://api.calorieninjas.com/v1/nutrition?query="+food_name;
//        String url="https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-hotels";
//        Toast.makeText(view.getContext(), "Clicked",Toast.LENGTH_SHORT).show();
//
//        RequestQueue queue = Volley.newRequestQueue(view.getContext());
//        JsonObjectRequest jsonObjectRequest = new
//                JsonObjectRequest(Request.Method.GET,url,
//                        new JSONObject(),
//                        new com.android.volley.Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    String server_data = response.toString();
//                                    JSONObject object = new JSONObject(server_data);
//                                    JSONObject obj = (JSONObject) object.getJSONArray("items").get(0);
////                                    map.put("name",food_name);
////                                    map.put("date",dateFormat.format(date));
////                                    map.put("type",type);
//                                    Toast.makeText(view.getContext(), obj.toString(), Toast.LENGTH_LONG).show();
////                                    double calories = Double.parseDouble(obj.getString("calories"));
////                                    map.put("Calories", calories);
////                                    docref.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
////                                        @Override
////                                        public void onSuccess(Void unused) {
////                                            Toast.makeText(view.getContext(), "Food Added", Toast.LENGTH_SHORT).show();
////                                            finish();
////                                            startActivity(getIntent());
////                                        }
////                                    }).addOnFailureListener(new OnFailureListener() {
////                                        @Override
////                                        public void onFailure(@NonNull @NotNull Exception e) {
////                                            Toast.makeText(view.getContext(), "Error, Try Again", Toast.LENGTH_SHORT).show();
////                                        }
////                                    });
//                                } catch (Exception e) {
//                                    Toast.makeText(view.getContext(),"Error, Try Again",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }, new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(view.getContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
////                        params.put("X-Api-Key", key);
//                        params.put("API Key",API_KEY);
//                        params.put("API Secret", API_Secret);
//                        return params;
//
//                    }
//                };
//
//        queue.add(jsonObjectRequest);
    }


}
