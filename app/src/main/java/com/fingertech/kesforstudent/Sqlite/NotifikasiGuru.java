package com.fingertech.kesforstudent.Sqlite;

public class NotifikasiGuru {
    public static final String TABLE                    = "table_notifikasi_guru";
    public static final String KEY_CourseId             = "schoolId";
    public static final String KEY_NotifId              = "id_notifikasi";
    public static final String KEY_Title                = "title_notifikasi";
    public static final String KEY_Body                 = "body_notifikasi";
    public static final String KEY_School_code          = "school_code";
    public static final String KEY_parent_message_id    = "parent_message_id";
    public static final String KEY_message_id           = "message_id";
    public static final String KEY_type                 = "type";
    public static final String KEY_time                 = "time";
    public static final String KEY_status               = "status";
    public static final String KEY_member_id            = "member_id";
    public static final String KEY_student_id           = "student_id";
    public static final String KEY_classroom_id         = "classroom_id";
    public static final String KEY_agenda_date          = "agenda_date";


    private String id,title, body,school_code,parent_message_id,message_id,type,time,status,member_id,student_id,classroom_id,agenda_date;
    private int id_notifikasi;
    public NotifikasiGuru() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NotifikasiGuru(String id, int id_notifikasi,String title, String body, String message_id, String school_code, String parent_message_id, String type, String time,String status,String member_id, String student_id, String classroom_id, String agenda_date) {
        this.id                 = id;
        this.id_notifikasi      = id_notifikasi;
        this.title              = title;
        this.body               = body;
        this.message_id         = message_id;
        this.school_code        = school_code;
        this.parent_message_id  = parent_message_id;
        this.type               = type;
        this.time               = time;
        this.status             = status;
        this.member_id          = member_id;
        this.student_id         = student_id;
        this.classroom_id       = classroom_id;
        this.agenda_date        = agenda_date;
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

    public int getId_notifikasi() {
        return id_notifikasi;
    }

    public void setId_notifikasi(int id_notifikasi) {
        this.id_notifikasi = id_notifikasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }
}
