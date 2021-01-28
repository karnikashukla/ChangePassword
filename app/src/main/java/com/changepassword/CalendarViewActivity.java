package com.changepassword;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CalendarViewActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String[] title = {"A", "B", "C"};
    public static final String[] subtitle = {"Location A", "Location B", "Location C"};
    public static Calendar myCalendar;
    private final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String[] options = {"Add new event", "View all events"};
    CompactCalendarView compactCalendarView;
    SimpleDateFormat sdf;
    public static int currentYear;
    public static Date dateFromUser;
    TextView tx_date, tx_today;
    LinearLayout ly_left, ly_right;
    Date c;
    public String dateString;
    SimpleDateFormat df;
    String formattedDate;
    TextView events;
    Button add;
    EditText addEvent;
    SharedPreferences myPref;
    List<String> monthNames;
    Integer[] day = {10, 20, 21, 25, 27};
    List<Integer> days = new ArrayList<Integer>(Arrays.asList(day));
    Integer[] month = {10, 10, 11, 11, 12};
    List<Integer> months = new ArrayList<Integer>(Arrays.asList(month));
    Integer[] year = {2020, 2020, 2018, 2018, 2018};
    List<Integer> years = new ArrayList<Integer>(Arrays.asList(year));
    RecyclerView monthList;
    SimpleDateFormat yearFormat;
    MonthAdapter adapter;
    int yearNumber, monthNumber;
    ListView listView;
    List<ListModel> listModels;

    FloatingActionButton addFloating, copy, delete;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-YYYY", Locale.getDefault());

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        compactCalendarView = findViewById(R.id.calendar);

        init();
        setList();
        calendarlistener();
        yearNumber = myCalendar.get(Calendar.YEAR);
        monthNumber = myCalendar.get(Calendar.MONTH) + 1;
        tx_date.setText("" + formattedDate);

        copy = findViewById(R.id.float_copy);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent copy = new Intent(CalendarViewActivity.this, MTPCopyActivity.class);
                startActivity(copy);
            }
        });
        tx_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentYear = yearNumber;
                // Toast.makeText(CalendarViewActivity.this, formattedDate, Toast.LENGTH_SHORT).show();
                CardFragment cardFragment = CardFragment.newInstance();
                cardFragment.show(getSupportFragmentManager(), "BottomSheet");
            }
        });
        ly_right.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
            @Override
            public void onClick(View v) {
                monthNumber = monthNumber + 1;
                if (monthNumber > 12) {
                    monthNumber = 1;
                    yearNumber = yearNumber + 1;
                    yearFormat = new SimpleDateFormat("YYYY");
                    tx_date.setText(Month.of(monthNumber) + " , " + yearFormat.format(c));
                    Date date = new GregorianCalendar(yearNumber, monthNumber, 1).getTime();
                    setCalendar(date);
                    Setdate();


                    currentYear = yearNumber;
                    //                    Toast.makeText(CalendarViewActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();
                } else {
                    yearFormat = new SimpleDateFormat("YYYY");
                    tx_date.setText(Month.of(monthNumber) + " , " + yearFormat.format(c));
                    Date date = new GregorianCalendar(yearNumber, monthNumber, 1).getTime();
                    setCalendar(date);
//                    monthNumber = monthNumber + 1;
                    Setdate();
                }
            }
        });

        ly_left.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                monthNumber = monthNumber - 1;
                if (monthNumber < 1) {
                    monthNumber = 12;
                    yearNumber = yearNumber - 1;
                    yearFormat = new SimpleDateFormat("YYYY");
                    tx_date.setText(Month.of(monthNumber) + " , " + yearFormat.format(c));
                    Date date = new GregorianCalendar(yearNumber, monthNumber, 1).getTime();
                    setCalendar(date);
                    Setdate();
                    //      monthNumber = 12;
                    //  yearNumber = yearNumber - 1;
                    //    currentYear = yearNumber;

//                    Toast.makeText(CalendarViewActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();
                } else {
                    yearFormat = new SimpleDateFormat("YYYY");
                    tx_date.setText(Month.of(monthNumber) + " , " + yearFormat.format(c));
                    Date date = new GregorianCalendar(yearNumber, monthNumber, 1).getTime();
                    setCalendar(date);
//                    monthNumber = monthNumber - 1;
                    Setdate();
                }

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

    private void setList() {
        listModels = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            ListModel model = new ListModel();
            model.setTitle(title[i]);
            model.setSubtitle(subtitle[i]);

            listModels.add(model);
        }

        listView = findViewById(R.id.event_list);
        ListAdapter listAdapter = new ListAdapter(this, listModels);
        listView.setAdapter(listAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.mtp_list_item_detail, null);
                boolean focusable = true;
                int height = 600;
                int width = 1000;
                TextView title = popupView.findViewById(R.id.popup_title);
                ImageView cancel = popupView.findViewById(R.id.cancel);
                TextView city = popupView.findViewById(R.id.city);
                title.setText(listModels.get(i).getTitle());
                city.setText(listModels.get(i).getSubtitle());
                PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupView.setOnTouchListener((view1, motionEvent) -> {
                    popupWindow.dismiss();
                    return true;
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    //Setting calendar
    public void setCalendar(Date date) {
//        Toast.makeText(CalendarViewActivity.this, "CalendarViewActivity", Toast.LENGTH_SHORT).show();
        compactCalendarView.setCurrentDate(date);
        dateFromUser = date;
        yearFormat = new SimpleDateFormat("YYYY");
        simpleDateFormat = new SimpleDateFormat("MMMM");
        tx_date.setText(simpleDateFormat.format(date) + " , " + yearFormat.format(date));
    }

    //variable initialization

    @SuppressLint("WrongViewCast")
    public void init() {
        compactCalendarView = findViewById(R.id.compactcalendar_view);
        tx_date = findViewById(R.id.text_current_month);
        ly_left = findViewById(R.id.left);
        ly_right = findViewById(R.id.right);
        tx_today = findViewById(R.id.text_today);
//        add = findViewById(R.id.btnAdd);
//        events = findViewById(R.id.event_data);
//        addEvent = findViewById(R.id.add);

        myPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Setdate();
    }

    //calendar method
    public void calendarlistener() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @SuppressLint("SimpleDateFormat")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDayClick(Date dateClicked) {
                LocalDate date = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dateClicked));
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
//                                    Log.d("day", Arrays.toString(day));
//                                    SharedPreferences.Editor editor = myPref.edit();
//                                    SharedPreferences getData = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//                                    if (getData.getString("date", "date").matches(String.valueOf(dateClicked)) && getData.getString("data", "data") != null) {
//                                        editor.putString("date", String.valueOf(dateClicked));
//                                        editor.putString("data", getData.getString("data", "data") + "\n" + addEvent.getText().toString());
//
//                                        int dayOfMonth = date.getDayOfMonth();
//                                        int monthofYear = date.getMonthValue();
//                                        int yearSelected = date.getYear();
//                                        days.add(dayOfMonth);
//                                        months.add(monthofYear);
//                                        years.add(yearSelected);
//
//                                        day = days.toArray(day);
//                                        month = months.toArray(month);
//                                        year = years.toArray(year);
//                                        Log.d("day", Arrays.toString(day));
////                                        myCalendar = Calendar.getInstance()1;
////
////                                        for (int j = 0; j < month.length; j++) {
////
////                                            int mon = Integer.parseInt(month[j]);
////                                            myCalendar.set(Calendar.YEAR, yearSelected);
////                                            myCalendar.set(Calendar.MONTH, monthofYear);
////                                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
////
////                                            Event event = new Event(Color.RED, myCalendar.getTimeInMillis(), getData.getString("data","data"));
////                                            compactCalendarView.addEvent(event);
//                                        editor.commit();
//                                        Setdate();
//                                        //compactCalendarView.addEvent(event);
//                                    } else {
//                                        editor.putString("date", String.valueOf(dateClicked));
//                                        editor.putString("data", addEvent.getText().toString());
//                                        editor.commit();
//                                        int dayOfMonth = date.getDayOfMonth();
//                                        int monthofYear = date.getMonthValue();
//                                        int yearSelected = date.getYear();
//                                        days.add(dayOfMonth);
//                                        months.add(monthofYear);
//                                        years.add(yearSelected);
//
//                                        day = days.toArray(day);
//                                        month = months.toArray(month);
//                                        year = years.toArray(year);
//
//                                        Log.d("day", Arrays.toString(day));
//                                        Log.d("day", Arrays.toString(month));
//                                        Log.d("day", Arrays.toString(year));
//
//                                        compactCalendarView.removeAllEvents();
//                                        Setdate();
//                                    }
//                                    String data = String.valueOf(addEvent.getText());
//                                    events.setText("All Events:\n" + data);
//
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
//                            //currentDate = String.valueOf(calendarView.getSelectedDate());
//                            date = getData.getString("date", "date");
//                            String data;
//                            if (date.matches(String.valueOf(dateClicked))) {
//                                data = getData.getString("data", "data");
//                                events.setText("All Events:\n" + data);
//
//                            } else {
//                                events.setText("No events available!");
//                            }
//                        }
//                    }
//                });
//                alertDialog.show();

//                if (DateFormat.format(dateClicked).equals("2018-11-21")) {
//                    Toast.makeText(getApplicationContext(), DateFormat.format(dateClicked) + " This day your brother birth day ", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), DateFormat.format(dateClicked) + " In This day no Events Available", Toast.LENGTH_LONG).show();
//                }
                dateString = new SimpleDateFormat("dd/MM/yyyy").format(dateClicked);
            }

            @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                compactCalendarView.removeAllEvents();
                Setdate();
                yearFormat = new SimpleDateFormat("YYYY");
                simpleDateFormat = new SimpleDateFormat("MMMM");
                tx_date.setText(simpleDateFormat.format(firstDayOfNewMonth) + " , " + yearFormat.format(firstDayOfNewMonth));
