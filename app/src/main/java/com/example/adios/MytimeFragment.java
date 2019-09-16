package com.example.adios;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MytimeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MytimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MytimeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MytimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MytimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MytimeFragment newInstance(String param1, String param2) {
        MytimeFragment fragment = new MytimeFragment();
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

    private ListView courseListView;
    private MytimeCourseListAdapter adapter;

    private List<Course> courseList;
    public static int totalCredit = 0;
    public static TextView credit;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        courseListView = (ListView) getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new MytimeCourseListAdapter(getContext().getApplicationContext(), courseList,this);

        courseListView.setAdapter(adapter);
        new BackgroundTask().execute();

        totalCredit = 0;
        credit = (TextView) getView().findViewById(R.id.totalCredit);

    }

    class BackgroundTask extends AsyncTask<Void, Void, String>{

        String target;

        @Override
        protected  void onPreExecute() {
            try
            {
               // target = "http://eshc1.cafe24.com/CourseList.php?courseYear=" + URLEncoder.encode("2018", "UTF-8") +
               //         "&courseTerm=" + URLEncoder.encode("2학기", "UTF-8") +
               //         "&courseMajor=" + URLEncoder.encode("소프트웨어학과", "UTF-8");

                target = "http://eshc1.cafe24.com/MytimeCourseList.php?userID="+URLEncoder.encode(MainActivity.userID,"UTF-8");
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
                courseList.clear();
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

                while (count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getString("courseID");
                    courseName = object.getString("courseName");
                    courseArea = object.getString("courseArea");
                    courseCredit = object.getInt("courseCredit");
                    courseYear = object.getInt("courseYear");
                    courseTerm = object.getString("courseTerm");
                    courseMajor = object.getString("courseMajor");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseRoom = object.getString("courseRoom");
                    courseLanguage = object.getString("courseLanguage");
                    courseScore = object.getInt("courseScore");
                    totalCredit += courseCredit;
                    Course course = new Course(courseID, courseName, courseArea, courseCredit, courseYear, courseTerm, courseMajor, courseProfessor, courseTime, courseRoom, courseLanguage, courseScore);
                    courseList.add(course);
                    count++;
                }
               adapter.notifyDataSetChanged();
                //credit = (TextView)getView().findViewById(R.id.totalCredit);
                credit.setText(totalCredit+"학점");
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mytime, container, false);
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
}
