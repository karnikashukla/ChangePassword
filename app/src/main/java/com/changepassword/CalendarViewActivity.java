package com.changepassword;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarViewActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-YYYY", Locale.getDefault());
    private final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String[] options = {"Add new event", "View all events"};
    CompactCalendarView compactCalendarView;
    SimpleDateFormat sdf;
    TextView tx_date, tx_today;
    LinearLayout ly_left, ly_right;
    Calendar myCalendar;
    Date c;
    SimpleDateFormat df;
    String formattedDate;
    TextView events;
    Button add;
    EditText addEvent;
    SharedPreferences myPref;
    Integer[] day = {10, 20, 21, 25, 27};
    List<Integer> days = new ArrayList<Integer>(Arrays.asList(day));
    Integer[] month = {10, 10, 11, 11, 12};
    List<Integer> months = new ArrayList<Integer>(Arrays.asList(month));
    Integer[] year = {2020, 2020, 2018, 2018, 2018};
    List<Integer> years = new ArrayList<Integer>(Arrays.asList(year));

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        compactCalendarView = findViewById(R.id.calendar);

        init();
        calendarlistener();


        tx_date.setText("" + formattedDate);

        ly_right.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                compactCalendarView.showCalendarWithAnimation();
//                compactCalendarView.showNextMonth();
            }
        });

        ly_left.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                compactCalendarView.showCalendarWithAnimation();
//                compactCalendarView.showPreviousMonth();
            }
        });

        tx_today.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                Intent intent = new Intent(CalendarViewActivity.this, CalendarViewActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    //variable initialization

    public void init() {
        compactCalendarView = findViewById(R.id.compactcalendar_view);
        tx_date = findViewById(R.id.text);
        ly_left = findViewById(R.id.layout_left);
        ly_right = findViewById(R.id.layout_right);
        tx_today = findViewById(R.id.text_today);
        add = findViewById(R.id.btnAdd);
        events = findViewById(R.id.event_data);
        addEvent = findViewById(R.id.add);
        myPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Setdate();
    }

    //calendar method
    public void calendarlistener() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDayClick(Date dateClicked) {
                LocalDate date = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dateClicked));
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CalendarViewActivity.this);
                alertDialog.setTitle("Choose");
                alertDialog.setItems(options, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if ("Add new event".matches(options[i])) {
                            addEvent.setEnabled(true);
                            addEvent.setVisibility(View.VISIBLE);
                            addEvent.setHint("Enter your event details");
                            add.setVisibility(View.VISIBLE);
                            add.setOnClickListener(new View.OnClickListener() {

                                @SuppressLint("ApplySharedPref")
                                @Override
                                public void onClick(View view) {
                                    Log.d("day", Arrays.toString(day));
                                    SharedPreferences.Editor editor = myPref.edit();
                                    SharedPreferences getData = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                    if (getData.getString("date", "date").matches(String.valueOf(dateClicked)) && getData.getString("data", "data") != null) {
                                        editor.putString("date", String.valueOf(dateClicked));
                                        editor.putString("data", getData.getString("data", "data") + "\n" + addEvent.getText().toString());

                                        int dayOfMonth = date.getDayOfMonth();
                                        int monthofYear = date.getMonthValue();
                                        int yearSelected = date.getYear();
                                        days.add(dayOfMonth);
                                        months.add(monthofYear);
                                        years.add(yearSelected);

                                        day = days.toArray(day);
                                        month = months.toArray(month);
                                        year = years.toArray(year);
                                        Log.d("day", Arrays.toString(day));
//                                        myCalendar = Calendar.getInstance()1;
//
//                                        for (int j = 0; j < month.length; j++) {
//
//                                            int mon = Integer.parseInt(month[j]);
//                                            myCalendar.set(Calendar.YEAR, yearSelected);
//                                            myCalendar.set(Calendar.MONTH, monthofYear);
//                                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                                            Event event = new Event(Color.RED, myCalendar.getTimeInMillis(), getData.getString("data","data"));
//                                            compactCalendarView.addEvent(event);
                                        editor.commit();
                                        Setdate();
                                        //compactCalendarView.addEvent(event);
                                    } else {
                                        editor.putString("date", String.valueOf(dateClicked));
                                        editor.putString("data", addEvent.getText().toString());
                                        editor.commit();
                                        int dayOfMonth = date.getDayOfMonth();
                                        int monthofYear = date.getMonthValue();
                                        int yearSelected = date.getYear();
                                        days.add(dayOfMonth);
                                        months.add(monthofYear);
                                        years.add(yearSelected);

                                        day = days.toArray(day);
                                        month = months.toArray(month);
                                        year = years.toArray(year);

                                        Log.d("day", Arrays.toString(day));
                                        Log.d("day", Arrays.toString(month));
                                        Log.d("day", Arrays.toString(year));

                                        compactCalendarView.removeAllEvents();
                                        Setdate();
                                    }
                                    String data = String.valueOf(addEvent.getText());
                                    events.setText("All Events:\n" + data);

                                }
                            });
                        } else if ("View all events".matches(options[i])) {
                            addEvent.setEnabled(false);
                            addEvent.setVisibility(View.INVISIBLE);
//                            addEvent.setHint("Enter your event details");
                            add.setVisibility(View.INVISIBLE);
                            //data.setText("");
                            SharedPreferences getData = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            String date;
                            //currentDate = String.valueOf(calendarView.getSelectedDate());
                            date = getData.getString("date", "date");
                            String data;
                            if (date.matches(String.valueOf(dateClicked))) {
                                data = getData.getString("data", "data");
                                events.setText("All Events:\n" + data);

                            } else {
                                events.setText("No events available!");
                            }
                        }
                    }
                });
                alertDialog.show();

