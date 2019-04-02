package com.fingertech.kesforstudent.Controller;

import com.fingertech.kesforstudent.Rest.JSONResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
                                 @Field("password") String password);

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
    ///// Ganti Foto
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

    ///// Jadwal Guru
    @GET("teachers/kes_get_edulevel")
    Call<JSONResponse.ListEdulevel>kes_get_edulevel_get(@Header("Authorization") String authorization,
                                                        @Query("school_code") String school_code,
                                                        @Query("teacher_id") String teacher_id,
                                                        @Query("scyear_id") String scyear_id);

    ///// List Mapel
    @GET("teachers/kes_get_edulevel_cources")
    Call<JSONResponse.ListMapelEdu>kes_get_edulevel_cources_get(@Header("Authorization") String authorization,
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


}
