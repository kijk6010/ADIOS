package com.example.adios;

public class Course {
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

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseArea() {
        return courseArea;
    }

    public void setCourseArea(String courseArea) {
        this.courseArea = courseArea;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        this.courseMajor = courseMajor;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public String getCourseLanguage() {
        return courseLanguage;
    }

    public void setCourseLan(String courseLanguage) {
        this.courseLanguage = courseLanguage;
    }

    public int getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(int courseScore) {
        this.courseScore = courseScore;
    }

    public Course(String courseID, String courseName, String courseArea, int courseCredit, int courseYear, String courseTerm, String courseMajor, String courseProfessor, String courseTime, String courseRoom, String courseLanguage, int courseScore) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseArea = courseArea;
        this.courseCredit = courseCredit;
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        this.courseMajor = courseMajor;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseRoom = courseRoom;
        this.courseLanguage = courseLanguage;
        this.courseScore = courseScore;
    }
}
