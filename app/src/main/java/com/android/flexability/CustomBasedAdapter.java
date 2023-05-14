package com.android.flexability;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;

public class CustomBasedAdapter extends BaseAdapter {

    Context context;
    ArrayList<Appointment> appointments;
    int type; //type 1 creates all 3 colors of cards, while type 2 creates only black cards
    LayoutInflater inflater;

    public CustomBasedAdapter(Context ctx, String response, int type){
        this.context = ctx;
        this.appointments = parseJson(response);
        this.type = type;
        inflater = LayoutInflater.from(ctx);
    }

    public static ArrayList<Appointment> parseJson(String response) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray appointmentsArray = jsonObject.getJSONArray("appointments");

            for (int i = 0; i < appointmentsArray.length(); i++) {
                JSONObject appointmentObject = appointmentsArray.getJSONObject(i);

                String name = appointmentObject.getString("name");
                String surname = appointmentObject.getString("surname");
                String amka = appointmentObject.getString("amka");
                String timestamp = appointmentObject.getString("timestamp").replace(" ", "T");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDateTime ldt = LocalDateTime.parse( timestamp );
                    OffsetDateTime odt = ldt.atOffset( ZoneOffset.UTC );
                    //LocalDate ld = odt.toLocalDate();
                    LocalTime beginTime = odt.toLocalTime();
                    LocalTime endTime = beginTime.plus(1, ChronoUnit.HOURS);
                    String beginTimeString = beginTime.toString();
                    String endTimeString = endTime.toString();
                    String time  = beginTimeString.substring(0,5) + " - " + endTimeString.substring(0,5);
                    Appointment appointment = new Appointment(name, surname, amka, time);
                    appointments.add(appointment);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        if(position == 0 && type == 1) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_custom_list_view_green, parent, false);

            Button btn = (Button) row.findViewById(R.id.button2);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CurrentAppointmentScreenActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        else if(position == 1 && type == 1){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_custom_list_view_orange, parent, false);
        }
        else{
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_custom_list_view_black, parent, false);
        }

        TextView txtView = (TextView)row.findViewById(R.id.textView9);
        txtView.setText(appointments.get(position).getName_() + " " + appointments.get(position).getSurname());
        txtView = (TextView)row.findViewById(R.id.textView11);
        txtView.setText(appointments.get(position).getAmka());
        txtView = (TextView)row.findViewById(R.id.textView7);
        txtView.setText(appointments.get(position).getDate());

        return row;
    }
}
