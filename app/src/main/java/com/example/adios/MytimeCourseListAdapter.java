package com.example.adios;

import android.app.AlertDialog;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
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

public class MytimeCourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;

    public MytimeCourseListAdapter(Context context, List<Course> courseList, Fragment parent) {

        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.mytime, null);
        TextView courseName = (TextView) v.findViewById(R.id.courseName);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseArea = (TextView) v.findViewById(R.id.courseArea);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseMajor = (TextView) v.findViewById(R.id.courseMajor);
        TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        courseName.setText(courseList.get(i).getCourseName() + " /");
        courseProfessor.setText(courseList.get(i).getCourseProfessor());
        courseArea.setText(courseList.get(i).getCourseArea() + " /");
        courseCredit.setText(courseList.get(i).getCourseCredit() + "학점 /");
        courseMajor.setText(courseList.get(i).getCourseMajor());
        courseRoom.setText(courseList.get(i).getCourseRoom() + " /");
        courseTime.setText(courseList.get(i).getCourseTime());

        v.setTag(courseList.get(i).getCourseID());

        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            courseList.clear();
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = builder.setMessage("강의가 삭제 되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                MytimeFragment.totalCredit -= courseList.get(i).getCourseCredit();
                                MytimeFragment.credit.setText(MytimeFragment.totalCredit + "학점");
                                courseList.remove(i);
                                notifyDataSetChanged();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = builder.setMessage("추가 되어있지 않은 강의입니다")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteRequest deleteRequest = new DeleteRequest(userID, courseList.get(i).getCourseID() + "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(deleteRequest);
            }

        });
        return v;

    }

}