//                tx_date.setText(dateFormatForMont.format(firstDayOfNewMonth));
                currentYear = firstDayOfNewMonth.getYear();
                formattedDate = simpleDateFormat.format(firstDayOfNewMonth) + " , " + yearFormat.format(firstDayOfNewMonth);
            }
        });

    }

    //get current date

    @SuppressLint("SimpleDateFormat")
    public void Setdate() {
        c = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("MMMM");
        yearFormat = new SimpleDateFormat("YYYY");
        formattedDate = df.format(c) + " , " + yearFormat.format(c);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        sdf = new SimpleDateFormat("MMMM yyyy");
        myCalendar = Calendar.getInstance();
//        monthNumber = monthNumber + 1;
        currentYear = c.getYear();
//
//        for (int j = 0; j < month.length; j++) {
//
//            int mon = (month[j]);
//            myCalendar.set(Calendar.YEAR, (year[j]));
//            myCalendar.set(Calendar.MONTH, mon - 1);
//            myCalendar.set(Calendar.DAY_OF_MONTH, (day[j]));
//            //    String myDate = +year[j] + "-" + (mon + 1) + "-" + day[j];
//            Log.d("Month", String.valueOf(myCalendar));
//            Event event = new Event(Color.RED, myCalendar.getTimeInMillis(), "test");
//            compactCalendarView.addEvent(event);
//
//        }
    }

    public void goForTour(View view) {
        if (dateString == null) {
            dateString = new SimpleDateFormat("dd/MM/yyyy").format(c);
        } else {
            Intent tour = new Intent(CalendarViewActivity.this, TourPlanActivity.class);
            tour.putExtra("SelectedDate", dateString);
            startActivity(tour);
        }

    }
}

