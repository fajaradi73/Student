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

        @SerializedName("lastlogin")
        public String last_login;

        @SerializedName("member_type")
        public String member_type;

        @SerializedName("lastupdate")
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

        @SerializedName("edulevel_id")
        public String edulevel_id;

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

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

    public class UploadSilabus{
        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;
        @SerializedName("data")
        public String data;

    }

    //// dapat jadwal pelajaran
    public class JadwalPelajaran{


        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataJadwal data;

        @SerializedName("status")
        public int status;

        public void setCode(String code){
            this.code = code;
        }

        public String getCode(){
            return code;
        }

        public DataJadwal getData() {
            return data;
        }

        public void setData(DataJadwal data) {
            this.data = data;
        }

        public void setStatus(int status){
            this.status = status;
        }

        public int getStatus(){
            return status;
        }
    }
    public class DataJadwal {
        @SerializedName("class_schedule")
        public List<JadwalData> class_schedule;

        public List<JadwalData> getClass_schedule() {
            return class_schedule;
        }

        public void setClass_schedule(List<JadwalData> class_schedule) {
            this.class_schedule = class_schedule;
        }

        public List<AgendaData> getClass_agenda() {
            return class_agenda;
        }

        public void setClass_agenda(List<AgendaData> class_agenda) {
            this.class_agenda = class_agenda;
        }

        @SerializedName("class_agenda")
        public List<AgendaData> class_agenda;
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
    public class AgendaData{
        @SerializedName("desc")
        private String desc;
        @SerializedName("type")
        private String type;
        @SerializedName("date")
        private String date;
        @SerializedName("content")
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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
        @SerializedName("cources_colour")
        private String cources_colour;

        public String getCources_colour() {
            return cources_colour;
        }

        public void setCources_colour(String cources_colour) {
            this.cources_colour = cources_colour;
        }

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

//    absen

    public class ListMurid{

        @SerializedName("status")
        public Integer status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataMurid> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataMurid> getData() {
            return data;
        }

        public void setData(List<DataMurid> data) {
            this.data = data;
        }



    }
    public class DataMurid{
        @SerializedName("memberid")
        public String member_id;

        @SerializedName("fullname")
        public String fullname;

        @SerializedName("NIS")
        public String NIS;

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

        public String getNIS(){return member_id;}

        public void setNIS (String NIS){this.NIS = NIS;}




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

    ///// Response Raport Anak
    public class Raport{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public RaportData data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public RaportData getData() {
            return data;
        }

        public void setData(RaportData data) {
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public class RaportData{

        @SerializedName("detail_score")
        public List<DetailScoreItem> detailScore;

        @SerializedName("classroom")
        public Classroom classroom;

        public List<DetailScoreItem> getDetailScore() {
            return detailScore;
        }

        public void setDetailScore(List<DetailScoreItem> detailScore) {
            this.detailScore = detailScore;
        }

        public Classroom getClassroom() {
            return classroom;
        }

        public void setClassroom(Classroom classroom) {
            this.classroom = classroom;
        }
    }
    public class DetailScoreItem{

        @SerializedName("member_id")
        private String memberId;

        @SerializedName("religion_type")
        private String religionType;

        @SerializedName("datez")
        private String datez;

        @SerializedName("colour_id")
        private String colourId;

        @SerializedName("scoring_percent")
        private int scoringPercent;

        @SerializedName("type_exam")
        private TypeExam typeExam;

        @SerializedName("cources_publish")
        private String courcesPublish;

        @SerializedName("cources_spec")
        private String courcesSpec;

        @SerializedName("class_average_score")
        private String classAverageScore;

        @SerializedName("cources_type")
        private String courcesType;

        @SerializedName("final_score")
        private String finalScore;

        @SerializedName("cources_code")
        private String courcesCode;

        @SerializedName("cources_name")
        private String courcesName;

        @SerializedName("cources_kkm")
        private String cources_kkm;

        public String getCources_kkm() {
            return cources_kkm;
        }

        public void setCources_kkm(String cources_kkm) {
            this.cources_kkm = cources_kkm;
        }

        @SerializedName("lastupdate")
        private String lastupdate;

        @SerializedName("courcesid")
        private String courcesid;

        public String getClass_average_edu() {
            return class_average_edu;
        }

        public void setClass_average_edu(String class_average_edu) {
            this.class_average_edu = class_average_edu;
        }

        @SerializedName("class_average_edu")
        private String class_average_edu;

        public void setMemberId(String memberId){
            this.memberId = memberId;
        }

        public String getMemberId(){
            return memberId;
        }

        public void setReligionType(String religionType){
            this.religionType = religionType;
        }

        public String getReligionType(){
            return religionType;
        }

        public void setDatez(String datez){
            this.datez = datez;
        }

        public String getDatez(){
            return datez;
        }

        public void setColourId(String colourId){
            this.colourId = colourId;
        }

        public String getColourId(){
            return colourId;
        }

        public void setScoringPercent(int scoringPercent){
            this.scoringPercent = scoringPercent;
        }

        public int getScoringPercent(){
            return scoringPercent;
        }

        public void setTypeExam(TypeExam typeExam){
            this.typeExam = typeExam;
        }

        public TypeExam getTypeExam(){
            return typeExam;
        }

        public void setCourcesPublish(String courcesPublish){
            this.courcesPublish = courcesPublish;
        }

        public String getCourcesPublish(){
            return courcesPublish;
        }

        public void setCourcesSpec(String courcesSpec){
            this.courcesSpec = courcesSpec;
        }

        public String getCourcesSpec(){
            return courcesSpec;
        }

        public void setClassAverageScore(String classAverageScore){
            this.classAverageScore = classAverageScore;
        }

        public String getClassAverageScore(){
            return classAverageScore;
        }

        public void setCourcesType(String courcesType){
            this.courcesType = courcesType;
        }

        public String getCourcesType(){
            return courcesType;
        }

        public void setFinalScore(String finalScore){
            this.finalScore = finalScore;
        }

        public String getFinalScore(){
            return finalScore;
        }

        public void setCourcesCode(String courcesCode){
            this.courcesCode = courcesCode;
        }

        public String getCourcesCode(){
            return courcesCode;
        }

        public void setCourcesName(String courcesName){
            this.courcesName = courcesName;
        }

        public String getCourcesName(){
            return courcesName;
        }

        public void setLastupdate(String lastupdate){
            this.lastupdate = lastupdate;
        }

        public String getLastupdate(){
            return lastupdate;
        }

        public void setCourcesid(String courcesid){
            this.courcesid = courcesid;
        }

        public String getCourcesid(){
            return courcesid;
        }
    }
    public class Classroom{
        @SerializedName("classroom_desc")
        private String classroomDesc;

        @SerializedName("classroom_name")
        private String classroomName;

        @SerializedName("scyear_id")
        private String scyearId;

        @SerializedName("promote_ranking")
        private String promoteRanking;

        @SerializedName("classroom_code")
        private String classroomCode;

        @SerializedName("promote_text")
        private String promoteText;

        @SerializedName("promote_status")
        private String promoteStatus;

        @SerializedName("classroomid")
        private String classroomid;

        @SerializedName("description_text")
        private String descriptionText;

        @SerializedName("edulevel_id")
        private String edulevelId;

        public void setClassroomDesc(String classroomDesc){
            this.classroomDesc = classroomDesc;
        }

        public String getClassroomDesc(){
            return classroomDesc;
        }

        public void setClassroomName(String classroomName){
            this.classroomName = classroomName;
        }

        public String getClassroomName(){
            return classroomName;
        }

        public void setScyearId(String scyearId){
            this.scyearId = scyearId;
        }

        public String getScyearId(){
            return scyearId;
        }

        public void setPromoteRanking(String promoteRanking){
            this.promoteRanking = promoteRanking;
        }

        public String getPromoteRanking(){
            return promoteRanking;
        }

        public void setClassroomCode(String classroomCode){
            this.classroomCode = classroomCode;
        }

        public String getClassroomCode(){
            return classroomCode;
        }

        public void setPromoteText(String promoteText){
            this.promoteText = promoteText;
        }

        public String getPromoteText(){
            return promoteText;
        }

        public void setPromoteStatus(String promoteStatus){
            this.promoteStatus = promoteStatus;
        }

        public String getPromoteStatus(){
            return promoteStatus;
        }

        public void setClassroomid(String classroomid){
            this.classroomid = classroomid;
        }

        public String getClassroomid(){
            return classroomid;
        }

        public void setDescriptionText(String descriptionText){
            this.descriptionText = descriptionText;
        }

        public String getDescriptionText(){
            return descriptionText;
        }

        public void setEdulevelId(String edulevelId){
            this.edulevelId = edulevelId;
        }

        public String getEdulevelId(){
            return edulevelId;
        }
    }
    public class TypeExam{

        @SerializedName("1")
        private UjianSekolah ujianSekolah;

        @SerializedName("2")
        private UjianNegara ujianNegara;

        @SerializedName("3")
        private UlanganHarian ulanganHarian;

        @SerializedName("4")
        private LatihanTeori latihanTeori;

        @SerializedName("5")
        private Praktikum praktikum;

        @SerializedName("6")
        private Ekstrakulikuler ekstrakulikuler;

        public UjianSekolah getUjianSekolah() {
            return ujianSekolah;
        }

        public void setUjianSekolah(UjianSekolah ujianSekolah) {
            this.ujianSekolah = ujianSekolah;
        }

        public UjianNegara getUjianNegara() {
            return ujianNegara;
        }

        public void setUjianNegara(UjianNegara ujianNegara) {
            this.ujianNegara = ujianNegara;
        }

        public UlanganHarian getUlanganHarian() {
            return ulanganHarian;
        }

        public void setUlanganHarian(UlanganHarian ulanganHarian) {
            this.ulanganHarian = ulanganHarian;
        }

        public LatihanTeori getLatihanTeori() {
            return latihanTeori;
        }

        public void setLatihanTeori(LatihanTeori latihanTeori) {
            this.latihanTeori = latihanTeori;
        }

        public Praktikum getPraktikum() {
            return praktikum;
        }

        public void setPraktikum(Praktikum praktikum) {
            this.praktikum = praktikum;
        }

        public Ekstrakulikuler getEkstrakulikuler() {
            return ekstrakulikuler;
        }

        public void setEkstrakulikuler(Ekstrakulikuler ekstrakulikuler) {
            this.ekstrakulikuler = ekstrakulikuler;
        }
    }
    public class UjianSekolah{
        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    public class UjianNegara{

        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    public class UlanganHarian{
        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    public class LatihanTeori{
        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    public class Praktikum{
        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    public class Ekstrakulikuler{
        @SerializedName("type_name")
        private String typeName;

        @SerializedName("type_id")
        private String typeId;

        @SerializedName("score_status")
        private int scoreStatus;

        @SerializedName("score_exam")
        private double scoreExam;

        public void setTypeName(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return typeName;
        }

        public void setTypeId(String typeId){
            this.typeId = typeId;
        }

        public String getTypeId(){
            return typeId;
        }

        public void setScoreStatus(int scoreStatus){
            this.scoreStatus = scoreStatus;
        }

        public int getScoreStatus(){
            return scoreStatus;
        }

        public void setScoreExam(double scoreExam){
            this.scoreExam = scoreExam;
        }

        public double getScoreExam(){
            return scoreExam;
        }
    }
    ////--------------------------------///

    ///// Response Classroom Detail
    public class ClassroomDetail{

        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataClassroom data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public DataClassroom getData() {
            return data;
        }

        public void setData(DataClassroom data) {
            this.data = data;
        }
    }
    public class DataClassroom{
        @SerializedName("classroom_name")
        public String classroom_name;

        @SerializedName("teacher_id")
        public String teacher_id;

        @SerializedName("edulevel_id")
        public String edulevel_id;

        @SerializedName("scyear_id")
        public String scyear_id;

        @SerializedName("scyear_name")
        public String scyear_name;

        @SerializedName("homeroom_teacher")
        public String homeroom_teacher;

        @SerializedName("edulevel_name")
        public String edulevel_name;

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
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

        public String getScyear_name() {
            return scyear_name;
        }

        public void setScyear_name(String scyear_name) {
            this.scyear_name = scyear_name;
        }

        public String getHomeroom_teacher() {
            return homeroom_teacher;
        }

        public void setHomeroom_teacher(String homeroom_teacher) {
            this.homeroom_teacher = homeroom_teacher;
        }

        public String getEdulevel_name() {
            return edulevel_name;
        }

        public void setEdulevel_name(String edulevel_name) {
            this.edulevel_name = edulevel_name;
        }
    }

    //// Response Pesan Anak
    public class PesanAnak{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataPesan> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataPesan> getData() {
            return data;
        }

        public void setData(List<DataPesan> data) {
            this.data = data;
        }
    }
    public class DataPesan{
        @SerializedName("messageid")
        public String messageid;

        @SerializedName("user_id_from")
        public String user_id_from;

        @SerializedName("user_id_to")
        public String user_id_to;

        @SerializedName("message_cont")
        public String message_cont;

        @SerializedName("message_date")
        public String message_date;

        @SerializedName("message_status")
        public String message_status;

        @SerializedName("message_type")
        public String message_type;

        @SerializedName("classroom_id")
        public String classroom_id;

        @SerializedName("cources_id")
        public String cources_id;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("reply_message_id")
        public String reply_message_id;

        @SerializedName("read_status")
        public String read_status;

        @SerializedName("read_date")
        public String read_date;

        @SerializedName("read_message_id")
        public String read_message_id;

        @SerializedName("school_id")
        public String school_id;

        @SerializedName("parent_status")
        public String parent_status;

        @SerializedName("teacher_id")
        public String teacher_id;

        @SerializedName("datez_ok")
        public String datez_ok;

        @SerializedName("classroom_name")
        public String classroom_name;

        @SerializedName("cources_name")
        public String cources_name;

        @SerializedName("sender_name")
        public String sender_name;

        @SerializedName("member_type_text")
        public String member_type_text;

        @SerializedName("parent_message_id")
        public String parent_message_id;

        @SerializedName("reply_status")
        public int reply_status;

        @SerializedName("message_title")
        public String message_title;

        public String getMessage_title() {
            return message_title;
        }

        public void setMessage_title(String message_title) {
            this.message_title = message_title;
        }

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getUser_id_from() {
            return user_id_from;
        }

        public void setUser_id_from(String user_id_from) {
            this.user_id_from = user_id_from;
        }

        public String getUser_id_to() {
            return user_id_to;
        }

        public void setUser_id_to(String user_id_to) {
            this.user_id_to = user_id_to;
        }

        public String getMessage_cont() {
            return message_cont;
        }

        public void setMessage_cont(String message_cont) {
            this.message_cont = message_cont;
        }

        public String getMessage_date() {
            return message_date;
        }

        public void setMessage_date(String message_date) {
            this.message_date = message_date;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
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

        public String getReply_message_id() {
            return reply_message_id;
        }

        public void setReply_message_id(String reply_message_id) {
            this.reply_message_id = reply_message_id;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getRead_date() {
            return read_date;
        }

        public void setRead_date(String read_date) {
            this.read_date = read_date;
        }

        public String getRead_message_id() {
            return read_message_id;
        }

        public void setRead_message_id(String read_message_id) {
            this.read_message_id = read_message_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getParent_status() {
            return parent_status;
        }

        public void setParent_status(String parent_status) {
            this.parent_status = parent_status;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getDatez_ok() {
            return datez_ok;
        }

        public void setDatez_ok(String datez_ok) {
            this.datez_ok = datez_ok;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getMember_type_text() {
            return member_type_text;
        }

        public void setMember_type_text(String member_type_text) {
            this.member_type_text = member_type_text;
        }

        public String getParent_message_id() {
            return parent_message_id;
        }

        public void setParent_message_id(String parent_message_id) {
            this.parent_message_id = parent_message_id;
        }

        public int getReply_status() {
            return reply_status;
        }

        public void setReply_status(int reply_status) {
            this.reply_status = reply_status;
        }
    }

    /// pesan detail
    public class PesanDetail{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataPesanDetail data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public DataPesanDetail getData() {
            return data;
        }

        public void setData(DataPesanDetail data) {
            this.data = data;
        }
    }
    public class DataPesanDetail{
        @SerializedName("message")
        public DataMessage dataMessage;

        @SerializedName("reply_messages")
        public List<ReplyMessage> replyMessages;

        public DataMessage getDataMessage() {
            return dataMessage;
        }

        public void setDataMessage(DataMessage dataMessage) {
            this.dataMessage = dataMessage;
        }

        public List<ReplyMessage> getReplyMessages() {
            return replyMessages;
        }

        public void setReplyMessages(List<ReplyMessage> replyMessages) {
            this.replyMessages = replyMessages;
        }
    }
    public class DataMessage{
        @SerializedName("messageid")
        public String messageid;

        @SerializedName("user_id_from")
        public String user_id_from;

        @SerializedName("user_id_to")
        public String user_id_to;

        @SerializedName("message_cont")
        public String message_cont;

        @SerializedName("message_date")
        public String message_date;

        @SerializedName("message_status")
        public String message_status;

        @SerializedName("message_type")
        public String message_type;

        @SerializedName("classroom_id")
        public String classroom_id;

        @SerializedName("cources_id")
        public String cources_id;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("reply_message_id")
        public String reply_message_id;

        @SerializedName("read_status")
        public String read_status;

        @SerializedName("read_date")
        public String read_date;

        @SerializedName("read_message_id")
        public String read_message_id;

        @SerializedName("school_id")
        public String school_id;

        @SerializedName("parent_status")
        public String parent_status;

        @SerializedName("teacher_id")
        public String teacher_id;

        @SerializedName("datez_ok")
        public String datez_ok;

        @SerializedName("school_name")
        public String school_name;

        @SerializedName("created_by")
        public String created_by;

        @SerializedName("student_name")
        public String student_name;

        @SerializedName("classroom_name")
        public String classroom_name;

        @SerializedName("cources_name")
        public String cources_name;

        @SerializedName("sender_name")
        public String sender_name;

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getMessage_title() {
            return message_title;
        }

        public void setMessage_title(String message_title) {
            this.message_title = message_title;
        }

        @SerializedName("message_title")
        public String message_title;

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getUser_id_from() {
            return user_id_from;
        }

        public void setUser_id_from(String user_id_from) {
            this.user_id_from = user_id_from;
        }

        public String getUser_id_to() {
            return user_id_to;
        }

        public void setUser_id_to(String user_id_to) {
            this.user_id_to = user_id_to;
        }

        public String getMessage_cont() {
            return message_cont;
        }

        public void setMessage_cont(String message_cont) {
            this.message_cont = message_cont;
        }

        public String getMessage_date() {
            return message_date;
        }

        public void setMessage_date(String message_date) {
            this.message_date = message_date;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
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

        public String getReply_message_id() {
            return reply_message_id;
        }

        public void setReply_message_id(String reply_message_id) {
            this.reply_message_id = reply_message_id;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getRead_date() {
            return read_date;
        }

        public void setRead_date(String read_date) {
            this.read_date = read_date;
        }

        public String getRead_message_id() {
            return read_message_id;
        }

        public void setRead_message_id(String read_message_id) {
            this.read_message_id = read_message_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getParent_status() {
            return parent_status;
        }

        public void setParent_status(String parent_status) {
            this.parent_status = parent_status;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getDatez_ok() {
            return datez_ok;
        }

        public void setDatez_ok(String datez_ok) {
            this.datez_ok = datez_ok;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }
    }
    public class ReplyMessage{
        @SerializedName("messageid")
        public String messageid;

        @SerializedName("user_id_from")
        public String user_id_from;

        @SerializedName("user_id_to")
        public String user_id_to;

        @SerializedName("message_cont")
        public String message_cont;

        @SerializedName("message_date")
        public String message_date;

        @SerializedName("message_status")
        public String message_status;

        @SerializedName("message_type")
        public String message_type;

        @SerializedName("classroom_id")
        public String classroom_id;

        @SerializedName("cources_id")
        public String cources_id;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("reply_message_id")
        public String reply_message_id;

        @SerializedName("read_status")
        public String read_status;

        @SerializedName("read_date")
        public String read_date;

        @SerializedName("read_message_id")
        public String read_message_id;

        @SerializedName("school_id")
        public String school_id;

        @SerializedName("parent_status")
        public String parent_status;

        @SerializedName("teacher_id")
        public String teacher_id;

        @SerializedName("datez_ok")
        public String datez_ok;

        @SerializedName("school_name")
        public String school_name;

        @SerializedName("created_by")
        public String created_by;

        @SerializedName("student_name")
        public String student_name;

        @SerializedName("classroom_name")
        public String classroom_name;

        @SerializedName("cources_name")
        public String cources_name;

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getUser_id_from() {
            return user_id_from;
        }

        public void setUser_id_from(String user_id_from) {
            this.user_id_from = user_id_from;
        }

        public String getUser_id_to() {
            return user_id_to;
        }

        public void setUser_id_to(String user_id_to) {
            this.user_id_to = user_id_to;
        }

        public String getMessage_cont() {
            return message_cont;
        }

        public void setMessage_cont(String message_cont) {
            this.message_cont = message_cont;
        }

        public String getMessage_date() {
            return message_date;
        }

        public void setMessage_date(String message_date) {
            this.message_date = message_date;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
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

        public String getReply_message_id() {
            return reply_message_id;
        }

        public void setReply_message_id(String reply_message_id) {
            this.reply_message_id = reply_message_id;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getRead_date() {
            return read_date;
        }

        public void setRead_date(String read_date) {
            this.read_date = read_date;
        }

        public String getRead_message_id() {
            return read_message_id;
        }

        public void setRead_message_id(String read_message_id) {
            this.read_message_id = read_message_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getParent_status() {
            return parent_status;
        }

        public void setParent_status(String parent_status) {
            this.parent_status = parent_status;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getDatez_ok() {
            return datez_ok;
        }

        public void setDatez_ok(String datez_ok) {
            this.datez_ok = datez_ok;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }
    }
    //// Response Absen Anak
    public class AbsenSiswa{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataJam> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataJam> getData() {
            return data;
        }

        public void setData(List<DataJam> data) {
            this.data = data;
        }
    }
    public class DataJam{
        @SerializedName("schedule_time")
        String schedule_time;

        @SerializedName("timez_start")
        public String timez_start;

        @SerializedName("timez_ok")
        public String timez_ok;

        @SerializedName("timez_finish")
        public String timez_finish;

        @SerializedName("lesson_duration")
        public String lesson_duration;

        @SerializedName("total_attendance")
        public String total_attendance;

        @SerializedName("total_leave_sick")
        public String total_leave_sick;

        @SerializedName("days")
        public List<DataHari> days;
        public String getTimez_start() {
            return timez_start;
        }

        public void setTimez_start(String timez_start) {
            this.timez_start = timez_start;
        }

        public String getSchedule_time() {
            return schedule_time;
        }

        public void setSchedule_time(String schedule_time) {
            this.schedule_time = schedule_time;
        }

        public String getTimez_finish() {
            return timez_finish;
        }

        public void setTimez_finish(String timez_finish) {
            this.timez_finish = timez_finish;
        }

        public String getLesson_duration() {
            return lesson_duration;
        }

        public void setLesson_duration(String lesson_duration) {
            this.lesson_duration = lesson_duration;
        }

        public String getTotal_attendance() {
            return total_attendance;
        }

        public void setTotal_attendance(String total_attendance) {
            this.total_attendance = total_attendance;
        }

        public String getTotal_leave_sick() {
            return total_leave_sick;
        }

        public void setTotal_leave_sick(String total_leave_sick) {
            this.total_leave_sick = total_leave_sick;
        }

        public List<DataHari> getDays() {
            return days;
        }

        public void setDays(List<DataHari> days) {
            this.days = days;
        }
    }
    public class DataHari{
        @SerializedName("day_id")
        public String day_id;

        @SerializedName("day_type")
        public String day_type;

        @SerializedName("absent_status")
        public String absen_status;

        public String getDay_id() {
            return day_id;
        }

        public void setDay_id(String day_id) {
            this.day_id = day_id;
        }

        public String getDay_type() {
            return day_type;
        }

        public void setDay_type(String day_type) {
            this.day_type = day_type;
        }

        public String getAbsen_status() {
            return absen_status;
        }

        public void setAbsen_status(String absen_status) {
            this.absen_status = absen_status;
        }
    }

    ///// Response Calendar Anak
    public class ClassCalendar{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataCalendar> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataCalendar> getData() {
            return data;
        }

        public void setData(List<DataCalendar> data) {
            this.data = data;
        }
    }
    public class DataCalendar{

        @SerializedName("calendar_id")
        public int calendar_id;

        @SerializedName("calendar_title")
        public String calendar_title;

        @SerializedName("calendar_desc")
        public String calendar_desc;

        @SerializedName("calendar_date")
        public String calendar_date;

        @SerializedName("calendar_time")
        public String calendar_time;

        @SerializedName("calendar_type")
        public String calendar_type;


        @SerializedName("calendar_colour")
        public String calendar_colour;

        public String getCalendar_colour() {
            return calendar_colour;
        }

        public void setCalendar_colour(String calendar_colour) {
            this.calendar_colour = calendar_colour;
        }

        public int getCalendar_id() {
            return calendar_id;
        }

        public void setCalendar_id(int calendar_id) {
            this.calendar_id = calendar_id;
        }

        public String getCalendar_title() {
            return calendar_title;
        }

        public void setCalendar_title(String calendar_title) {
            this.calendar_title = calendar_title;
        }

        public String getCalendar_desc() {
            return calendar_desc;
        }

        public void setCalendar_desc(String calendar_desc) {
            this.calendar_desc = calendar_desc;
        }

        public String getCalendar_date() {
            return calendar_date;
        }

        public void setCalendar_date(String calendar_date) {
            this.calendar_date = calendar_date;
        }

        public String getCalendar_time() {
            return calendar_time;
        }

        public void setCalendar_time(String calendar_time) {
            this.calendar_time = calendar_time;
        }

        public String getCalendar_type() {
            return calendar_type;
        }

        public void setCalendar_type(String calendar_type) {
            this.calendar_type = calendar_type;
        }
    }

    ///// Response Calendar Detail
    public class CalendarDetail{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataCalendarDetail data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public DataCalendarDetail getData() {
            return data;
        }

        public void setData(DataCalendarDetail data) {
            this.data = data;
        }
    }
    public class DataCalendarDetail{
        @SerializedName("calendarid")
        public String calendarid;

        @SerializedName("calendar_title")
        public String calendar_title;

        @SerializedName("calendar_desc")
        public String calendar_desc;

        @SerializedName("calendar_date")
        public String calendar_date;

        @SerializedName("calendar_time")
        public String calendar_time;

        @SerializedName("calendar_bg")
        public String calendar_bg;

        @SerializedName("calendar_status")
        public String calendar_status;

        @SerializedName("calendar_type")
        public String calendar_type;

        @SerializedName("classroom_id")
        public String classroom_id;

        @SerializedName("datez")
        public String datez;

        @SerializedName("member_id")
        public String member_id;

        @SerializedName("lastupdate")
        public String lastupdate;

        @SerializedName("event_type")
        public String event_type;

        @SerializedName("edulevel_id")
        public String edulevel_id;

        @SerializedName("scyear_id")
        public String scyear_id;

        @SerializedName("courcesreligion_id")
        public String courcesreligion_id;

        @SerializedName("reminder_status")
        public String reminder_status;

        @SerializedName("reminder_duration")
        public String reminder_duration;

        @SerializedName("calendar_date_ok")
        public String calendar_date_ok;

        @SerializedName("timez")
        public String timez;

        @SerializedName("created_by")
        public String created_by;

        @SerializedName("edulevel_name")
        public String edulevel_name;

        @SerializedName("classroom_name")
        public String classroom_name;

        public String getCalendarid() {
            return calendarid;
        }

        public void setCalendarid(String calendarid) {
            this.calendarid = calendarid;
        }

        public String getCalendar_title() {
            return calendar_title;
        }

        public void setCalendar_title(String calendar_title) {
            this.calendar_title = calendar_title;
        }

        public String getCalendar_desc() {
            return calendar_desc;
        }

        public void setCalendar_desc(String calendar_desc) {
            this.calendar_desc = calendar_desc;
        }

        public String getCalendar_date() {
            return calendar_date;
        }

        public void setCalendar_date(String calendar_date) {
            this.calendar_date = calendar_date;
        }

        public String getCalendar_time() {
            return calendar_time;
        }

        public void setCalendar_time(String calendar_time) {
            this.calendar_time = calendar_time;
        }

        public String getCalendar_bg() {
            return calendar_bg;
        }

        public void setCalendar_bg(String calendar_bg) {
            this.calendar_bg = calendar_bg;
        }

        public String getCalendar_status() {
            return calendar_status;
        }

        public void setCalendar_status(String calendar_status) {
            this.calendar_status = calendar_status;
        }

        public String getCalendar_type() {
            return calendar_type;
        }

        public void setCalendar_type(String calendar_type) {
            this.calendar_type = calendar_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
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

        public String getEvent_type() {
            return event_type;
        }

        public void setEvent_type(String event_type) {
            this.event_type = event_type;
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

        public String getCourcesreligion_id() {
            return courcesreligion_id;
        }

        public void setCourcesreligion_id(String courcesreligion_id) {
            this.courcesreligion_id = courcesreligion_id;
        }

        public String getReminder_status() {
            return reminder_status;
        }

        public void setReminder_status(String reminder_status) {
            this.reminder_status = reminder_status;
        }

        public String getReminder_duration() {
            return reminder_duration;
        }

        public void setReminder_duration(String reminder_duration) {
            this.reminder_duration = reminder_duration;
        }

        public String getCalendar_date_ok() {
            return calendar_date_ok;
        }

        public void setCalendar_date_ok(String calendar_date_ok) {
            this.calendar_date_ok = calendar_date_ok;
        }

        public String getTimez() {
            return timez;
        }

        public void setTimez(String timez) {
            this.timez = timez;
        }

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        public String getEdulevel_name() {
            return edulevel_name;
        }

        public void setEdulevel_name(String edulevel_name) {
            this.edulevel_name = edulevel_name;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }
    }

    //// Response Jadwal Guru
    public class JadwalGuru{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataJadwalGuru data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public DataJadwalGuru getData() {
            return data;
        }

        public void setData(DataJadwalGuru data) {
            this.data = data;
        }
    }
    public class DataJadwalGuru{

        @SerializedName("schedule_class")
        List<JadwalDataGuru> schedule_class;

        @SerializedName("substitute_class")
        List<JadwalPenggantiGuru> substitute_class;

        @SerializedName("schedule_religion_class")
        List<JadwalAgamaKelas> schedule_religion_class;
        public List<JadwalDataGuru> getSchedule_class() {
            return schedule_class;
        }

        public void setSchedule_class(List<JadwalDataGuru> schedule_class) {
            this.schedule_class = schedule_class;
        }

        public List<JadwalPenggantiGuru> getSubstitute_class() {
            return substitute_class;
        }

        public void setSubstitute_class(List<JadwalPenggantiGuru> substitute_class) {
            this.substitute_class = substitute_class;
        }

        public List<JadwalAgamaKelas> getSchedule_religion_class() {
            return schedule_religion_class;
        }

        public void setSchedule_religion_class(List<JadwalAgamaKelas> schedule_religion_class) {
            this.schedule_religion_class = schedule_religion_class;
        }


    }
    public class JadwalDataGuru {

        @SerializedName("dayid")
        String dayid;
        @SerializedName("day_name")
        String day_name;
        @SerializedName("day_status")
        String day_status;
        @SerializedName("day_type")
        String day_type;
        @SerializedName("schedule_class")
        List<JadwalKelasGuru> schedule_class;

        public String getDayid() {
            return dayid;
        }

        public void setDayid(String dayid) {
            this.dayid = dayid;
        }

        public String getDay_name() {
            return day_name;
        }

        public void setDay_name(String day_name) {
            this.day_name = day_name;
        }

        public String getDay_status() {
            return day_status;
        }

        public void setDay_status(String day_status) {
            this.day_status = day_status;
        }

        public String getDay_type() {
            return day_type;
        }

        public void setDay_type(String day_type) {
            this.day_type = day_type;
        }

        public List<JadwalKelasGuru> getSchedule_class() {
            return schedule_class;
        }

        public void setSchedule_class(List<JadwalKelasGuru> schedule_class) {
            this.schedule_class = schedule_class;
        }
    }
    public class JadwalPenggantiGuru {

    }
    public class JadwalAgamaKelas {
    }
    public class JadwalKelasGuru {
        @SerializedName("schedule_time")
        String schedule_time;
        @SerializedName("teacher_id")
        String teacher_id;
        @SerializedName("cources_id")
        String cources_id;
        @SerializedName("classroom_id")
        String classroom_id;
        @SerializedName("timez_start")
        String timez_start;
        @SerializedName("teacher_name")
        String teacher_name;
        @SerializedName("cources_name")
        String cources_name;
        @SerializedName("cources_colour")
        String cources_colour;
        @SerializedName("classroom_name")
        String classroom_name;
        @SerializedName("lesson_duration")
        String lesson_duration;
        @SerializedName("timez_finish")
        String timez_finish;

        public String getSchedule_time() {
            return schedule_time;
        }

        public void setSchedule_time(String schedule_time) {
            this.schedule_time = schedule_time;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getTimez_start() {
            return timez_start;
        }

        public void setTimez_start(String timez_start) {
            this.timez_start = timez_start;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getCources_colour() {
            return cources_colour;
        }

        public void setCources_colour(String cources_colour) {
            this.cources_colour = cources_colour;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getLesson_duration() {
            return lesson_duration;
        }

        public void setLesson_duration(String lesson_duration) {
            this.lesson_duration = lesson_duration;
        }

        public String getTimez_finish() {
            return timez_finish;
        }

        public void setTimez_finish(String timez_finish) {
            this.timez_finish = timez_finish;
        }
    }

    //// Response List Edulevel
    public class ListEdulevel{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataEdulevel> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataEdulevel> getData() {
            return data;
        }

        public void setData(List<DataEdulevel> data) {
            this.data = data;
        }
    }
    public class DataEdulevel{
        @SerializedName("edulevelid")
        public String edulevelid;
        @SerializedName("edulevel_name")
        public String edulevel_name;
        @SerializedName("edulevel_name2")
        public String edulevel_name2;
        @SerializedName("edulevel_status")
        public String edulevel_status;
        @SerializedName("edulevel_pos")
        public String edulevel_pos;
        @SerializedName("edulevel_type")
        public String edulevel_type;

        public String getEdulevelid() {
            return edulevelid;
        }

        public void setEdulevelid(String edulevelid) {
            this.edulevelid = edulevelid;
        }

        public String getEdulevel_name() {
            return edulevel_name;
        }

        public void setEdulevel_name(String edulevel_name) {
            this.edulevel_name = edulevel_name;
        }

    }

    //// Response List Kelas
    public class ListKelas{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataKelas> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataKelas> getData() {
            return data;
        }

        public void setData(List<DataKelas> data) {
            this.data = data;
        }
    }
    public class DataKelas{
        @SerializedName("classroomid")
        public String classroomid;
        @SerializedName("classroom_code")
        public String classroom_code;
        @SerializedName("classroom_name")
        public String classroom_name;
        @SerializedName("classroom_desc")
        public String classroom_desc;
        @SerializedName("classroom_publish")
        public String classroom_publish;
        @SerializedName("classroom_type")
        public String classroom_type;
        @SerializedName("classroom_count")
        public String classroom_count;
        @SerializedName("edulevel_id")
        public String edulevel_id;
        @SerializedName("datez")
        public String datez;
        @SerializedName("member_id")
        public String member_id;
        @SerializedName("lastupdate")
        public String lastupdate;
        @SerializedName("teacher_id")
        public String teacher_id;
        @SerializedName("scyear_id")
        public String scyear_id;
        @SerializedName("leader_mbr")
        public String leader_mbr;
        @SerializedName("viceleader_mbr")
        public String viceleader_mbr;

        public String getClassroomid() {
            return classroomid;
        }

        public void setClassroomid(String classroomid) {
            this.classroomid = classroomid;
        }

        public String getClassroom_code() {
            return classroom_code;
        }

        public void setClassroom_code(String classroom_code) {
            this.classroom_code = classroom_code;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getClassroom_desc() {
            return classroom_desc;
        }

        public void setClassroom_desc(String classroom_desc) {
            this.classroom_desc = classroom_desc;
        }

        public String getClassroom_publish() {
            return classroom_publish;
        }

        public void setClassroom_publish(String classroom_publish) {
            this.classroom_publish = classroom_publish;
        }

        public String getClassroom_type() {
            return classroom_type;
        }

        public void setClassroom_type(String classroom_type) {
            this.classroom_type = classroom_type;
        }

        public String getClassroom_count() {
            return classroom_count;
        }

        public void setClassroom_count(String classroom_count) {
            this.classroom_count = classroom_count;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
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

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getScyear_id() {
            return scyear_id;
        }

        public void setScyear_id(String scyear_id) {
            this.scyear_id = scyear_id;
        }

        public String getLeader_mbr() {
            return leader_mbr;
        }

        public void setLeader_mbr(String leader_mbr) {
            this.leader_mbr = leader_mbr;
        }

        public String getViceleader_mbr() {
            return viceleader_mbr;
        }

        public void setViceleader_mbr(String viceleader_mbr) {
            this.viceleader_mbr = viceleader_mbr;
        }
    }

    //// Response List Mapel Edulevel
    public class ListMapelEdu{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataMapelEdu> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataMapelEdu> getData() {
            return data;
        }

        public void setData(List<DataMapelEdu> data) {
            this.data = data;
        }
    }
    public class DataMapelEdu {
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

    //// Response List Silabus
    public class ListSilabus{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataSilabus> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataSilabus> getData() {
            return data;
        }

        public void setData(List<DataSilabus> data) {
            this.data = data;
        }
    }
    public class DataSilabus {
        @SerializedName("silabusid")
        public String silabusid;
        @SerializedName("edulevel_id")
        public String edulevel_id;
        @SerializedName("cources_id")
        public String cources_id;
        @SerializedName("silabus_title")
        public String silabus_title;
        @SerializedName("silabus_file")
        public String silabus_file;
        @SerializedName("silabus_status")
        public String silabus_status;
        @SerializedName("member_id")
        public String member_id;
        @SerializedName("datez")
        public String datez;
        @SerializedName("scyear_id")
        public String scyear_id;
        @SerializedName("datez_ok")
        public String datez_ok;
        @SerializedName("edulevel_name")
        public String edulevel_name;
        @SerializedName("cources_name")
        public String cources_name;
        @SerializedName("teacher_name")
        public String teacher_name;
        @SerializedName("del_stat")
        public String del_stat;

        public String getSilabusid() {
            return silabusid;
        }

        public void setSilabusid(String silabusid) {
            this.silabusid = silabusid;
        }

        public String getEdulevel_id() {
            return edulevel_id;
        }

        public void setEdulevel_id(String edulevel_id) {
            this.edulevel_id = edulevel_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
        }

        public String getSilabus_title() {
            return silabus_title;
        }

        public void setSilabus_title(String silabus_title) {
            this.silabus_title = silabus_title;
        }

        public String getSilabus_file() {
            return silabus_file;
        }

        public void setSilabus_file(String silabus_file) {
            this.silabus_file = silabus_file;
        }

        public String getSilabus_status() {
            return silabus_status;
        }

        public void setSilabus_status(String silabus_status) {
            this.silabus_status = silabus_status;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
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

        public String getDatez_ok() {
            return datez_ok;
        }

        public void setDatez_ok(String datez_ok) {
            this.datez_ok = datez_ok;
        }

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

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getDel_stat() {
            return del_stat;
        }

        public void setDel_stat(String del_stat) {
            this.del_stat = del_stat;
        }
    }

    //// Response List Dashboard
    public class Dashboard{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataDashboard data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public DataDashboard getData() {
            return data;
        }

        public void setData(DataDashboard data) {
            this.data = data;
        }
    }
    public class DashboardCalendar{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public DataCalendar data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public DataCalendar getData() {
            return data;
        }

        public void setData(DataCalendar data) {
            this.data = data;
        }
    }
    public class DataDashboard {
        @SerializedName("calendars")
        public List<DataCalendar> calendars;

        @SerializedName("schedule_class")
        public List<DataJadwalGuru> schedule_class;

        @SerializedName("substitute_class")
        public List<DataSilabus> substitute_class;

        @SerializedName("test_not_score")
        public List<DataSilabus> test_not_score;

        @SerializedName("cources_score_not_completed")
        public List<DataSilabus> cources_score_not_completed;

        public List<DataCalendar> getCalendars() {
            return calendars;
        }

        public void setCalendars(List<DataCalendar> calendars) {
            this.calendars = calendars;
        }

        public List<DataJadwalGuru> getSchedule_class() {
            return schedule_class;
        }

        public void setSchedule_class(List<DataJadwalGuru> schedule_class) {
            this.schedule_class = schedule_class;
        }

        public List<DataSilabus> getSubstitute_class() {
            return substitute_class;
        }

        public void setSubstitute_class(List<DataSilabus> substitute_class) {
            this.substitute_class = substitute_class;
        }

        public List<DataSilabus> getTest_not_score() {
            return test_not_score;
        }

        public void setTest_not_score(List<DataSilabus> test_not_score) {
            this.test_not_score = test_not_score;
        }

        public List<DataSilabus> getCources_score_not_completed() {
            return cources_score_not_completed;
        }

        public void setCources_score_not_completed(List<DataSilabus> cources_score_not_completed) {
            this.cources_score_not_completed = cources_score_not_completed;
        }
    }

    //// Response List exam
    public class ListExam{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataExam> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataExam> getData() {
            return data;
        }

        public void setData(List<DataExam> data) {
            this.data = data;
        }
    }
    public class DataExam{
        @SerializedName("typeid")
        public String typeid;
        @SerializedName("type_name")
        public String type_name;
        @SerializedName("type_status")
        public String type_status;
        @SerializedName("type_publish")
        public String type_publish;
        @SerializedName("type_sort")
        public String type_sort;

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getType_status() {
            return type_status;
        }

        public void setType_status(String type_status) {
            this.type_status = type_status;
        }

        public String getType_publish() {
            return type_publish;
        }

        public void setType_publish(String type_publish) {
            this.type_publish = type_publish;
        }

        public String getType_sort() {
            return type_sort;
        }

        public void setType_sort(String type_sort) {
            this.type_sort = type_sort;
        }
    }

    ///Response Agenda
    public class ListAgenda{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataAgenda> data;

        @SerializedName("last_cources")
        public List<JadwalData> class_schedule;

        public List<JadwalData> getClass_schedule() {
            return class_schedule;
        }

        public void setClass_schedule(List<JadwalData> class_schedule) {
            this.class_schedule = class_schedule;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataAgenda> getData() {
            return data;
        }

        public void setData(List<DataAgenda> data) {
            this.data = data;
        }
    }
    public class DataAgenda{
        @SerializedName("agenda_desc")
        public String agenda_desc;
        @SerializedName("desc")
        public String desc;
        @SerializedName("type")
        public String type;
        @SerializedName("date")
        public String date;
        @SerializedName("day")
        public String day;
        @SerializedName("agenda_color")
        public String agenda_color;
        @SerializedName("agenda_time")
        public String agenda_time;

        public String getAgenda_desc() {
            return agenda_desc;
        }

        public void setAgenda_desc(String agenda_desc) {
            this.agenda_desc = agenda_desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getAgenda_color() {
            return agenda_color;
        }

        public void setAgenda_color(String agenda_color) {
            this.agenda_color = agenda_color;
        }

        public String getAgenda_time() {
            return agenda_time;
        }

        public void setAgenda_time(String agenda_time) {
            this.agenda_time = agenda_time;
        }
    }

    //// Response Detail Raport
    public class ListDetailRapor{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataDetailRapor> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataDetailRapor> getData() {
            return data;
        }

        public void setData(List<DataDetailRapor> data) {
            this.data = data;
        }
    }
    public class DataDetailRapor{
        @SerializedName("examid")
        public String examid;
        @SerializedName("exam_date")
        public String exam_date;
        @SerializedName("exam_date_ok")
        public String exam_date_ok;
        @SerializedName("exam_desc")
        public String exam_desc;
        @SerializedName("exam_type")
        public String exam_type;
        @SerializedName("score_value")
        public String score_value;

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

        public String getExam_date_ok() {
            return exam_date_ok;
        }

        public void setExam_date_ok(String exam_date_ok) {
            this.exam_date_ok = exam_date_ok;
        }

        public String getExam_desc() {
            return exam_desc;
        }

        public void setExam_desc(String exam_desc) {
            this.exam_desc = exam_desc;
        }

        public String getExam_type() {
            return exam_type;
        }

        public void setExam_type(String exam_type) {
            this.exam_type = exam_type;
        }

        public String getScore_value() {
            return score_value;
        }

        public void setScore_value(String score_value) {
            this.score_value = score_value;
        }
    }

    ///Response Tambah Agenda
    public class AddAgenda{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public String data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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

    ///Response Pesan Guru
    public class ListPesanGuru{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataPesanGuru> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataPesanGuru> getData() {
            return data;
        }

        public void setData(List<DataPesanGuru> data) {
            this.data = data;
        }
    }
    public class DataPesanGuru{
        @SerializedName("messageid")
        public String messageid;
        @SerializedName("user_id_from")
        public String user_id_from;
        @SerializedName("user_id_to")
        public String user_id_to;
        @SerializedName("message_cont")
        public String message_cont;
        @SerializedName("message_date")
        public String message_date;
        @SerializedName("message_status")
        public String message_status;
        @SerializedName("message_type")
        public String message_type;
        @SerializedName("classroom_id")
        public String classroom_id;
        @SerializedName("cources_id")
        public String cources_id;
        @SerializedName("datez")
        public String datez;
        @SerializedName("member_id")
        public String member_id;
        @SerializedName("reply_message_id")
        public String reply_message_id;
        @SerializedName("read_status")
        public String read_status;
        @SerializedName("read_date")
        public String read_date;
        @SerializedName("read_message_id")
        public String read_message_id;
        @SerializedName("parent_status")
        public String parent_status;
        @SerializedName("parent_id")
        public String parent_id;
        @SerializedName("message_title")
        public String message_title;
        @SerializedName("student_status")
        public String student_status;
        @SerializedName("datez_ok")
        public String datez_ok;
        @SerializedName("classroom_name")
        public String classroom_name;
        @SerializedName("cources_name")
        public String cources_name;
        @SerializedName("sender_name")
        public String sender_name;
        @SerializedName("member_type_text")
        public String member_type_text;
        @SerializedName("reply_status")
        public String reply_status;

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getUser_id_from() {
            return user_id_from;
        }

        public void setUser_id_from(String user_id_from) {
            this.user_id_from = user_id_from;
        }

        public String getUser_id_to() {
            return user_id_to;
        }

        public void setUser_id_to(String user_id_to) {
            this.user_id_to = user_id_to;
        }

        public String getMessage_cont() {
            return message_cont;
        }

        public void setMessage_cont(String message_cont) {
            this.message_cont = message_cont;
        }

        public String getMessage_date() {
            return message_date;
        }

        public void setMessage_date(String message_date) {
            this.message_date = message_date;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
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

        public String getReply_message_id() {
            return reply_message_id;
        }

        public void setReply_message_id(String reply_message_id) {
            this.reply_message_id = reply_message_id;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getRead_date() {
            return read_date;
        }

        public void setRead_date(String read_date) {
            this.read_date = read_date;
        }

        public String getRead_message_id() {
            return read_message_id;
        }

        public void setRead_message_id(String read_message_id) {
            this.read_message_id = read_message_id;
        }

        public String getParent_status() {
            return parent_status;
        }

        public void setParent_status(String parent_status) {
            this.parent_status = parent_status;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getMessage_title() {
            return message_title;
        }

        public void setMessage_title(String message_title) {
            this.message_title = message_title;
        }

        public String getStudent_status() {
            return student_status;
        }

        public void setStudent_status(String student_status) {
            this.student_status = student_status;
        }

        public String getDatez_ok() {
            return datez_ok;
        }

        public void setDatez_ok(String datez_ok) {
            this.datez_ok = datez_ok;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public String getMember_type_text() {
            return member_type_text;
        }

        public void setMember_type_text(String member_type_text) {
            this.member_type_text = member_type_text;
        }

        public String getReply_status() {
            return reply_status;
        }

        public void setReply_status(String reply_status) {
            this.reply_status = reply_status;
        }
    }

    ///Response List Whattodolist
    public class ListWhattodolist{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataWhattodolis> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataWhattodolis> getData() {
            return data;
        }

        public void setData(List<DataWhattodolis> data) {
            this.data = data;
        }
    }
    public class DataWhattodolis{
        @SerializedName("cources_name")
        public String cources_name;
        @SerializedName("cscheduletimeid")
        public String cscheduletimeid;
        @SerializedName("class")
        public String classs;
        @SerializedName("absent_todo_text")
        public String absent_todo_text;
        @SerializedName("classid")
        public String classid;
        @SerializedName("classroom_name")
        public String classroom_name;
        @SerializedName("courcesid")
        public String courcesid;
        @SerializedName("exam_date")
        public String exam_date;
        @SerializedName("exam_desc")
        public String exam_desc;
        @SerializedName("exam_tipe")
        public String exam_tipe;
        @SerializedName("exam_id")
        public String exam_id;
        @SerializedName("exam_tipe_id")
        public String exam_tipe_id;
        @SerializedName("exam_todo_text")
        public String exam_todo_text;

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getCscheduletimeid() {
            return cscheduletimeid;
        }

        public void setCscheduletimeid(String cscheduletimeid) {
            this.cscheduletimeid = cscheduletimeid;
        }

        public String getClasss() {
            return classs;
        }

        public void setClasss(String classs) {
            this.classs = classs;
        }

        public String getAbsent_todo_text() {
            return absent_todo_text;
        }

        public void setAbsent_todo_text(String absent_todo_text) {
            this.absent_todo_text = absent_todo_text;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getCourcesid() {
            return courcesid;
        }

        public void setCourcesid(String courcesid) {
            this.courcesid = courcesid;
        }

        public String getExam_date() {
            return exam_date;
        }

        public void setExam_date(String exam_date) {
            this.exam_date = exam_date;
        }

        public String getExam_desc() {
            return exam_desc;
        }

        public void setExam_desc(String exam_desc) {
            this.exam_desc = exam_desc;
        }

        public String getExam_tipe() {
            return exam_tipe;
        }

        public void setExam_tipe(String exam_tipe) {
            this.exam_tipe = exam_tipe;
        }

        public String getExam_id() {
            return exam_id;
        }

        public void setExam_id(String exam_id) {
            this.exam_id = exam_id;
        }

        public String getExam_tipe_id() {
            return exam_tipe_id;
        }

        public void setExam_tipe_id(String exam_tipe_id) {
            this.exam_tipe_id = exam_tipe_id;
        }

        public String getExam_todo_text() {
            return exam_todo_text;
        }

        public void setExam_todo_text(String exam_todo_text) {
            this.exam_todo_text = exam_todo_text;
        }
    }

    //Response Pesan Terkirim Guru
    public class ListPesanTerkirimGuru {
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataPesanTerkirim> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataPesanTerkirim> getData() {
            return data;
        }

        public void setData(List<DataPesanTerkirim> data) {
            this.data = data;
        }
    }
    public class DataPesanTerkirim{
        @SerializedName("messageid")
        public String messageid;
        @SerializedName("user_id_from")
        public String user_id_from;
        @SerializedName("user_id_to")
        public String user_id_to;
        @SerializedName("message_cont")
        public String message_cont;
        @SerializedName("message_date")
        public String message_date;
        @SerializedName("message_status")
        public String message_status;
        @SerializedName("message_type")
        public String message_type;
        @SerializedName("classroom_id")
        public String classroom_id;
        @SerializedName("cources_id")
        public String cources_id;
        @SerializedName("datez")
        public String datez;
        @SerializedName("member_id")
        public String member_id;
        @SerializedName("reply_message_id")
        public String reply_message_id;
        @SerializedName("read_status")
        public String read_status;
        @SerializedName("read_date")
        public String read_date;
        @SerializedName("read_message_id")
        public String read_message_id;
        @SerializedName("parent_status")
        public String parent_status;
        @SerializedName("parent_id")
        public String parent_id;
        @SerializedName("message_title")
        public String message_title;
        @SerializedName("student_status")
        public String student_status;
        @SerializedName("datez_ok")
        public String datez_ok;
        @SerializedName("classroom_name")
        public String classroom_name;
        @SerializedName("cources_name")
        public String cources_name;
        @SerializedName("recipient_name")
        public String recipient_name;
        @SerializedName("member_type_text")
        public String member_type_text;


        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getUser_id_from() {
            return user_id_from;
        }

        public void setUser_id_from(String user_id_from) {
            this.user_id_from = user_id_from;
        }

        public String getUser_id_to() {
            return user_id_to;
        }

        public void setUser_id_to(String user_id_to) {
            this.user_id_to = user_id_to;
        }

        public String getMessage_cont() {
            return message_cont;
        }

        public void setMessage_cont(String message_cont) {
            this.message_cont = message_cont;
        }

        public String getMessage_date() {
            return message_date;
        }

        public void setMessage_date(String message_date) {
            this.message_date = message_date;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getClassroom_id() {
            return classroom_id;
        }

        public void setClassroom_id(String classroom_id) {
            this.classroom_id = classroom_id;
        }

        public String getCources_id() {
            return cources_id;
        }

        public void setCources_id(String cources_id) {
            this.cources_id = cources_id;
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

        public String getReply_message_id() {
            return reply_message_id;
        }

        public void setReply_message_id(String reply_message_id) {
            this.reply_message_id = reply_message_id;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getRead_date() {
            return read_date;
        }

        public void setRead_date(String read_date) {
            this.read_date = read_date;
        }

        public String getRead_message_id() {
            return read_message_id;
        }

        public void setRead_message_id(String read_message_id) {
            this.read_message_id = read_message_id;
        }

        public String getParent_status() {
            return parent_status;
        }

        public void setParent_status(String parent_status) {
            this.parent_status = parent_status;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getMessage_title() {
            return message_title;
        }

        public void setMessage_title(String message_title) {
            this.message_title = message_title;
        }

        public String getStudent_status() {
            return student_status;
        }

        public void setStudent_status(String student_status) {
            this.student_status = student_status;
        }

        public String getDatez_ok() {
            return datez_ok;
        }

        public void setDatez_ok(String datez_ok) {
            this.datez_ok = datez_ok;
        }

        public String getClassroom_name() {
            return classroom_name;
        }

        public void setClassroom_name(String classroom_name) {
            this.classroom_name = classroom_name;
        }

        public String getCources_name() {
            return cources_name;
        }

        public void setCources_name(String cources_name) {
            this.cources_name = cources_name;
        }

        public String getSender_name() {
            return recipient_name;
        }

        public void setSender_name(String sender_name) {
            this.recipient_name = sender_name;
        }

        public String getMember_type_text() {
            return member_type_text;
        }

        public void setMember_type_text(String member_type_text) {
            this.member_type_text = member_type_text;
        }

    }

    //// Response List admin
    public class ListAdmin{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public List<DataAdmin> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<DataAdmin> getData() {
            return data;
        }

        public void setData(List<DataAdmin> data) {
            this.data = data;
        }
    }
    public class DataAdmin{
        @SerializedName("memberid")
        public String memberid;
        @SerializedName("fullname")
        public String fullname;

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }
    }

    //// Response Kirim Pesan{
    public class KirimPesanGuru{
        @SerializedName("status")
        public int status;

        @SerializedName("code")
        public String code;

        @SerializedName("data")
        public String data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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
}
