package com.fingertech.kesforstudent.Controller;

import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Auth {


    /////Cari Sekolah
    @FormUrlEncoded
    @POST("auth/kes_search_school")
    Call<JSONResponse.School> search_school_post(@Field("key") String key);

    //////Login
    @FormUrlEncoded
    @POST("auth/kes_login")
    Call<JSONResponse>login_post(@Field("school_code") String school_code,
                                 @Field("username") String username,
                                 @Field("password") String password,
                                 @Field("firebase_token") String firebase_token,
                                 @Field("device_id") String device_id);

    //////// Forgot password
    @FormUrlEncoded
    @POST("auth/kes_forgot_password")
    Call<JSONResponse>forgot_password_post(@Field("email") String email);

    //////Ganti Password
    @FormUrlEncoded
    @PUT("auth/kes_change_password/{member_id}")
    Call<JSONResponse.ChangePassword>kes_change_password_post(@Header("Authorization") String authorization,
                                               @Path("member_id") String memberid,
                                               @Field("password") String password,
                                               @Field("old_pass") String old_pass,
                                               @Field("school_code") String school_code);

    //////GET PROFILE
    @GET("auth/kes_profile")
    Call<JSONResponse.GetProfile>kes_profile_get(@Header("Authorization") String authorization,
                                                 @Query("school_code") String school_code,
                                                 @Query("member_id") String member_id);

    ///// Ganti Foto
    @Multipart
    @POST("auth/kes_update_picture")
    Call<JSONResponse.UpdatePicture> kes_update_picture_post(@Header("Authorization") String authorization,
                                                             @Part ("school_code") RequestBody school_code,
                                                             @Part("member_id") RequestBody memberid,
                                                             @Part MultipartBody.Part picture_old,
                                                             @Part("pic_type") RequestBody pic_type);

    ///// Update Profile
    @FormUrlEncoded
    @PUT("auth/kes_update/{member_id}")
    Call<JSONResponse> kes_update_put(@Header("Authorization") String authorization,
                                      @Path("member_id") String memberid,
                                      @Field("school_code") String school_code,
                                      @Field("fullname") String fullname,
                                      @Field("mobile_phone") String mobile_phone,
                                      @Field("lastupdate") String lastupdate,
                                      @Field("gender") String gender,
                                      @Field("religion") String religion,
                                      @Field("birth_date") String birth_date);

    ////// Jadwal Pelajaran
    @GET("students/kes_class_schedule")
    Call<JSONResponse.JadwalPelajaran>kes_class_schedule_get(@Header("Authorization") String authorization,
                                                             @Query("school_code") String school_code,
                                                             @Query("student_id") String student_id,
                                                             @Query("classroom_id") String classroom_id);
    ///// Jadwal Ujian
    @GET("students/kes_exam_schedule")
    Call<JSONResponse.JadwalUjian>kes_exam_schedule_get(@Header("Authorization") String authorization,
                                                        @Query("school_code") String school_code,
                                                        @Query("student_id") String member_id,
                                                        @Query("classroom_id") String classroom_id,
                                                        @Query("semester_id") String semester_id);

    ///// Check Semester
    @GET("students/kes_check_semester")
    Call<JSONResponse.CheckSemester>kes_check_semester_get(@Header("Authorization") String authorization,
                                                           @Query("school_code") String school_code,
                                                           @Query("classroom_id") String classroom_id,
                                                           @Query("date_now") String date_now);
    ///// List Semester
    @GET("students/kes_list_semester")
    Call<JSONResponse.ListSemester>kes_list_semester_get(@Header("Authorization") String authorization,
                                                         @Query("school_code") String school_code,
                                                         @Query("classroom_id") String classroom_id);

    ///// List Mata Pelajaran
    @GET("students/kes_class_cources")
    Call<JSONResponse.ListMapel>kes_list_cources_get(@Header("Authorization") String authorization,
                                                     @Query("school_code") String school_code,
                                                     @Query("classroom_id") String classroom_id);

    ///// Tugas Anak
    @GET("students/kes_cources_score")
    Call<JSONResponse.TugasAnak>kes_cources_score_get(@Header("Authorization") String authorization,
                                                      @Query("school_code") String school_code,
                                                      @Query("student_id") String student_id,
                                                      @Query("classroom_id") String classroom_id,
                                                      @Query("semester_id") String semester_id);

    ///// Raport Anak
    @GET("students/kes_rapor_score")
    Call<JSONResponse.Raport>kes_rapor_score_get(@Header("Authorization") String authorization,
                                                 @Query("school_code") String school_code,
                                                 @Query("student_id") String student_id,
                                                 @Query("classroom_id") String classroom_id,
                                                 @Query("semester_id") String semester_id);

    ///// Classroom Detail
    @GET("students/kes_classroom_detail")
    Call<JSONResponse.ClassroomDetail>kes_classroom_detail_get(@Header("Authorization") String authorization,
                                                               @Query("school_code") String school_code,
                                                               @Query("classroom_id") String classroom_id);

    ///// Pesan Anak
    @GET("students/kes_message_inbox")
    Observable<JSONResponse.PesanAnak> kes_message_get(@Header("Authorization") String authorization,
                                                      @Query("school_code") String school_code,
                                                      @Query("student_id") String member_id,
                                                      @Query("date_from") String date_from,
                                                      @Query("date_to") String date_to);

    ///// Pesan Anak
    @GET("students/kes_message_inbox")
    Call<JSONResponse.PesanAnak> kes_message_anak_get(@Header("Authorization") String authorization,
                                                       @Query("school_code") String school_code,
                                                       @Query("student_id") String member_id,
                                                       @Query("date_from") String date_from,
                                                       @Query("date_to") String date_to);

    //// Pesan Detail
    @GET("students/kes_message_detail")
    Call<JSONResponse.PesanDetail>kes_message_detail_get(@Header("Authorization") String authorization,
                                                         @Query("school_code") String school_code,
                                                         @Query("student_id") String student_id,
                                                         @Query("classroom_id") String classroom_id,
                                                         @Query("message_id") String message_id);

    ///// Absen Siswa
    @GET("students/kes_class_attendance")
    Call<JSONResponse.AbsenSiswa>kes_class_attendance_get(@Header("Authorization") String authorization,
                                                          @Query("school_code") String school_code,
                                                          @Query("student_id") String student_id,
                                                          @Query("classroom_id") String classroom_id,
                                                          @Query("attendance_month") String attendance_month,
                                                          @Query("attendance_year") String attendance_year);
    ///// Calendar Kelas
    @GET("students/kes_class_calendar")
    Call<JSONResponse.ClassCalendar>kes_class_calendar_get(@Header("Authorization") String authorization,
                                                           @Query("school_code") String school_code,
                                                           @Query("student_id") String student_id,
                                                           @Query("classroom_id") String classroom_id,
                                                           @Query("calendar_month") String calendar_month,
                                                           @Query("calendar_year") String calendar_year);

    ///// Calendar Detail
    @GET("students/kes_calendar_detail")
    Call<JSONResponse.CalendarDetail>kes_calendar_detail_get(@Header("Authorization") String authorization,
                                                             @Query("school_code") String school_code,
                                                             @Query("calendar_id") String calendar_id);

    ///// Jadwal Guru
    @GET("teachers/kes_class_schedule")
    Call<JSONResponse.JadwalGuru>kes_class_schedule_teacher_get(@Header("Authorization") String authorization,
                                                             @Query("school_code") String school_code,
                                                             @Query("teacher_id") String teacher_id,
                                                             @Query("scyear_id") String scyear_id);

    //revisiabsen
    @GET("teachers/kes_classroom_absent")
    Call<JSONResponse.Absen>kes_classroom_absent_get(@Header("Authorization") String authorization,
                                                     @Query("school_code") String school_code,
                                                     @Query("teacher_id") String teacher_id,
                                                     @Query("scyear_id") String scyear_id,
                                                     @Query("classroom_id") String classroom_id,
                                                     @Query ("schedule_time_id") String schedule_time_id);

    //absenmurid
    @GET("teachers/kes_get_student")
    Call<JSONResponse.ListMurid>kes_get_student_get(@Header("Authorization") String authorization,
                                                    @Query("school_code") String school_code,
                                                    @Query("teacher_id") String teacher_id,
                                                    @Query("scyear_id") String scyear_id,
                                                    @Query("classroom_id") String classroom_id);

    //DataAttidude
    @GET("teachers/kes_attitude")
    Call<JSONResponse.Attidude>kes_attitude_get(@Header("Authorization") String authorization,
                                                @Query("school_code") String school_code,
                                                @Query("teacher_id") String teacher_id,
                                                @Query("scyear_id") String  scyear_id );

    //DataAttidude
    @GET("teachers/kes_attitude")
    Call<JsonElement>kes_attitudes_get(@Header("Authorization") String authorization,
                                        @Query("school_code") String school_code,
                                        @Query("teacher_id") String teacher_id,
                                        @Query("scyear_id") String  scyear_id );

    ///// Jadwal Guru
    @GET("teachers/kes_get_edulevel")
    Call<JSONResponse.ListEdulevel>kes_get_edulevel_get(@Header("Authorization") String authorization,
                                                        @Query("school_code") String school_code,
                                                        @Query("teacher_id") String teacher_id,
                                                        @Query("scyear_id") String scyear_id);

    @GET("teachers/kes_get_classroom")
    Call<JSONResponse.ListKelas>kes_get_classroom_get(@Header("Authorization") String authorization,
                                                        @Query("school_code") String school_code,
                                                        @Query("teacher_id") String teacher_id,
                                                        @Query("scyear_id") String scyear_id);


    ///// List Mapel
    @GET("teachers/kes_get_cources")
    Call<JSONResponse.ListMapelEdu>kes_get_edulevel_cources_get(@Header("Authorization") String authorization,
                                                                @Query("school_code") String school_code,
                                                                @Query("teacher_id") String teacher_id,
                                                                @Query("classroom_id") String classroom_id,
                                                                @Query("scyear_id") String scyear_id);

    ///// List Mapel
    @GET("teachers/kes_get_edulevel_cources")
    Call<JSONResponse.ListMapelEdu>kes_get_edulevel_cource_get(@Header("Authorization") String authorization,
                                                                @Query("school_code") String school_code,
                                                                @Query("teacher_id") String teacher_id,
                                                                @Query("edulevel_id") String edulevel_id,
                                                                @Query("scyear_id") String scyear_id);

    //// List Silabus
    @GET("teachers/kes_silabus")
    Call<JSONResponse.ListSilabus>kes_silabus_get(@Header("Authorization") String authorization,
                                                   @Query("school_code") String school_code,
                                                   @Query("teacher_id") String teacher_id,
                                                   @Query("edulevel_id") String edulevel_id,
                                                   @Query("cources_id") String cources_id,
                                                   @Query("scyear_id") String scyear_id);

    //// download rapor
    @GET("students/kes_rapor_pdf")
    Call<ResponseBody>kes_rapor_pdf(@Header("Authorization") String authorization,
                                    @Query("school_code") String school_code,
                                    @Query("student_id") String student_id,
                                    @Query("classroom_id") String classroom_id,
                                    @Query("semester_id") String semester_id);

    ///// Tambah Silabus
    @Multipart
    @POST("teachers/kes_add_silabus")
    Call<JSONResponse.UploadSilabus> kes_add_silabus_post(@Header("Authorization") String authorization,
                                                          @Part ("school_code") RequestBody school_code,
                                                          @Part("teacher_id") RequestBody teacher_id,
                                                          @Part ("cources_id") RequestBody cources_id,
                                                          @Part("edulevel_id") RequestBody edulevel_id,
                                                          @Part MultipartBody.Part filename,
                                                          @Part("title") RequestBody title,
                                                          @Part("scyear_id") RequestBody scyear_id);

    ///// Tambah Tugas
    @Multipart
    @POST("teachers/kes_add_exercises")
    Call<JSONResponse.UploadTugas> kes_add_exercises_post(@Header("Authorization") String authorization,
                                                            @Part ("school_code") RequestBody school_code,
                                                            @Part("teacher_id") RequestBody teacher_id,
                                                            @Part("classroom_id") RequestBody classroom_id,
                                                            @Part ("cources_id") RequestBody cources_id,
                                                            @Part("exam_date") RequestBody exam_date,
                                                            @Part("exercises_type") RequestBody exercises_type,
                                                            @Part MultipartBody.Part filename,
                                                            @Part("exam_type") RequestBody exam_type,
                                                            @Part("exam_desc") RequestBody exam_desc,
                                                            @Part("scyear_id") RequestBody scyear_id);

    ///// Kalendar Guru
    @GET("teachers/kes_dashboard_calendar")
    Call<JSONResponse.DashboardCalendar>kes_dashboard_get(@Header("Authorization") String authorization,
                                                  @Query("school_code") String school_code,
                                                  @Query("teacher_id") String teacher_id,
                                                  @Query("calendar_month") String calendar_month,
                                                  @Query("calendar_year") String calendar_year,
                                                  @Query("scyear_id") String scyear_id);

    ///// Kalendar Guru
    @GET("teachers/kes_dashboard_calendar")
    Call<JsonElement>kes_dashboard_calendar_get(@Header("Authorization") String authorization,
                                                @Query("school_code") String school_code,
                                                @Query("teacher_id") String teacher_id,
                                                @Query("calendar_month") String calendar_month,
                                                @Query("calendar_year") String calendar_year,
                                                @Query("scyear_id") String scyear_id);

    ///// List Exam
    @GET("teachers/kes_exam_type")
    Call<JSONResponse.ListExam> kes_exam_type_get(@Header("Authorization") String authorization,
                                         @Query("school_code") String school_code,
                                         @Query("teacher_id") String teacher_id);

    //// Penilaian
    @GET("teachers/kes_score")
    Call<JsonElement>kes_score_get(@Header("Authorization") String authorization,
                                   @Query("school_code") String school_code,
                                   @Query("teacher_id") String teacher_id,
                                   @Query("scyear_id") String scyear_id,
                                   @Query("classroom_id") String classroom_id,
                                   @Query("semester_id") String semester_id,
                                   @Query("cources_id") String cources_id);

    ///// Agenda Guru
    @GET("teachers/kes_class_agenda_teacher")
    Call<JSONResponse.ListAgenda> kes_class_agenda_teacher_get(@Header("Authorization") String authorization,
                                                               @Query("school_code") String school_code,
                                                               @Query("teacher_id") String teacher_id,
                                                               @Query("classroom_id") String classroom_id,
                                                               @Query("cources_id") String cources_id,
                                                               @Query("year_month") String year_month,
                                                               @Query("day_now") String day_now);

    ///// agenda Anak
    @GET("students/kes_class_agenda_student")
    Call<JSONResponse.ListAgenda> kes_class_agenda_student_get(@Header("Authorization") String authorization,
                                                               @Query("school_code") String school_code,
                                                               @Query("student_id") String student_id,
                                                               @Query("classroom_id") String classroom_id,
                                                               @Query("year_month") String year_month,
                                                               @Query("day_now") String day_now);

    ///// Raport Anak
    @GET("students/kes_rapor_score")
    Call<JsonElement>kes_rapor_get(@Header("Authorization") String authorization,
                                   @Query("school_code") String school_code,
                                   @Query("student_id") String student_id,
                                   @Query("classroom_id") String classroom_id,
                                   @Query("semester_id") String semester_id);
    //// detail rapor
    @GET("students/kes_cources_score_detail")
    Call<JSONResponse.ListDetailRapor> kes_cources_score_detail_get(@Header("Authorization") String authorization,
                                                                    @Query("school_code") String school_code,
                                                                    @Query("student_id") String student_id,
                                                                    @Query("classroom_id") String classroom_id,
                                                                    @Query("cources_id") String cources_id,
                                                                    @Query("semester_id") String semester_id,
                                                                    @Query("exam_type") String exam_type);

    ///Tambah Agenda
    @FormUrlEncoded
    @POST("teachers/kes_add_agenda")
    Call<JSONResponse.AddAgenda>kes_add_agenda_post(@Header("Authorization") String authorization,
                                                      @Field("school_code") String school_code,
                                                      @Field("teacher_id") String teacher_id,
                                                      @Field("classroom_id") String classroom_id,
                                                      @Field("agenda_date") String agenda_date,
                                                      @Field("cources_id") String cources_id,
                                                      @Field("agenda_desc") String agenda_desc,
                                                      @Field("scyear_id") String scyear_id);

    //// Pesan Guru
    @GET("teachers/kes_message_inbox")
    Call<JSONResponse.ListPesanGuru> kes_message_inbox_get(@Header("Authorization") String authorization,
                                                      @Query("school_code") String school_code,
                                                      @Query("teacher_id") String teacher_id,
                                                      @Query("date_from") String date_from,
                                                      @Query("date_to") String date_to);

    //// Detail Pesan Guru
    @GET("teachers/kes_inbox_detail")
    Call<JSONResponse.PesanDetail>kes_inbox_detail_get(@Header("Authorization") String authorization,
                                                       @Query("school_code") String school_code,
                                                       @Query("teacher_id") String parent_id,
                                                       @Query("message_id") String message_id,
                                                       @Query("parent_message_id") String parent_message_id);

    //// List Whattodolist
    @GET("teachers/kes_whattodolist")
    Call<JsonElement>kes_whattodolist_get(@Header("Authorization") String authorization,
                                          @Query("school_code") String school_code,
                                          @Query("teacher_id") String teacher_id,
                                          @Query("scyear_id") String scyear_id);

    //// Detail Pesan Terkirim Guru
    @GET("teachers/kes_message_sent")
    Call<JSONResponse.ListPesanTerkirimGuru>kes_message_sent_get(@Header("Authorization") String authorization,
                                                                 @Query("school_code") String school_code,
                                                                 @Query("teacher_id") String parent_id,
                                                                 @Query("date_from") String date_from,
                                                                 @Query("date_to") String parent_date_to);
    //// List Whattodolist
    @GET("teachers/kes_get_admin")
    Call<JSONResponse.ListAdmin>kes_get_admin_get(@Header("Authorization") String authorization,
                                          @Query("school_code") String school_code,
                                          @Query("teacher_id") String teacher_id,
                                          @Query("scyear_id") String scyear_id);

    ///Kirim Pesan
    @FormUrlEncoded
    @POST("teachers/kes_send_message")
    Call<JSONResponse.KirimPesanGuru>kes_send_message(@Header("Authorization") String authorization,
                                                      @Field("school_code") String school_code,
                                                      @Field("teacher_id") String teacher_id,
                                                      @Field("type_id") String type_id,
                                                      @Field("admin_id") String admin_id,
                                                      @Field("classroom_id") String classroom_id,
                                                      @Field("student_id") String student_id,
                                                      @Field("cources_id") String cources_id,
                                                      @Field("message_title") String message_title,
                                                      @Field("message") String message);

    ////// Lesson Review Guru
    @GET("teachers/kes_dashboard_lesson")
    Call<JSONResponse.LessonReview> kes_dashboard_lesson_get(@Header("Authorization") String authorization,
                                                             @Query("school_code") String school_code,
                                                             @Query("teacher_id") String teacher_id,
                                                             @Query("cources_id") String cources_id,
                                                             @Query("classroom_id") String classroom_id,
                                                             @Query("scyear_id") String scyear_id);

    //// lesson review Murid
    @GET("students/kes_lesson_review")
    Call<JSONResponse.LessonReview> kes_lesson_review_get(@Header("Authorization") String authorization,
                                                          @Query("school_code") String school_code,
                                                          @Query("student_id") String student_id,
                                                          @Query("classroom_id") String classroom_id,
                                                          @Query("cources_id") String cources_id);


    //// insert absen
    @FormUrlEncoded
    @POST("teachers/kes_insertdb_student_attendance")
    Call<JSONResponse.InsertData>kes_insertdb_student_attendance_post(@Header("Authorization") String authorization,
                                                           @Field("school_code") String school_code,
                                                           @Field("teacher_id") String teacher_id,
                                                           @Field("classroom_id") String classroom_id,
                                                           @Field("schedule_time_id") String schedule_time_id,
                                                           @Field("schedule_date") String schedule_date,
                                                           @Body ArrayList<JSONObject> jsonObjects);

    ///// Logout
    @FormUrlEncoded
    @POST("auth/kes_logout")
    Call<JSONResponse.Logout> kes_logout_post(@Header("Authorization") String authorization,
                                               @Field("school_code") String school_code,
                                               @Field("member_id") String member_id);

    //////Login
    @FormUrlEncoded
    @POST("teachers/kes_reply_message")
    Call<JSONResponse>kes_reply_post(@Header("Authorization") String authorization,
                                     @Field("school_code") String school_code,
                                     @Field("teacher_id") String teacher_id,
                                     @Field("message_id") String message_id,
                                     @Field("parent_message_id") String parent_message_id,
                                     @Field("message_cont") String message_cont);


    //////Delete Agenda
    @FormUrlEncoded
    @POST("teachers/kes_delete_agenda")
    Call<JSONResponse.DeleteAgenda>kes_delete_agenda_post(@Header("Authorization") String authorization,
                                             @Field("school_code") String school_code,
                                             @Field("teacher_id") String teacher_id,
                                             @Field("agenda_id") String agenda_id);

    //////Delete Tugas
    @FormUrlEncoded
    @POST("teachers/kes_delete_exercises")
    Call<JSONResponse.DeleteTugas>kes_delete_exercises_post(@Header("Authorization") String authorization,
                                                          @Field("school_code") String school_code,
                                                          @Field("teacher_id") String teacher_id,
                                                          @Field("exercises_id") String exercises_id);

    //////Edit Agenda
    @FormUrlEncoded
    @POST("teachers/kes_edit_agenda")
    Call<JSONResponse.AddAgenda>kes_edit_agenda_post(@Header("Authorization") String authorization,
                                                     @Field("school_code") String school_code,
                                                     @Field("teacher_id") String teacher_id,
                                                     @Field("classroom_id") String classroom_id,
                                                     @Field("agenda_date") String agenda_date,
                                                     @Field("cources_id") String cources_id,
                                                     @Field("agenda_desc") String agenda_desc,
                                                     @Field("agenda_id") String agenda_id,
                                                     @Field("scyear_id") String scyear_id);
}
