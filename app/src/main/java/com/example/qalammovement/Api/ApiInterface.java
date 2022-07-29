package com.example.qalammovement.Api;

import com.example.qalammovement.Model.AttandenceResponse;
import com.example.qalammovement.Model.GalleryImages;
import com.example.qalammovement.Model.LoginResponse;
import com.example.qalammovement.Model.NotificationModel;
import com.example.qalammovement.Model.RegisteredStatisticsResponse;
import com.example.qalammovement.Model.ReportResponse;
import com.example.qalammovement.Model.Schedule;
import com.example.qalammovement.Model.School;
import com.example.qalammovement.Model.StudentModel;
import com.example.qalammovement.Model.StudentResponse;
import com.example.qalammovement.Model.User;
import com.example.qalammovement.Model.VolunteerModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("register")
    Call<User> registerUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("V_ContactNumber") String contactnumber,
            @Field("V_CNIC") String cnic,
            @Field("V_PresentAddress") String address,
            @Field("V_UniversityName") String universityname,
            @Field("V_Degree") String degree,
            @Field("V_Semester") String semester,
            @Field("V_TeamName") String teamname,
            @Field("V_Day") String day,
            @Field("V_InstituteName") String institutename,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> LoginUser(
            @Field("name") String name,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("registerstudent")
    Call<StudentResponse> registerstudent(
            @Field("S_Name") String sname,
            @Field("S_Gender") String sgender,
            @Field("S_DOB") String sdob,
            @Field("S_Section") String ssection,
            @Field("S_Religion") String sreligion,
            @Field("S_Nationality") String snationality,
            @Field("S_SchoolName") String sschoolname,
            @Field("S_PermanaentAddress") String saddress,
            @Field("S_ContactNumber") String sfathercontact,
            @Field("S_FatherName") String sfathername,
            @Field("S_FatherCNIC") String sfathercnic,
            @Field("S_FatherOccupation") String sfatheroccupation,
            @Field("S_MonthlyIncome") String sfatherincome);

    @FormUrlEncoded
    @POST("adddailyreport")
    Call<ReportResponse> report(
            @Field("Date") String rdate,
            @Field("NoOfPresentStudents") String rnoofstudent,
            @Field("Feedback") String rfeedback);


    @GET("sendStatistics")
    Call<RegisteredStatisticsResponse> getstatistics(
    );

    @GET("sendattendance")
    Call<List<AttandenceResponse>>getstudentlist(
            @Query("S_SchoolName") String schoolname
    );

    @GET("getSchools")
    Call<List<School>> getSchools(
    );

    @Multipart
    @POST("addcomplain")
    Call<ReportResponse> addComplaint(
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("user_id") RequestBody user_id,
            @Part("school_id") RequestBody school_id,
            @Part MultipartBody.Part image
    );

    @GET("getStudents")
    Call<List<StudentModel>> getStudents(
            @Query("school_id") String school_id
    );
    @GET("volunteers")
    Call<List<VolunteerModel>> getVolunteer(
            @Query("school_id") String school_id
    );
    @POST("getTeams")
    @FormUrlEncoded
    Call<List<VolunteerModel>> getTeams(
            @Field("team_name") String team_name
    );
    @POST("getTeamsbyId")
    @FormUrlEncoded
    Call<List<VolunteerModel>> getTeamsbyId(
            @Field("school_id") String school_id
    );

    @Multipart
    @POST("dailyReport")
    Call<ReportResponse> addDailyReport(
            @Part("NoOfPresentStudents") RequestBody title,
            @Part("Feedback") RequestBody description,
            @Part("school_id") RequestBody school_id,
            @Part MultipartBody.Part image
    );

    @GET("getGalleryImages")
    Call<List<GalleryImages>> getGalleryImages();

    @POST("isprincipal")
    @FormUrlEncoded
    Call<ReportResponse> isprincipal(
            @Field("id") int id
    );

    @POST("addScehdule")
    @FormUrlEncoded
    Call<ReportResponse> addScehdule(
            @Field("school_id") String school_id,
            @Field("date") String date,
            @Field("time") String time,
            @Field("subject") String subject

    );

    @POST("getScehdule")
    @FormUrlEncoded
    Call<List<Schedule>> getScehdule(
            @Field("school_id") String school_id
    );

    @POST("requestStationary")
    @FormUrlEncoded
    Call<ReportResponse> requestStationary(
            @Field("school_id") String school_id,
            @Field("user_id") String user_id,
            @Field("description") String description
    );

    @POST("emergencyrequest")
    @FormUrlEncoded
    Call<ReportResponse> emergencyRequest(
            @Field("title") String title,
            @Field("user_id") String user_id,
            @Field("description") String description,
            @Field("request_type") int request_type
    );

    @POST("allattendance")
    @FormUrlEncoded
    Call<List<AttandenceResponse>> getAllattendance(
            @Field("user_id") String user_id
    );


    @POST("create_attendance")
    @FormUrlEncoded
    Call<ReportResponse> createAttendance(
            @Field("class") String userclass,
            @Field("time") String time,
            @Field("user_id") String user_id,
            @Field("school_id") String school_id
    );

    @GET("getAllStudents")
    Call<List<StudentModel>> getAllStudents();

    @POST("markattendance")
    @FormUrlEncoded
    Call<ReportResponse> markattendance(
            @Field("student_id") String student_id,
            @Field("attendance_id") String attendance_id,
            @Field("value") int value

    );

    @GET("getNotification")
    Call<List<NotificationModel>> getNotification();



   /* @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login12(
            @Field(Constant.KEY_EMAIL) String email,
            @Field(Constant.KEY_PASSWORD) String password);
    //for signup
    @FormUrlEncoded
    @POST("registeration.php")
    Call<User> signUp(
            @Field(Constant.KEY_EMAIL) String email,
            @Field(Constant.KEY_PASSWORD) String password,
            @Field(Constant.KEY_ROLE) String role);
    @FormUrlEncoded
    @POST("addtoilet.php")
    public Call<AddResponse> insertPatient(
            @Field("email") String extra_email,
            @Field("slots") String slots,
            @Field("place") String place,
            @Field("type") String type,
            @Field("type_of_location") String type_of_location,
            @Field("type_of_parking") String type_of_parking,
            @Field("latitude") String extra_latitude,
            @Field("longitude") String  extra_longitude);
    @GET("gettoilets.php")
    Call<List<Parking>> getPatient(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );
    @GET("getOwner.php")
    Call<List<User>> getowner(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );
    @FormUrlEncoded
    @POST("delete.php")
    Call<User> delete(
            @Field("id") int id
    );
    @FormUrlEncoded
    @POST("slots.php")
    Call<List<recycler_modal>> TotalSlot(@Field("parking_id") String parking_id);

    @GET("getOwner.php")
    Call<List<User>> getuser(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );
    @FormUrlEncoded
    @POST("showparking.php")
    Call<List<ShowParkingModal>> getparking(
            @Field(Constant.KEY_EMAIL) String email
    );

    @FormUrlEncoded
    @POST("slot.php")
    Call<List<Parking>> getslot(
            @Field(Constant.KEY_EMAIL) String email
    );

    @GET("getUser.php")
    Call<List<LoginResponse>> getUser(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );
    @GET("getnear.php")
    Call<ParkingData> getnear(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude
    );
    @FormUrlEncoded
    @POST("update.php")
    Call<UpdateModal> updateslote(
            @Field("id") String id,
            @Field("available") String available,
            @Field("parking_id") String parking_id
    );*/
}