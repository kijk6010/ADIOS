package com.example.adios;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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

public class CourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID= MainActivity.userID;
    private Schedule schedule = new Schedule();
    private List<String> courseIDList;
    private List<String> courseTimeList;

    public static int totalCredit=0;

    public CourseListAdapter(Context context, List<Course> courseList, Fragment parent) {

        this.context = context;
        this.courseList = courseList;
        this.parent =parent;
        schedule = new Schedule();
        courseIDList = new ArrayList<String>();
        courseTimeList = new ArrayList<String>();
        new BackgroundTask().execute();
        totalCredit=0;
    }

    @Override
    public int getCount() {return courseList.size();}

    @Override
    public Object getItem(int i) {return courseList.get(i);}


    @Override
    public long getItemId(int i) {return i;}

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.course, null);
        TextView courseName = (TextView) v.findViewById(R.id.courseName);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseArea = (TextView) v.findViewById(R.id.courseArea);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseMajor = (TextView) v.findViewById(R.id.courseMajor);
        TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        courseName.setText(courseList.get(i).getCourseName()+ " /");
        courseProfessor.setText(courseList.get(i).getCourseProfessor());
        courseArea.setText(courseList.get(i).getCourseArea() + " /");
        courseCredit.setText(courseList.get(i).getCourseCredit()+ "학점 /");
        courseMajor.setText(courseList.get(i).getCourseMajor());
        courseRoom.setText(courseList.get(i).getCourseRoom()+ " /");
        courseTime.setText(courseList.get(i).getCourseTime());

        v.setTag(courseList.get(i).getCourseID());

        Button addButton =(Button)v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                boolean validate = false;
                validate= schedule.validate(courseList.get(i).getCourseTime());
                if(!alreadyIn(courseIDList,courseList.get(i).getCourseID())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("이미 추가한 강의입니다.")
                            .setPositiveButton("다시시도", null)
                            .create();
                    dialog.show();
                }
                else if(totalCredit+courseList.get(i).getCourseCredit()>19){
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("19학점을 초과할 수 없습니다.")
                            .setPositiveButton("다시시도", null)
                            .create();
                    dialog.show();
                }
                else if(validate==false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("시간표가 중복됩니다.")
                            .setPositiveButton("다시시도", null)
                            .create();
                    dialog.show();
                }
                else{
                    String userID = MainActivity.userID;
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try
                            {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success)
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = builder.setMessage("강의가 추가 되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    courseIDList.add(courseList.get(i).getCourseID());
                                    courseTimeList.add(courseList.get(i).getCourseTime());
                                    schedule.addSchedule(courseList.get(i).getCourseTime());
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = builder.setMessage("강의 추가에 실패하였습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };
                    AddRequest addRequest = new AddRequest(userID,courseList.get(i).getCourseID()+"", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(addRequest);
                }
            }

        });

        return v;


    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected  void onPreExecute() {
            try
            {
                target = "http://eshc1.cafe24.com/ScheduleList.php?userID="+URLEncoder.encode(userID,"UTF-8");
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
                String courseRoom;
                String courseTime;
                String courseID;
                totalCredit=0;
                while (count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getString("courseID");
                    courseRoom = object.getString("courseRoom");
                    courseTime = object.getString("courseTime");
                    totalCredit+=object.getInt("courseCredit");
                    courseIDList.add(courseID);
                    courseTimeList.add(courseTime);
                    schedule.addSchedule(courseTime);
                    count++;
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    public boolean alreadyIn(List<String> courseIDList,String item){

        for(int i=0;i<courseIDList.size();i++){
            if(courseIDList.get(i).equals(item)){
                        return false;
                }

        }
        return true;
    }

}