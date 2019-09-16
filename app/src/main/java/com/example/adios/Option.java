package com.example.adios;

public class Option {
    int csCredit;
    String favorCourse;
    String empday;

    public int getcsCredit() { return csCredit; }
    public void setcsCredit(int csCredit) {
        this.csCredit = csCredit;
    }

    public String getfavorCourse() {
        return favorCourse;
    }
    public void setfavorCourse(String favorCourse) {
        this.favorCourse = favorCourse;
    }

    public String empday() {
        return empday;
    }
    public void setempday(String empday) { this.empday = empday;}


    public Option(int csCredit, String favorCourse, String empday){
        this.csCredit = csCredit;
        this.favorCourse = favorCourse;
        this.empday = empday;
    }
}
//1)empday=monday라고 치면if(empday==monday)

//소프트웨어학과 강의중에서 monday가 들어간 강의는 다없앤채로 임시 테이블을 만든다.

//임시테이블에는 월요일이 들어간 수업은 하나도 없는 강의만 있다.

//선호과목이 소프트웨어공학이면

//임시테이블에 소프트웨어 공학은 들어간채로 요구하는 학점이 소프트웨어공학의 학점만큼 채워진다.

//요구학점이 15학점이고 소프트웨어공학이 3학점이면 12학점을 가지고

