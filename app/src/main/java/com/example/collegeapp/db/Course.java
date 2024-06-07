package com.example.collegeapp.db;

public class Course {

    private int id; //课程ID
    private String courseName;
    private int section; //从第几节课开始
    private int sectionSpan; //跨几节课
    private int weekDay; //周几
    private String classRoom; //教室
    private int courseFlag; //课程背景颜色

    public Course() {
    }

    public Course(int id, String courseName, int section, int sectionSpan, int weekDay, String classRoom, int courseFlag) {
        this.id = id;
        this.courseName = courseName;
        this.section = section;
        this.sectionSpan = sectionSpan;
        this.weekDay = weekDay;
        this.classRoom = classRoom;
        this.courseFlag = courseFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSectionSpan() {
        return sectionSpan;
    }

    public void setSectionSpan(int sectionSpan) {
        this.sectionSpan = sectionSpan;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public int getCourseFlag() {
        return courseFlag;
    }

    public void setCourseFlag(int courseFlag) {
        this.courseFlag = courseFlag;
    }
}