//                if (DateFormat.format(dateClicked).equals("2018-11-21")) {
//                    Toast.makeText(getApplicationContext(), DateFormat.format(dateClicked) + " This day your brother birth day ", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), DateFormat.format(dateClicked) + " In This day no Events Available", Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                compactCalendarView.removeAllEvents();
                Setdate();
                tx_date.setText(simpleDateFormat.format(firstDayOfNewMonth));
            }
        });

    }

    //get current date

    public void Setdate() {
        c = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        sdf = new SimpleDateFormat("MMMM yyyy");
        myCalendar = Calendar.getInstance();

        for (int j = 0; j < month.length; j++) {

            int mon = (month[j]);
            myCalendar.set(Calendar.YEAR, (year[j]));
            myCalendar.set(Calendar.MONTH, mon - 1);
            myCalendar.set(Calendar.DAY_OF_MONTH, (day[j]));
            //    String myDate = +year[j] + "-" + (mon + 1) + "-" + day[j];
            Log.d("Month", String.valueOf(myCalendar));
            Event event = new Event(Color.RED, myCalendar.getTimeInMillis(), "test");
            compactCalendarView.addEvent(event);

        }

    }
}

//    calendarView = findViewById(R.id.calendar);
//        calendarView.setMotionEventSplittingEnabled(true);
//        calendarView.setOnDayClickListener(new OnDayClickListener() {
//            @Override
//            public void onDayClick(EventDay eventDay) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CalendarViewActivity.this);
//                alertDialog.setTitle("Choose");
//                alertDialog.setItems(options, new DialogInterface.OnClickListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if ("Add new event".matches(options[i])) {
//                            addEvent.setEnabled(true);
//                            addEvent.setVisibility(View.VISIBLE);
//                            addEvent.setHint("Enter your event details");
//                            add.setVisibility(View.VISIBLE);
//                            add.setOnClickListener(new View.OnClickListener() {
//
//                                @SuppressLint("ApplySharedPref")
//                                @Override
//                                public void onClick(View view) {
//                                    SharedPreferences.Editor editor = myPref.edit();
//                                    SharedPreferences getData = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//                                    if (getData.getString("date","date").matches(String.valueOf(currentDate)) && getData.getString("data","data")!= null){
//                                        editor.putString("date", String.valueOf(currentDate));
//                                        editor.putString("data", getData.getString("data","data")+"\n"+addEvent.getText().toString());
//                                        editor.commit();
////                                        Intent i = new Intent(Intent.ACTION_EDIT);
////                                        i.setType("vnd.android.cursor.item/event");
////                                        i.putExtra("beginTime", calendar.getTimeInMillis());
////                                        i.putExtra("allDay", true);
////                                        i.putExtra("rule", "FREQ=YEARLY");
////                                        i.putExtra("endTime", calendar.getTimeInMillis() + 60 * 60 * 1000);
////                                        i.putExtra("title", "Calendar Event");
////                                        startActivity(i);
//                                    }else{
//                                        editor.putString("date", String.valueOf(currentDate));
//                                        editor.putString("data", addEvent.getText().toString());
//                                        editor.commit();
//                                    }
//
//                                    events = String.valueOf(addEvent.getText());
//                                    data.setText("All Events:\n" + events);
//                                    Event event = new Event(Color.argb(255,169,255,0),calendar.getTimeInMillis(),"Event at : "+ new Date(calendar.getTimeInMillis()));
//                                }
//                            });
//                        } else if ("View all events".matches(options[i])) {
//                            addEvent.setEnabled(false);
//                            addEvent.setVisibility(View.INVISIBLE);
////                            addEvent.setHint("Enter your event details");
//                            add.setVisibility(View.INVISIBLE);
//                            //data.setText("");
//                            SharedPreferences getData = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//                            String date;
//                            currentDate = String.valueOf(calendarView.getSelectedDate());
//                            date = getData.getString("date", "date");
//                            if (date.matches(String.valueOf(currentDate))) {
//                                events = getData.getString("data", "data");
//                                data.setText("All Events:\n" + events);
//                            } else {
//                                data.setText("No events available!");
//                            }
//                        }
//                    }
//                });
//                alertDialog.show();
//                eventDays.add(new EventDay(calendar,R.drawable.dot));
//                calendarView.setEvents(eventDays);
//            }
//        });
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
//                currentDate = (year+month+dayOfMonth);
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CalendarViewActivity.this);
//                alertDialog.setTitle("Choose");
//                alertDialog.setItems(options, new DialogInterface.OnClickListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if ("Add new event".matches(options[i])) {
//                            addEvent.setEnabled(true);
//                            addEvent.setVisibility(View.VISIBLE);
//                            addEvent.setHint("Enter your event details");
//                            add.setVisibility(View.VISIBLE);
//                            add.setOnClickListener(new View.OnClickListener() {
//
//                                @SuppressLint("ApplySharedPref")
//                                @Override
//                                public void onClick(View view) {
//                                    SharedPreferences.Editor editor = myPref.edit();
//                                    SharedPreferences getData = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//                                    if (getData.getString("date","date").matches(String.valueOf(currentDate)) && getData.getString("data","data")!= null){
//                                        editor.putString("date", String.valueOf(currentDate));
//                                        editor.putString("data", getData.getString("data","data")+"\n"+addEvent.getText().toString());
//                                        editor.commit();
////                                        Intent i = new Intent(Intent.ACTION_EDIT);
////                                        i.setType("vnd.android.cursor.item/event");
////                                        i.putExtra("beginTime", calendar.getTimeInMillis());
////                                        i.putExtra("allDay", true);
////                                        i.putExtra("rule", "FREQ=YEARLY");
////                                        i.putExtra("endTime", calendar.getTimeInMillis() + 60 * 60 * 1000);
////                                        i.putExtra("title", "Calendar Event");
////                                        startActivity(i);
//                                    }else{
//                                        editor.putString("date", String.valueOf(currentDate));
//                                        editor.putString("data", addEvent.getText().toString());
//                                        editor.commit();
//                                    }
//
//                                    events = String.valueOf(addEvent.getText());
//                                    data.setText("All Events:\n" + events);
//                                    Event event = new Event(Color.argb(255,169,255,0),calendar.getTimeInMillis(),"Event at : "+ new Date(calendar.getTimeInMillis()));
//                                }
//                            });
//                        } else if ("View all events".matches(options[i])) {
//                            addEvent.setEnabled(false);
//                            addEvent.setVisibility(View.INVISIBLE);
////                            addEvent.setHint("Enter your event details");
//                            add.setVisibility(View.INVISIBLE);
//                            //data.setText("");
//                            currentDate = (year+month+dayOfMonth);
//                            SharedPreferences getData = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//                            String date;
//                            date = getData.getString("date", "date");
//                            if (date.matches(String.valueOf(currentDate))) {
//                                events = getData.getString("data", "data");
//                                data.setText("All Events:\n" + events);
//                            } else {
//                                data.setText("No events available!");
//                            }
//                        }
//                    }
//                });
//                alertDialog.show();
//            }
//        });