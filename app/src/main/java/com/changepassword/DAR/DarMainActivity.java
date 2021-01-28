package com.changepassword.DAR;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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

import com.changepassword.MonthAdapter;
import com.changepassword.R;
import com.changepassword.TourPlanActivity;
import com.daimajia.swipe.SwipeLayout;
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

import static com.changepassword.R.layout.calendar_view;

public class DarMainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String[] title = {"A", "B", "C"};
    public static final String[] subtitle = {"Location A", "Location B", "Location C"};
    public static Calendar myCalendar;
    public static int currentYear;
    public static Date dateFromUser;
    public static String dateString, city, name;
    public static String listFrom = "Dar";
    static Context context;
    private final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final String TAG = "MyTag";
    CompactCalendarView compactCalendarView;
    SimpleDateFormat sdf;
    TextView tx_date, tx_today;
    LinearLayout ly_left, ly_right;
    Date c;
    SimpleDateFormat yearFormat;
    SimpleDateFormat df;
    String formattedDate;
    TextView events;
    Button add;
    EditText addEvent;
    SharedPreferences myPref;
    MonthAdapter adapter;
    Integer[] day = {10, 20, 21, 25, 27};
    List<Integer> days = new ArrayList<Integer>(Arrays.asList(day));
    Integer[] month = {10, 10, 11, 11, 12};
    List<Integer> months = new ArrayList<Integer>(Arrays.asList(month));
    Integer[] year = {2020, 2020, 2018, 2018, 2018};
    List<Integer> years = new ArrayList<Integer>(Arrays.asList(year));
    int yearNumber, monthNumber;
    ListView listView;
    List<ListModel> listModels;
    List<RecordDetailListModel> recordModel;
    SwipeLayout swipeLayout;
    LinearLayout layout, calendarView, dateAndCalendar;
    FloatingActionButton addFloating, copy, leave;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-YYYY", Locale.getDefault());

    public static void goToWork() {
        Intent work = new Intent(context, WorkWithSelectionActivity.class);
        work.putExtra("SelectedDate", dateString);
        work.putExtra("City", city);
        work.putExtra("Name", name);
        context.startActivity(work);
    }

    public static void goToProduct() {
        Intent work = new Intent(context, ProductListDetalingActivity.class);
        work.putExtra("SelectedDate", dateString);
        work.putExtra("City", city);
        work.putExtra("Name", name);
        context.startActivity(work);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_main);
        context = this;
        compactCalendarView = findViewById(R.id.calendar);

        setList();

        calendarView = (LinearLayout) this.getLayoutInflater().inflate(calendar_view, null);
        dateAndCalendar = (LinearLayout) this.getLayoutInflater().inflate(R.layout.date_header, null);

        init();
        setCalendarView();
        calendarlistener();
        swipe();
        yearNumber = myCalendar.get(Calendar.YEAR);
        monthNumber = myCalendar.get(Calendar.MONTH) + 1;
        tx_date.setText("" + formattedDate);
        currentYear = yearNumber;

        addFloating = findViewById(R.id.float_add);
        leave = findViewById(R.id.float_leave);

        addFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workType = new Intent(DarMainActivity.this, WorkTypeActivity.class);
                workType.putExtra("SelectedDate", dateString);
                startActivity(workType);
            }
        });

        tx_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                currentYear = yearNumber;
                // Toast.makeText(CalendarViewActivity.this, formattedDate, Toast.LENGTH_SHORT).show();
                com.changepassword.DAR.CardFragment cardFragment = com.changepassword.DAR.CardFragment.newInstance();
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

    }

    private void setCalendarView() {
        layout.removeAllViews();
        layout.addView(calendarView);
    }

    private void swipe() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.list_item_dar, listView, false);
        swipeLayout = (SwipeLayout) view.findViewById(R.id.swipe_layout);
        setSwipeViewFeatures();
    }

    private void setSwipeViewFeatures() {
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                Log.i(TAG, "onClose");
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                Log.i(TAG, "on swiping");
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {
                Log.i(TAG, "on start open");
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                Log.i(TAG, "the BottomView totally show");
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                Log.i(TAG, "the BottomView totally close");
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });
    }

    private void setList() {

        if (listFrom.equals("Dar")) {
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
        } else if (listFrom.equals("Record")) {
            recordModel = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                RecordDetailListModel model = new RecordDetailListModel();

                model.setReasonType("Reason Type " + i);
                model.setCity("City " + i);

                recordModel.add(model);
            }

            listView = findViewById(R.id.event_list);
            RecordDetailListAdapter listAdapter = new RecordDetailListAdapter(this, recordModel);
            listView.setAdapter(listAdapter);
            listFrom = "Dar";
            listAdapter.notifyDataSetChanged();
        }

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @SuppressLint("ClickableViewAccessibility")
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
//                @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.dar_list_item_details, null);
//                int height = 600;
//                int width = 1000;
//                TextView title = popupView.findViewById(R.id.popup_title);
//                ImageView cancel = popupView.findViewById(R.id.cancel);
//                TextView date = popupView.findViewById(R.id.date);
//                title.setText(listModels.get(i).getTitle());
//                date.setText(dateString);
//                PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
//                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//                popupView.setOnTouchListener((view1, motionEvent) -> {
//                    popupWindow.dismiss();
//                    return true;
//                });
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        popupWindow.dismiss();
//                    }
//                });
//            }
//        });
    }

    //Setting calendar
    public void setCalendar(Date date) {
//        Toast.makeText(CalendarViewActivity.this, "CalendarViewActivity", Toast.LENGTH_SHORT).show();
        compactCalendarView.setCurrentDate(date);
        dateFromUser = date;
        yearFormat = new SimpleDateFormat("YYYY");
        simpleDateFormat = new SimpleDateFormat("MMMM");
        tx_date.setText(simpleDateFormat.format(date) + " , " + yearFormat.format(date));
        currentYear = Integer.parseInt(yearFormat.format(date));
        Log.d("year", String.valueOf(currentYear));
    }

    //variable initialization

    @SuppressLint("WrongViewCast")
    public void init() {
        compactCalendarView = calendarView.findViewById(R.id.compactcalendar_view);
        tx_date = calendarView.findViewById(R.id.text_current_month);
        ly_left = calendarView.findViewById(R.id.left);
        ly_right = calendarView.findViewById(R.id.right);
//        tx_today = findViewById(R.id.text_today);
        layout = findViewById(R.id.calendar_layout);
//        myPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Setdate();
    }

    //calendar method
    public void calendarlistener() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @SuppressLint({"SimpleDateFormat", "ClickableViewAccessibility"})
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDayClick(Date dateClicked) {
                LocalDate date = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dateClicked));
                dateString = new SimpleDateFormat("dd/MM/yyyy").format(dateClicked);

                if (dateClicked.before(c)) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    @SuppressLint("InflateParams")
                    View popupView = inflater.inflate(R.layout.unlock_request_popup, null);
                    boolean focusable = true;
                    int height = 900;
                    int width = 1000;
                    ImageView cancel = popupView.findViewById(R.id.cancel);
                    PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
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
                } else {
                    layout.removeAllViews();
                    TextView dateTV = dateAndCalendar.findViewById(R.id.dateTV);
                    ImageView image = dateAndCalendar.findViewById(R.id.calendarIV);

                    layout.addView(dateAndCalendar);

                    dateTV.setText(dateString);
                    dateTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setCalendarView();
                        }
                    });
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setCalendarView();
                        }
                    });
                }


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
    }

    @SuppressLint("SimpleDateFormat")
    public void goForTour(View view) {
        if (dateString == null) {
            dateString = new SimpleDateFormat("dd/MM/yyyy").format(c);
        } else {
            Intent tour = new Intent(DarMainActivity.this, TourPlanActivity.class);
            tour.putExtra("SelectedDate", dateString);
            startActivity(tour);
        }

    }
}

