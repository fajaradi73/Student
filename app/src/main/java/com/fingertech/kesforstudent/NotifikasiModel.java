package com.fingertech.kesforstudent;

public class NotifikasiModel {
    private String title;
    private String body;
    private String time;
    private String status;
    private String type,id_notif,message_id,school_code,parent_message_id,member_id,student_id,classroom_id,agenda_date;

    public String getId_notif() {
        return id_notif;
    }

    public void setId_notif(String id_notif) {
        this.id_notif = id_notif;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getSchool_code() {
        return school_code;
    }

    public void setSchool_code(String school_code) {
        this.school_code = school_code;
    }

    public String getParent_message_id() {
        return parent_message_id;
    }

    public void setParent_message_id(String parent_message_id) {
        this.parent_message_id = parent_message_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(String classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getAgenda_date() {
        return agenda_date;
    }

    public void setAgenda_date(String agenda_date) {
        this.agenda_date = agenda_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
