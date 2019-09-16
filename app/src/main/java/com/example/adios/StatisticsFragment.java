package com.example.adios;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.provider.SyncStateContract.Helpers.update;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatisticsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ArrayAdapter subAdapter;
    private Spinner subSpinner;
    private ArrayAdapter subAdapter1;
    private Spinner subSpinner1;
    private ArrayAdapter emp1Adapter;
    private Spinner emp1Spinner;
    private ArrayAdapter emp2Adapter;
    private Spinner emp2Spinner;
    public SeekBar sb;
    public TextView tv;
    public int num=0;
    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<Course> courseList;
    private ListView courseListView1;
    private CourseListAdapter adapter1;
    private List<Course> courseList1; //일반적인
    private AlertDialog dialog;
    private TextView tv1;
    private List<String> courseIDList;
    private List<String> courseNameList;
    private List<String> courseTimeList;
    private List<Course> Temp_courseNameList;
    private List<Course> Temp_courseNameList1;
    private Schedule schedule;
    int num1; // 요구학점

    String emp1,emp2;
    String fav="없음";
    String fav1="없음";

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        sb = (SeekBar)getView().findViewById(R.id.seekBar);
        tv = (TextView)getView().findViewById(R.id.SBtext);
        new BackgroundTask().execute();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                num = seekBar.getProgress();
                update();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                num = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                num = seekBar.getProgress();
            }
        });
        subSpinner = (Spinner)getView(). findViewById(R.id.subSpinner);
        subAdapter = ArrayAdapter.createFromResource(getActivity(),  R.array.favorSubject, android.R.layout.simple_spinner_dropdown_item);
        subSpinner.setAdapter(subAdapter);

        subSpinner1 = (Spinner)getView(). findViewById(R.id.subSpinner1);
        subAdapter1 = ArrayAdapter.createFromResource(getActivity(),  R.array.favorSubject, android.R.layout.simple_spinner_dropdown_item);
        subSpinner1.setAdapter(subAdapter1);

        emp1Spinner = (Spinner)getView(). findViewById(R.id.emp1Spinner);
        emp1Adapter = ArrayAdapter.createFromResource(getActivity(),  R.array.day, android.R.layout.simple_spinner_dropdown_item);
        emp1Spinner.setAdapter(emp1Adapter);


        emp2Spinner = (Spinner)getView(). findViewById(R.id.emp2Spinner);
        emp2Adapter = ArrayAdapter.createFromResource(getActivity(),  R.array.day, android.R.layout.simple_spinner_dropdown_item);
        emp2Spinner.setAdapter(emp2Adapter);
        schedule = new Schedule();
        courseListView = (ListView) getView().findViewById(R.id.courseListView);
        courseList1 = new ArrayList<Course>();
        //adapter = new CourseListAdapter(getContext().getApplicationContext(), courseList,this);
        // courseListView.setAdapter(adapter);

        //courseListView1 = (ListView) getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        courseIDList = new ArrayList<String>();
        courseNameList = new ArrayList<String>();
        courseTimeList = new ArrayList<String>();
        Temp_courseNameList = new ArrayList<Course>();
        Temp_courseNameList1 = new ArrayList<Course>();
        adapter = new CourseListAdapter(getContext().getApplicationContext(), courseList,this);

        getView().findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
                                                                           @Override
                                                                           public void onClick(View v) {
                                                                               //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                               //dialog = builder.setMessage("시간표 완성!! 확인해주세요*^^*")
                                                                               //       .setPositiveButton("확인", null)
                                                                               //        .create();
                                                                               //dialog.show();
                                                                               emp1 = emp1Spinner.getSelectedItem().toString();
                                                                               emp2 = emp2Spinner.getSelectedItem().toString();
                                                                               fav = subSpinner.getSelectedItem().toString();
                                                                               fav1 = subSpinner1.getSelectedItem().toString();
                                                                               num1=num;
                                                                               sort();

                                                                               courseListView.setAdapter(adapter);

                                                                               // boolean validate = false;
                                                                               // validate= schedule.validate(courseList.get(i).getCourseTime());

                                                                           }
                                                                       }
        );

        //new BackgroundTask().execute();


    }

    public void update(){
        tv.setText(new StringBuilder().append(num));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected  void onPreExecute() {
            try
            {

                target = "http://eshc1.cafe24.com/CourseList.php?courseYear=" + URLEncoder.encode("2018", "UTF-8") +
                        "&courseTerm=" + URLEncoder.encode("2학기", "UTF-8") +
                        "&courseMajor=" + URLEncoder.encode("소프트웨어학과", "UTF-8");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {super.onProgressUpdate();}

        @Override

        public void onPostExecute(String result) {
            try {

                courseList1.clear();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String courseID;
                String courseName;
                String courseArea;
                int courseCredit;
                int courseYear;
                String courseTerm;
                String courseMajor;
                String courseProfessor;
                String courseTime;
                String courseRoom;
                String courseLanguage;
                int courseScore;

                String userID = MainActivity.userID;
                while (count < jsonArray.length())
                {
                    boolean contains=false;

                    JSONObject object = jsonArray.getJSONObject(count);
                    courseName = object.getString("courseName");
                    //hasfav=courseName.equals(fav);
                    courseTime = object.getString("courseTime");
                    //contains= (courseTime.contains(emp1)||courseTime.contains(emp2));
                    //if(contains){
                    //    count++;
                    //   continue;
                    //}
                    courseID = object.getString("courseID");

                    courseArea = object.getString("courseArea");
                    courseCredit = object.getInt("courseCredit");
                    //if(hasfav){
                    //    favcredit=courseCredit;
                    //    favint=count;
                    // }
                    courseYear = object.getInt("courseYear");
                    courseTerm = object.getString("courseTerm");
                    courseMajor = object.getString("courseMajor");
                    courseProfessor = object.getString("courseProfessor");

                    courseRoom = object.getString("courseRoom");
                    courseLanguage = object.getString("courseLanguage");
                    courseScore = object.getInt("courseScore");
                    Course course = new Course(courseID, courseName, courseArea, courseCredit, courseYear, courseTerm, courseMajor, courseProfessor, courseTime, courseRoom, courseLanguage, courseScore);
                    courseList1.add(course);

                    count++;

                }


                if(count == 0)
                {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(StatisticsFragment.this.getActivity());
                    dialog = builder.setMessage("조회된 강의가 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    public void sort() {
        courseIDList.clear();
        courseList.clear();
        courseNameList.clear();
        courseTimeList.clear();
        Temp_courseNameList.clear();
        Temp_courseNameList1.clear();
        int favorite=temp(Temp_courseNameList,fav); //선호하는 과목의 강의목록순서
        int favcred=courseList1.get(favorite).courseCredit;
        int favorite1=temp(Temp_courseNameList1,fav1); //선호하는 과목1의 강의목록순서
        int favcred1=courseList1.get(favorite1).courseCredit;
        boolean contains;
        if(new String("없음").equals(fav) && new String("없음").equals(fav1)) {
            for (int curcred = 0; curcred < num1;) {
                int r = (int) (Math.random() * courseList1.size());
                contains= (courseList1.get(r).courseTime.contains(emp1)||(courseList1.get(r).courseTime.contains(emp2)));
                if(contains){
                    //    count++;
                    continue;
                }
                int s = 0;
                //if (curcred > num1) break;
                //if(curcred<courseList1.get(r).courseCredit)continue;
                if (!alreadyIn(courseIDList, courseList1.get(r).getCourseID(),courseNameList,courseList1.get(r).getCourseName(),courseTimeList,courseList1.get(r).courseTime))//중복강의처리 - 과목코드 비교

                    continue;

                if (num1 - curcred >= courseList1.get(r).courseCredit) {
                    courseList.add(courseList1.get(r)); // 강의 추가
                    courseIDList.add(courseList1.get(r).getCourseID()); // 중복강의처리를 위한 강의ID 리스트에 추가
                    courseNameList.add(courseList1.get(r).getCourseName()); // 중복강의처리를 위한 강의 Name 리스트에 추가
                    courseTimeList.add(courseList1.get(r).getCourseTime()); // 같은 시간대 중복 과목 처리를 위한 time 리스트에 추가
                    s = courseList1.get(r).courseCredit;
                    curcred = s + curcred;

                }

            }
        }
        else if(new String("없음").equals(fav) && !new String("없음").equals(fav1)) {

            courseList.add(courseList1.get(favorite1));
            courseIDList.add(courseList1.get(favorite1).getCourseID());
            courseNameList.add(courseList1.get(favorite1).getCourseName());
            int curcred = 0;
            curcred = curcred + favcred1;
            for (; curcred < num1; ) {
                int r = (int) (Math.random() * courseList1.size());
                contains = (courseList1.get(r).courseTime.contains(emp1) || (courseList1.get(r).courseTime.contains(emp2)));
                if (contains) {
                    //    count++;
                    continue;
                }
                int s = 0;
                //if (curcred > num1) break;
                //if(curcred<courseList1.get(r).courseCredit)continue;
                if (!alreadyIn(courseIDList, courseList1.get(r).getCourseID(),courseNameList,courseList1.get(r).getCourseName(),courseTimeList,courseList1.get(r).courseTime))//중복강의처리
                    continue;
                if (num1 - curcred >= courseList1.get(r).courseCredit) {
                    courseList.add(courseList1.get(r)); // 강의 추가
                    courseIDList.add(courseList1.get(r).getCourseID()); // 중복강의처리를 위한 강의ID 리스트에 추가
                    courseNameList.add(courseList1.get(r).getCourseName()); // 중복강의처리를 위한 강의 Name 리스트에 추가
                    courseTimeList.add(courseList1.get(r).getCourseTime()); // 같은 시간대 중복 과목 처리를 위한 time 리스트에 추가
                    s = courseList1.get(r).courseCredit;
                    curcred = s + curcred;

                }

            }
        }
        else if(!new String("없음").equals(fav) && new String("없음").equals(fav1)) {
            courseList.add(courseList1.get(favorite));
            courseIDList.add(courseList1.get(favorite).getCourseID());
            courseNameList.add(courseList1.get(favorite).getCourseName());
            int curcred = 0;
            curcred = curcred + favcred;
            for (; curcred < num1; ) {
                int r = (int) (Math.random() * courseList1.size());
                contains = (courseList1.get(r).courseTime.contains(emp1) || (courseList1.get(r).courseTime.contains(emp2)));
                if (contains) {
                    //    count++;
                    continue;
                }
                int s = 0;
                //if (curcred > num1) break;
                //if(curcred<courseList1.get(r).courseCredit)continue;
                if (!alreadyIn(courseIDList, courseList1.get(r).getCourseID(),courseNameList,courseList1.get(r).getCourseName(),courseTimeList,courseList1.get(r).courseTime))//중복강의처리
                    continue;
                if (num1 - curcred >= courseList1.get(r).courseCredit) {
                    courseList.add(courseList1.get(r)); // 강의 추가
                    courseIDList.add(courseList1.get(r).getCourseID()); // 중복강의처리를 위한 강의ID 리스트에 추가
                    courseNameList.add(courseList1.get(r).getCourseName()); // 중복강의처리를 위한 강의 Name 리스트에 추가
                    courseTimeList.add(courseList1.get(r).getCourseTime()); // 같은 시간대 중복 과목 처리를 위한 time 리스트에 추가
                    s = courseList1.get(r).courseCredit;
                    curcred = s + curcred;

                }

            }
        }
        else {
            courseList.add(courseList1.get(favorite));
            courseIDList.add(courseList1.get(favorite).getCourseID());
            courseNameList.add(courseList1.get(favorite).getCourseName());
            courseList.add(courseList1.get(favorite1));
            courseIDList.add(courseList1.get(favorite1).getCourseID());
            courseNameList.add(courseList1.get(favorite1).getCourseName());
            int curcred = 0;
            curcred = favcred + curcred + favcred1;
            for (; curcred < num1; ) {
                int r = (int) (Math.random() * courseList1.size());
                contains = (courseList1.get(r).courseTime.contains(emp1) || (courseList1.get(r).courseTime.contains(emp2)));
                if (contains) {
                    //    count++;
                    continue;
                }
                int s = 0;
                //if (curcred > num1) break;
                //if(curcred<courseList1.get(r).courseCredit)continue;
                if (!alreadyIn(courseIDList, courseList1.get(r).getCourseID(),courseNameList,courseList1.get(r).getCourseName(),courseTimeList,courseList1.get(r).courseTime))//중복강의처리
                    continue;
                if (num1 - curcred >= courseList1.get(r).courseCredit) {
                    courseList.add(courseList1.get(r)); // 강의 추가
                    courseIDList.add(courseList1.get(r).getCourseID()); // 중복강의처리를 위한 강의ID 리스트에 추가
                    courseNameList.add(courseList1.get(r).getCourseName()); // 중복강의처리를 위한 강의 Name 리스트에 추가
                    courseTimeList.add(courseList1.get(r).getCourseTime()); // 같은 시간대 중복 과목 처리를 위한 time 리스트에 추가
                    s = courseList1.get(r).courseCredit;
                    curcred = s + curcred;

                }

            }
        }
    }
    public boolean alreadyIn(List<String> courseIDList,String item,List<String> courseNameList,String item1,List<String> courseTimeList,String item2){
        for(int i=0;i<courseTimeList.size();i++){
            if(courseTimeList.get(i).equals(item2)){
                return false;
            }
        }

        for(int i=0;i<courseIDList.size();i++){
            if(courseIDList.get(i).equals(item)){
                return false;
            }
        }
        for(int i=0;i<courseNameList.size();i++){
            if(courseNameList.get(i).equals(item1)){
                return false;
            }
        }

        return true;
    }
    public int favcour(String f) { // 입력한 선호과목의 리스트 순서를 읽어냄
        int result=0;
        int j;

        for (int i = -1; i < courseList1.size(); i++) {
            j=(int)(Math.random()*courseList1.size());
            if(j==i) {
                //.i--;
                continue;
            }

            if(new String(courseList1.get(j).courseName).equals(f)) {
                result = j;
            }
        }
        return result;
    }
    public int temp(List<Course> temp_courseNameList,String item) {

        int j;
        int result = 0;
        if(new String(item).equals("없음"))
        {
            return result;
        }
        for (int i = 0; i < courseList1.size(); i++) { // 선호과목과 과목명이 같은 과목들을 임시리스트에 저장.
            if (new String(courseList1.get(i).courseName).equals(item)) {
                temp_courseNameList.add(courseList1.get(i));
                result = i;
            }
        }
        j = (int) (Math.random() * temp_courseNameList.size()); // 임시리스트에서 랜덤으로 선택할 인덱스 저장

        for (int i = 0; i < courseList1.size(); i++) { // 선호과목 임시리스트에서 임의로 뽑아낸 강의의 원래 저장된 courselist에서의 인덱스를 추출하기 위한 작업
            if (new String(courseList1.get(i).courseID).equals(temp_courseNameList.get(j).courseID)) {
                result = i;
            }
        }

        return result;
    }
}