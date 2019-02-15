package com.fingertech.kesforstudent.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponse {

    @SerializedName("status")
    public Integer status;

    @SerializedName("code")
    public String code;

    @SerializedName("token")
    public String token;

    @SerializedName("data")
    public Login_Data login_data;

    //////// Data Response - Login Public
    public class Login_Data {
        @SerializedName("member_type")
        public String member_type;
        @SerializedName("member_id")
        public String member_id;
        @SerializedName("fullname")
        public String fullname;
        @SerializedName("token")
        public String token;

        public String getMember_type() {
            return member_type;
        }

        public void setMember_type(String member_type) {
            this.member_type = member_type;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    //////// Response Search School
    public class School{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<SData> data;

        public List<SData> getData() {
            return data;
        }
    }
    public static class SData {
        @SerializedName("schoolid")
        @Expose
        private String schoolid;
        @SerializedName("school_code")
        @Expose
        private String school_code;
        @SerializedName("school_name")
        @Expose
        private String school_name;
        @SerializedName("school_quez")
        @Expose
        private String school_quez;
        @SerializedName("school_email")
        @Expose
        private String school_email;
        @SerializedName("school_phone")
        @Expose
        private String school_phone;
        @SerializedName("school_address")
        @Expose
        private String school_address;
        @SerializedName("school_contact")
        @Expose
        private String school_contact;
        @SerializedName("school_publish")
        @Expose
        private String school_publish;
        @SerializedName("picture")
        @Expose
        private String picture;
        @SerializedName("datez")
        @Expose
        private String datez;
        @SerializedName("member_id")
        @Expose
        private String member_id;
        @SerializedName("lastupdate")
        @Expose
        private String lastupdate;
        @SerializedName("school_dbase")
        @Expose
        private String school_dbase;
        @SerializedName("lesson_hour")
        @Expose
        private String lesson_hour;
        @SerializedName("schooldetailid")
        @Expose
        private String schooldetailid;
        @SerializedName("school_id")
        @Expose
        private String school_id;
        @SerializedName("jenjang_pendidikan")
        @Expose
        private String jenjang_pendidikan;
        @SerializedName("rt")
        @Expose
        private String rt;
        @SerializedName("rw")
        @Expose
        private String rw;
        @SerializedName("kode_pos")
        @Expose
        private String kode_pos;
        @SerializedName("kelurahan")
        @Expose
        private String kelurahan;
        @SerializedName("provinsi_id")
        @Expose
        private String provinsi_id;
        @SerializedName("kabupaten_id")
        @Expose
        private String kabupaten_id;
        @SerializedName("kecamatan_id")
        @Expose
        private String kecamatan_id;
        @SerializedName("sk_pendirian")
        @Expose
        private String sk_pendirian;
        @SerializedName("tanggal_pendirian")
        @Expose
        private String tanggal_pendirian;
        @SerializedName("sk_izin")
        @Expose
        private String sk_izin;
        @SerializedName("tanggal_izin")
        @Expose
        private String tanggal_izin;
        @SerializedName("kebutuhan_khusus")
        @Expose
        private String kebutuhan_khusus;
        @SerializedName("no_rekening")
        @Expose
        private String no_rekening;
        @SerializedName("nama_bank")
        @Expose
        private String nama_bank;
        @SerializedName("cabang")
        @Expose
        private String cabang;
        @SerializedName("account_name")
        @Expose
        private String account_name;
        @SerializedName("mbs")
        @Expose
        private String mbs;
        @SerializedName("tanah_milik")
        @Expose
        private String tanah_milik;
        @SerializedName("tanah_bukan_milik")
        @Expose
        private String tanah_bukan_milik;
        @SerializedName("nwp")
        @Expose
        private String nwp;
        @SerializedName("npwp")
        @Expose
        private String npwp;
        @SerializedName("no_fax")
        @Expose
        private String no_fax;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("waktu_penyelenggaraan")
        @Expose
        private String waktu_penyelenggaraan;
        @SerializedName("bersedia_menerima_bos")
        @Expose
        private String bersedia_menerima_bos;
        @SerializedName("sertifikasi_iso")
        @Expose
        private String sertifikasi_iso;
        @SerializedName("sumber_listrik")
        @Expose
        private String sumber_listrik;
        @SerializedName("daya_listrik")
        @Expose
        private String daya_listrik;
        @SerializedName("akses_internet")
        @Expose
        private String akses_internet;
        @SerializedName("internet_alternatif")
        @Expose
        private String internet_alternatif;
        @SerializedName("kepsek")
        @Expose
        private String kepsek;
        @SerializedName("operator")
        @Expose
        private String operator;
        @SerializedName("akreditasi")
        @Expose
        private String akreditasi;
        @SerializedName("kurikulum")
        @Expose
        private String kurikulum;
        @SerializedName("status_sekolah")
        @Expose
        private String status_sekolah;
        @SerializedName("status_kepemilikan")
        @Expose
        private String status_kepemilikan;
        @SerializedName("tguru")
        @Expose
        private String tguru;
        @SerializedName("tsiswa_pria")
        @Expose
        private String tsiswa_pria;
        @SerializedName("tsiswa_wanita")
        @Expose
        private String tsiswa_wanita;
        @SerializedName("rombel")
        @Expose
        private String rombel;
        @SerializedName("ruang_kelas")
        @Expose
        private String ruang_kelas;
        @SerializedName("laboratorium")
        @Expose
        private String laboratorium;
        @SerializedName("perpustakaan")
        @Expose
        private String perpustakaan;
        @SerializedName("sanitasi")
        @Expose
        private String sanitasi;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("detail_picture")
        @Expose
        private String detail_picture;
        @SerializedName("kurikulum_id")
        @Expose
        private String kurikulum_id;
        @SerializedName("edulevel_id")
        @Expose
        private String edulevel_id;

        public String getSchoolid() {
            return schoolid;
        }

        public void setSchoolid(String schoolid) {
            this.schoolid = schoolid;
        }

        public String getSchool_code() {
            return school_code;
        }

        public void setSchool_code(String school_code) {
            this.school_code = school_code;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getSchool_quez() {
            return school_quez;
        }

        public void setSchool_quez(String school_quez) {
            this.school_quez = school_quez;
        }

        public String getSchool_email() {
            return school_email;
        }

        public void setSchool_email(String school_email) {
            this.school_email = school_email;
        }

        public String getSchool_phone() {
            return school_phone;
        }

        public void setSchool_phone(String school_phone) {
            this.school_phone = school_phone;
        }

        public String getSchool_address() {
            return school_address;
        }

        public void setSchool_address(String school_address) {
            this.school_address = school_address;
        }

        public String getSchool_contact() {
            return school_contact;
        }

        public void setSchool_contact(String school_contact) {
            this.school_contact = school_contact;
        }

        public String getSchool_publish() {
            return school_publish;
        }

        public void setSchool_publish(String school_publish) {
            this.school_publish = school_publish;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getLastupdate() {
            return lastupdate;
        }

        public void setLastupdate(String lastupdate) {
            this.lastupdate = lastupdate;
        }

        public String getSchool_dbase() {
            return school_dbase;
        }

        public void setSchool_dbase(String school_dbase) {
            this.school_dbase = school_dbase;
        }

        public String getLesson_hour() {
            return lesson_hour;
        }

        public void setLesson_hour(String lesson_hour) {
            this.lesson_hour = lesson_hour;
        }

        public String getSchooldetailid() {
            return schooldetailid;
        }

        public void setSchooldetailid(String schooldetailid) {
            this.schooldetailid = schooldetailid;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getJenjang_pendidikan() {
            return jenjang_pendidikan;
        }

        public void setJenjang_pendidikan(String jenjang_pendidikan) {
            this.jenjang_pendidikan = jenjang_pendidikan;
        }

        public String getRt() {
            return rt;
        }

        public void setRt(String rt) {
            this.rt = rt;
        }

        public String getRw() {
            return rw;
        }

        public void setRw(String rw) {
            this.rw = rw;
        }

        public String getKode_pos() {
            return kode_pos;
        }

        public void setKode_pos(String kode_pos) {
            this.kode_pos = kode_pos;
        }

        public String getKelurahan() {
            return kelurahan;
        }

        public void setKelurahan(String kelurahan) {
            this.kelurahan = kelurahan;
        }

        public String getProvinsi_id() {
            return provinsi_id;
        }

        public void setProvinsi_id(String provinsi_id) {
            this.provinsi_id = provinsi_id;
        }

        public String getKabupaten_id() {
            return kabupaten_id;
        }

        public void setKabupaten_id(String kabupaten_id) {
            this.kabupaten_id = kabupaten_id;
        }

        public String getKecamatan_id() {
            return kecamatan_id;
        }

        public void setKecamatan_id(String kecamatan_id) {
            this.kecamatan_id = kecamatan_id;
        }

        public String getSk_pendirian() {
            return sk_pendirian;
        }

        public void setSk_pendirian(String sk_pendirian) {
            this.sk_pendirian = sk_pendirian;
        }

        public String getTanggal_pendirian() {
            return tanggal_pendirian;
        }

        public void setTanggal_pendirian(String tanggal_pendirian) {
            this.tanggal_pendirian = tanggal_pendirian;
        }

        public String getSk_izin() {
            return sk_izin;
        }

        public void setSk_izin(String sk_izin) {
            this.sk_izin = sk_izin;
        }

        public String getTanggal_izin() {
            return tanggal_izin;
        }

        public void setTanggal_izin(String tanggal_izin) {
            this.tanggal_izin = tanggal_izin;
        }

        public String getKebutuhan_khusus() {
            return kebutuhan_khusus;
        }

        public void setKebutuhan_khusus(String kebutuhan_khusus) {
            this.kebutuhan_khusus = kebutuhan_khusus;
        }

        public String getNo_rekening() {
            return no_rekening;
        }

        public void setNo_rekening(String no_rekening) {
            this.no_rekening = no_rekening;
        }

        public String getNama_bank() {
            return nama_bank;
        }

        public void setNama_bank(String nama_bank) {
            this.nama_bank = nama_bank;
        }

        public String getCabang() {
            return cabang;
        }

        public void setCabang(String cabang) {
            this.cabang = cabang;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public String getMbs() {
            return mbs;
        }

        public void setMbs(String mbs) {
            this.mbs = mbs;
        }

        public String getTanah_milik() {
            return tanah_milik;
        }

        public void setTanah_milik(String tanah_milik) {
            this.tanah_milik = tanah_milik;
        }

        public String getTanah_bukan_milik() {
            return tanah_bukan_milik;
        }

        public void setTanah_bukan_milik(String tanah_bukan_milik) {
            this.tanah_bukan_milik = tanah_bukan_milik;
        }

        public String getNwp() {
            return nwp;
        }

        public void setNwp(String nwp) {
            this.nwp = nwp;
        }

        public String getNpwp() {
            return npwp;
        }

        public void setNpwp(String npwp) {
            this.npwp = npwp;
        }

        public String getNo_fax() {
            return no_fax;
        }

        public void setNo_fax(String no_fax) {
            this.no_fax = no_fax;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getWaktu_penyelenggaraan() {
            return waktu_penyelenggaraan;
        }

        public void setWaktu_penyelenggaraan(String waktu_penyelenggaraan) {
            this.waktu_penyelenggaraan = waktu_penyelenggaraan;
        }

        public String getBersedia_menerima_bos() {
            return bersedia_menerima_bos;
        }

        public void setBersedia_menerima_bos(String bersedia_menerima_bos) {
            this.bersedia_menerima_bos = bersedia_menerima_bos;
        }

        public String getSertifikasi_iso() {
            return sertifikasi_iso;
        }

        public void setSertifikasi_iso(String sertifikasi_iso) {
            this.sertifikasi_iso = sertifikasi_iso;
        }

        public String getSumber_listrik() {
            return sumber_listrik;
        }

        public void setSumber_listrik(String sumber_listrik) {
            this.sumber_listrik = sumber_listrik;
        }

        public String getDaya_listrik() {
            return daya_listrik;
        }

        public void setDaya_listrik(String daya_listrik) {
            this.daya_listrik = daya_listrik;
        }

        public String getAkses_internet() {
            return akses_internet;
        }

        public void setAkses_internet(String akses_internet) {
            this.akses_internet = akses_internet;
        }

        public String getInternet_alternatif() {
            return internet_alternatif;
        }

        public void setInternet_alternatif(String internet_alternatif) {
            this.internet_alternatif = internet_alternatif;
        }

        public String getKepsek() {
            return kepsek;
        }

        public void setKepsek(String kepsek) {
            this.kepsek = kepsek;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getAkreditasi() {
            return akreditasi;
        }

        public void setAkreditasi(String akreditasi) {
            this.akreditasi = akreditasi;
        }

        public String getKurikulum() {
            return kurikulum;
        }

        public void setKurikulum(String kurikulum) {
            this.kurikulum = kurikulum;
        }

        public String getStatus_sekolah() {
            return status_sekolah;
        }

        public void setStatus_sekolah(String status_sekolah) {
            this.status_sekolah = status_sekolah;
        }

        public String getStatus_kepemilikan() {
            return status_kepemilikan;
        }

        public void setStatus_kepemilikan(String status_kepemilikan) {
            this.status_kepemilikan = status_kepemilikan;
        }

        public String getTguru() {
            return tguru;
        }

        public void setTguru(String tguru) {
            this.tguru = tguru;
        }

        public String getTsiswa_pria() {
            return tsiswa_pria;
        }

        public void setTsiswa_pria(String tsiswa_pria) {
            this.tsiswa_pria = tsiswa_pria;
        }

        public String getTsiswa_wanita() {
            return tsiswa_wanita;
        }

        public void setTsiswa_wanita(String tsiswa_wanita) {
            this.tsiswa_wanita = tsiswa_wanita;
        }

        public String getRombel() {
            return rombel;
        }

        public void setRombel(String rombel) {
            this.rombel = rombel;
        }

        public String getRuang_kelas() {
            return ruang_kelas;
        }

        public void setRuang_kelas(String ruang_kelas) {
            this.ruang_kelas = ruang_kelas;
        }

        public String getLaboratorium() {
            return laboratorium;
        }

        public void setLaboratorium(String laboratorium) {
            this.laboratorium = laboratorium;
        }

        public String getPerpustakaan() {
            return perpustakaan;
        }

        public void setPerpustakaan(String perpustakaan) {
            this.perpustakaan = perpustakaan;
        }

        public String getSanitasi() {
            return sanitasi;
        }

        public void setSanitasi(String sanitasi) {
            this.sanitasi = sanitasi;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getDetail_picture() {
            return detail_picture;
        }

        public void setDetail_picture(String detail_picture) {
            this.detail_picture = detail_picture;
        }

        public String getKurikulum_id() {
            return kurikulum_id;
        }

        public void setKurikulum_id(String kurikulum_id) {
            this.kurikulum_id = kurikulum_id;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

    }

    /////// Response Ganti Password
    public class ChangePassword{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public String data;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    ////// Response GetProfile
    public class GetProfile{

        @SerializedName("status")
        public int status;
        @SerializedName("data")
        private DataProfile data;

        public void setData(DataProfile data){
            this.data = data;
        }

        public DataProfile getData(){
            return data;
        }

        public void setStatus(int status){
            this.status = status;
        }

        public int getStatus(){
            return status;
        }
    }
    public class DataProfile{
        @SerializedName("fullname")
        public String fullname;

        @SerializedName("email")
        public String email;

        @SerializedName("mobile_phone")
        public String mobile_phone;

        @SerializedName("address")
        public String address;

        @SerializedName("picture")
        public String picture;

        @SerializedName("last_login")
        public String last_login;

        @SerializedName("member_type")
        public String member_type;

        @SerializedName("last_update")
        public String last_update;

        @SerializedName("member_code")
        public String member_code;

        @SerializedName("gender")
        public String gender;

        @SerializedName("religion")
        public String religion;

        @SerializedName("birth_date")
        public String birth_date;

        @SerializedName("birth_place")
        public String birth_place;

        @SerializedName("classroom_id")
        public String classroom_id;

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile_phone() {
            return mobile_phone;
        }

        public void setMobile_phone(String mobile_phone) {
            this.mobile_phone = mobile_phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getLast_login() {
            return last_login;
        }

        public void setLast_login(String last_login) {
            this.last_login = last_login;
        }

        public String getMember_type() {
            return member_type;
        }

        public void setMember_type(String member_type) {
            this.member_type = member_type;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public String getMember_code() {
            return member_code;
        }

        public void setMember_code(String member_code) {
            this.member_code = member_code;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getBirth_date() {
            return birth_date;
        }

        public void setBirth_date(String birth_date) {
            this.birth_date = birth_date;
        }

        public String getBirth_place() {
            return birth_place;
        }

        public void setBirth_place(String birth_place) {
            this.birth_place = birth_place;
        }
    }

    ////// Response Ganti Foto
    public class UpdatePicture{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("message")
        private String message;

        @SerializedName("path")
        private String path;

        public String getMessage() {
            return message;
        }

        public String getPath() {
            return path;
        }
    }

    ////// Response Jadwal Pelajaran
    public class JadwalPelajaran{


        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<JadwalData> data;

        @SerializedName("status")
        public int status;

        public void setCode(String code){
            this.code = code;
        }

        public String getCode(){
            return code;
        }

        public void setData(List<JadwalData> data){
            this.data = data;
        }

        public List<JadwalData> getData(){
            return data;
        }

        public void setStatus(int status){
            this.status = status;
        }

        public int getStatus(){
            return status;
        }
    }
    public class JadwalData{

        @SerializedName("day_type")
        public String dayType;

        @SerializedName("schedule_class")
        public List<ScheduleClassItem> scheduleClass;

        @SerializedName("day_name")
        public String dayName;

        @SerializedName("dayid")
        public String dayid;

        @SerializedName("day_status")
        public String dayStatus;

        public void setDayType(String dayType){
            this.dayType = dayType;
        }

        public String getDayType(){
            return dayType;
        }

        public void setScheduleClass(List<ScheduleClassItem> scheduleClass){
            this.scheduleClass = scheduleClass;
        }

        public List<ScheduleClassItem> getScheduleClass(){
            return scheduleClass;
        }

        public void setDayName(String dayName){
            this.dayName = dayName;
        }

        public String getDayName(){
            return dayName;
        }

        public void setDayid(String dayid){
            this.dayid = dayid;
        }

        public String getDayid(){
            return dayid;
        }

        public void setDayStatus(String dayStatus){
            this.dayStatus = dayStatus;
        }

        public String getDayStatus(){
            return dayStatus;
        }
    }
    public class ScheduleClassItem{


        @SerializedName("timez_ok")
        private String timezOk;

        @SerializedName("teacher_name")
        private String teacherName;

        @SerializedName("schedule_time")
        private String scheduleTime;

        @SerializedName("cources_id")
        private String courcesId;

        @SerializedName("teacher_id")
        private String teacherId;

        @SerializedName("lesson_duration")
        private int lessonDuration;

        @SerializedName("cources_name")
        private String courcesName;

        @SerializedName("timez_finish")
        private String timezFinish;

        public void setTimezOk(String timezOk){
            this.timezOk = timezOk;
        }

        public String getTimezOk(){
            return timezOk;
        }

        public void setTeacherName(String teacherName){
            this.teacherName = teacherName;
        }

        public String getTeacherName(){
            return teacherName;
        }

        public void setScheduleTime(String scheduleTime){
            this.scheduleTime = scheduleTime;
        }

        public String getScheduleTime(){
            return scheduleTime;
        }

        public void setCourcesId(String courcesId){
            this.courcesId = courcesId;
        }

        public String getCourcesId(){
            return courcesId;
        }

        public void setTeacherId(String teacherId){
            this.teacherId = teacherId;
        }

        public String getTeacherId(){
            return teacherId;
        }

        public void setLessonDuration(int lessonDuration){
            this.lessonDuration = lessonDuration;
        }

        public int getLessonDuration(){
            return lessonDuration;
        }

        public void setCourcesName(String courcesName){
            this.courcesName = courcesName;
        }

        public String getCourcesName(){
            return courcesName;
        }

        public void setTimezFinish(String timezFinish){
            this.timezFinish = timezFinish;
        }

        public String getTimezFinish(){
            return timezFinish;
        }
    }

    ////// Response Jadwal Ujian
    public class  JadwalUjian{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataUjian> data;

        public void setData(List<DataUjian> data) {
            this.data = data;
        }

        public List<DataUjian> getData() {
            return data;
        }
    }
    public class DataUjian{

        @SerializedName("exam_type")
        private String exam_type;

        @SerializedName("member_id")
        private String member_id;

        @SerializedName("exam_status")
        private String exam_status;

        @SerializedName("classroom_id")
        private String classroom_id;

        @SerializedName("exam_desc")
        private String exam_desc;

        @SerializedName("type_name")
        private String type_name;

        @SerializedName("datez")
        private String datez;

        @SerializedName("scyear_id")
        private String scyear_id;

        @SerializedName("cources_id")
        private String cources_id;

        @SerializedName("type_id")
        private String type_id;

        @SerializedName("class_stat")
        private String class_stat;

        @SerializedName("edulevel_id")
        private String edulevel_id;

        @SerializedName("exam_time_ok")
        private String exam_time_ok;

        @SerializedName("total_exam_file")
        private int total_exam_file;

        @SerializedName("exam_date_ok")
        private String exam_date_ok;

        @SerializedName("exam_time")
        private String exam_time;

        @SerializedName("score_value")
        private String score_value;

        @SerializedName("examid")
        private String examid;

        @SerializedName("exam_date")
        private String exam_date;

        @SerializedName("courcesreligion_id")
        private String courcesreligion_id;

        @SerializedName("edulevel_name")
        private String edulevel_name;

        @SerializedName("cources_name")
        private String cources_name;

        public String getEdulevel_name() {
            return edulevel_name;
        }

        public void setEdulevel_name(String edulevel_name) {
            this.edulevel_name = edulevel_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getExam_type() {
            return exam_type;
        }

        public void setExam_type(String exam_type) {
            this.exam_type = exam_type;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getExam_status() {
            return exam_status;
        }

        public void setExam_status(String exam_status) {
            this.exam_status = exam_status;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getExam_desc() {
            return exam_desc;
        }

        public void setExam_desc(String exam_desc) {
            this.exam_desc = exam_desc;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getScyear_id() {
            return scyear_id;
        }

        public void setScyear_id(String scyear_id) {
            this.scyear_id = scyear_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getClass_stat() {
            return class_stat;
        }

        public void setClass_stat(String class_stat) {
            this.class_stat = class_stat;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

        public String getExam_time_ok() {
            return exam_time_ok;
        }

        public void setExam_time_ok(String exam_time_ok) {
            this.exam_time_ok = exam_time_ok;
        }

        public int getTotal_exam_file() {
            return total_exam_file;
        }

        public void setTotal_exam_file(int total_exam_file) {
            this.total_exam_file = total_exam_file;
        }

        public String getExam_date_ok() {
            return exam_date_ok;
        }

        public void setExam_date_ok(String exam_date_ok) {
            this.exam_date_ok = exam_date_ok;
        }

        public String getExam_time() {
            return exam_time;
        }

        public void setExam_time(String exam_time) {
            this.exam_time = exam_time;
        }

        public String getScore_value() {
            return score_value;
        }

        public void setScore_value(String score_value) {
            this.score_value = score_value;
        }

        public String getExamid() {
            return examid;
        }

        public void setExamid(String examid) {
            this.examid = examid;
        }

        public String getExam_date() {
            return exam_date;
        }

        public void setExam_date(String exam_date) {
            this.exam_date = exam_date;
        }

        public String getCourcesreligion_id() {
            return courcesreligion_id;
        }

        public void setCourcesreligion_id(String courcesreligion_id) {
            this.courcesreligion_id = courcesreligion_id;
        }
    }

    ///// Response Check Semester
    public class CheckSemester{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public String data;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    ///// Response List Semester
    public class ListSemester{
        @SerializedName("code")
        public String code;

        @SerializedName("data")
        @Expose
        public List<DataSemester> data;

        @SerializedName("status")
        public int status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataSemester> getData() {
            return data;
        }

        public void setData(List<DataSemester> data) {
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public class DataSemester{

        @SerializedName("semesterid")
        public String semester_id;

        @SerializedName("zedulevel_id")
        public String zedulevel_id;

        @SerializedName("semester_publish")
        public String semester_publish;

        @SerializedName("semester_name")
        public String semester_name;

        @SerializedName("start_date")
        public String start_date;

        @SerializedName("end_date")
        public String end_date;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("lastupdate")
        public String lastupdate;

        @SerializedName("zedulevelid")
        public String zedulevelid;

        @SerializedName("edulevel_name")
        public String edulevel_name;

        @SerializedName("edulevel_id")
        public String edulevel_id;

        @SerializedName("scyear_id")
        public String scyear_id;

        public String getZedulevel_id() {
            return zedulevel_id;
        }

        public void setZedulevel_id(String zedulevel_id) {
            this.zedulevel_id = zedulevel_id;
        }

        public String getSemester_publish() {
            return semester_publish;
        }

        public void setSemester_publish(String semester_publish) {
            this.semester_publish = semester_publish;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getLastupdate() {
            return lastupdate;
        }

        public void setLastupdate(String lastupdate) {
            this.lastupdate = lastupdate;
        }

        public String getZedulevelid() {
            return zedulevelid;
        }

        public void setZedulevelid(String zedulevelid) {
            this.zedulevelid = zedulevelid;
        }

        public String getEdulevel_name() {
            return edulevel_name;
        }

        public void setEdulevel_name(String edulevel_name) {
            this.edulevel_name = edulevel_name;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

        public String getScyear_id() {
            return scyear_id;
        }

        public void setScyear_id(String scyear_id) {
            this.scyear_id = scyear_id;
        }

        public String getSemester_id() {
            return semester_id;
        }

        public void setSemester_id(String semester_id) {
            this.semester_id = semester_id;
        }

        public String getSemester_name() {
            return semester_name;
        }

        public void setSemester_name(String semester_name) {
            this.semester_name = semester_name;
        }
    }

    ///// Response List Mata Pelajaran
    public class ListMapel{
        @SerializedName("code")
        public String code;

        @SerializedName("data")
        @Expose
        public List<DataMapel> data;

        @SerializedName("status")
        public int status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataMapel> getData() {
            return data;
        }

        public void setData(List<DataMapel> data) {
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public class DataMapel{

        @SerializedName("courcesid")
        public String courcesid;

        @SerializedName("cources_code")
        public String cources_code;

        @SerializedName("cources_name")
        public String cources_name;

        @SerializedName("cources_publish")
        public String cources_publish;

        @SerializedName("cources_type")
        public String cources_type;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("lastupdate")
        public String lastupdate;

        @SerializedName("colour_id")
        public String colour_id;

        @SerializedName("cources_spec")
        public String cources_spec;

        @SerializedName("religion_type")
        public String religion_type;

        public String getCourcesid() {
            return courcesid;
        }

        public void setCourcesid(String courcesid) {
            this.courcesid = courcesid;
        }

        public String getCources_code() {
            return cources_code;
        }

        public void setCources_code(String cources_code) {
            this.cources_code = cources_code;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getCources_publish() {
            return cources_publish;
        }

        public void setCources_publish(String cources_publish) {
            this.cources_publish = cources_publish;
        }

        public String getCources_type() {
            return cources_type;
        }

        public void setCources_type(String cources_type) {
            this.cources_type = cources_type;
        }

        public String getDatez() {
            return datez;
        }

        public void setDatez(String datez) {
            this.datez = datez;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getLastupdate() {
            return lastupdate;
        }

        public void setLastupdate(String lastupdate) {
            this.lastupdate = lastupdate;
        }

        public String getColour_id() {
            return colour_id;
        }

        public void setColour_id(String colour_id) {
            this.colour_id = colour_id;
        }

        public String getCources_spec() {
            return cources_spec;
        }

        public void setCources_spec(String cources_spec) {
            this.cources_spec = cources_spec;
        }

        public String getReligion_type() {
            return religion_type;
        }

        public void setReligion_type(String religion_type) {
            this.religion_type = religion_type;
        }
    }


    ///// Response Tugas Anak
    public class TugasAnak{
        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<TugasItem> data;

        @SerializedName("status")
        public int status;

        public void setCode(String code){
            this.code = code;
        }

        public String getCode(){
            return code;
        }

        public void setData(List<TugasItem> data){
            this.data = data;
        }

        public List<TugasItem> getData(){
            return data;
        }

        public void setStatus(int status){
            this.status = status;
        }

        public int getStatus(){
            return status;
        }
    }
    public class TugasItem{


        @SerializedName("cources_name")
        private  String cources_name;

        @SerializedName("teacher_name")
        private  String teacher_name;


        @SerializedName("exam_type")
        private String examType;

        @SerializedName("member_id")
        private String memberId;

        @SerializedName("exam_status")
        private String examStatus;

        @SerializedName("classroom_id")
        private String classroomId;

        @SerializedName("exam_desc")
        private String examDesc;

        @SerializedName("datez")
        private String datez;

        @SerializedName("scyear_id")
        private String scyearId;

        @SerializedName("cources_id")
        private String courcesId;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("class_stat")
        private String classStat;

        @SerializedName("edulevel_id")
        private String edulevelId;

        @SerializedName("total_exam_file")
        private int totalExamFile;

        @SerializedName("exam_date_ok")
        private String examDateOk;

        @SerializedName("exam_time")
        private String examTime;

        @SerializedName("score_value")
        private String scoreValue;

        @SerializedName("examid")
        private String examid;

        @SerializedName("exam_date")
        private String examDate;

        @SerializedName("courcesreligion_id")
        private String courcesreligionId;

        @SerializedName("exam_type_name")
        private String examTypeName;

        public void setExamType(String examType){
            this.examType = examType;
        }

        public String getExamType(){
            return examType;
        }

        public void setMemberId(String memberId){
            this.memberId = memberId;
        }

        public String getMemberId(){
            return memberId;
        }

        public void setExamStatus(String examStatus){
            this.examStatus = examStatus;
        }

        public String getExamStatus(){
            return examStatus;
        }

        public void setClassroomId(String classroomId){
            this.classroomId = classroomId;
        }

        public String getClassroomId(){
            return classroomId;
        }

        public void setExamDesc(String examDesc){
            this.examDesc = examDesc;
        }

        public String getExamDesc(){
            return examDesc;
        }

        public void setDatez(String datez){
            this.datez = datez;
        }

        public String getDatez(){
            return datez;
        }

        public void setScyearId(String scyearId){
            this.scyearId = scyearId;
        }

        public String getScyearId(){
            return scyearId;
        }

        public void setCourcesId(String courcesId){
            this.courcesId = courcesId;
        }

        public String getCourcesId(){
            return courcesId;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setClassStat(String classStat){
            this.classStat = classStat;
        }

        public String getClassStat(){
            return classStat;
        }

        public void setEdulevelId(String edulevelId){
            this.edulevelId = edulevelId;
        }

        public String getEdulevelId(){
            return edulevelId;
        }

        public void setTotalExamFile(int totalExamFile){
            this.totalExamFile = totalExamFile;
        }

        public int getTotalExamFile(){
            return totalExamFile;
        }

        public void setExamDateOk(String examDateOk){
            this.examDateOk = examDateOk;
        }

        public String getExamDateOk(){
            return examDateOk;
        }

        public void setExamTime(String examTime){
            this.examTime = examTime;
        }

        public String getExamTime(){
            return examTime;
        }

        public void setScoreValue(String scoreValue){
            this.scoreValue = scoreValue;
        }

        public String getScoreValue(){
            return scoreValue;
        }

        public void setExamid(String examid){
            this.examid = examid;
        }

        public String getExamid(){
            return examid;
        }

        public void setExamDate(String examDate){
            this.examDate = examDate;
        }

        public String getExamDate(){
            return examDate;
        }

        public void setCourcesreligionId(String courcesreligionId){
            this.courcesreligionId = courcesreligionId;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getCourcesreligionId(){
            return courcesreligionId;
        }

        public void setExamTypeName(String examTypeName){
            this.examTypeName = examTypeName;
        }

        public String getExamTypeName(){
            return examTypeName;
        }
    }

}
