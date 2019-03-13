package main.com.pluralsight.controllers;

import main.com.pluralsight.DAO.intefaces.services.RoleServices;
import main.com.pluralsight.DAO.intefaces.services.StudentServices;
import main.com.pluralsight.DAO.intefaces.services.UserServices;
import main.com.pluralsight.model.Role;
import main.com.pluralsight.model.Student;
import main.com.pluralsight.model.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;


@ManagedBean
@SessionScoped
public class LogInController implements Serializable {
    UserServices us = new UserServices();
    public  int id;
    public String username;
    public String password;

    public String repassword;
    public String email;
    public String role;
    public String university;
    public String name;
    public String birthday;
    User newUser;
    User currentUser;
    Student currentStudent;

    //constructor
    public LogInController(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LogInController() {
    }

    public LogInController(int id,String username, String password, String email, String role) {
        this.id=id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public LogInController(String username, String password, String email, String role, String university, String name, String birthday) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.university = university;
        this.name = name;
        this.birthday = birthday;

    }
    //accessors


    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public  int getId() {
        return id;
    }

    public  void setId(int id) {
        this.id = id;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

//controller functions

    public  String checkCredentials() {
        RoleServices rs=new RoleServices();

        User foundUser = us.findByUsername( this.username, this.password );
        if(foundUser!=null){
            currentUser=us.findById( foundUser.getId() );
            System.out.println( currentUser.getEmail() );
            Role currectRole=rs.findById(  foundUser.getRole_id());

            if(currectRole.getRole().equals( "Admin" ))
                return "admin";
            if(currectRole.getRole().equals( "Student" )){
                StudentServices ss=new StudentServices();
                currentStudent=ss.findByUserId( foundUser.getId() );
                return "student";
            }

            if (currectRole.getRole().equals( "User" ))
                return "user";


        }
         return "failed";
    }
    public List<Role> getRoles(){
        RoleServices rs=new RoleServices();
        List<Role> list=rs.findAll();
        list.remove( list.get( 0 ) );//remove to create the admin role possibility
        return list;
    }


    public String saveNewUser(){
        UserServices us=new UserServices();
        RoleServices rs=new RoleServices();
        int roleId=getIdRole( this.role);
        int userId;
        if( us.findAll().size()==0){
            userId=0;
        }else
            userId =us.findAll().get( us.findAll().size()-1 ).getId();
        userId++;
        if (checkPasswords()) {
            newUser = new User( userId, this.username, this.password, this.email, roleId );
            us.persist( newUser );
        }
            if (this.role.equals( "Student" )) {
                System.out.println( "Student" );
                return "/studentSignup.xhtml?faces-redirect=true";
            }

        return "/index.html?faces-redirect=true";

    }

    public String saveNewStudent(){
        StudentServices stdS=new StudentServices();
        int stdId;
        if( stdS.findAll().size()==0){
            stdId=0;
        }else
            stdId =us.findAll().get( us.findAll().size()-1 ).getId();
        stdId++;
        Student std=new Student( stdId,newUser.getId(),this.university,newUser.getEmail(),this.birthday,this.name,newUser.getPassword(),newUser.getUsername() );
        System.out.println( "Student created");
        stdS.persist( std );
        return "success";
    }

    public int getIdRole(String role){
        RoleServices rs= new RoleServices();
        List<Role> list=rs.findAll();
        for(int i=0;i<list.size();i++){
            if(role.equals( list.get( i ).getRole() ))
                return list.get( i ).getId();
        }
        return 0;
    }
    public String getStringRole(int id){
        RoleServices rs=new RoleServices();
        return rs.findById( id ).getRole();
    }
    public String updateUser(){
        //User updtedUser=new User( currentUser.getId(),username,password,email,currentUser.getRole_id() );
        us.update( currentUser );
        RoleServices rs=new RoleServices();
        String currentRole=rs.findById( currentUser.getId() ).getRole();
        if(currentRole.equals( "Admin" ))
            return "/indexAdmin.html?faces-redirect=true";
        return "/indexStudent.html?faces-redirect=true";
    }
    public String updateStudent(){
        currentUser.setEmail( currentStudent.getEmail() );
        currentUser.setPassword( currentStudent.getPassword() );
        currentUser.setUsername( currentStudent.getUsername() );
        updateUser();
        StudentServices std=new StudentServices();
        std.update( currentStudent );
        System.out.println(currentUser  );
        return "/indexStudent.html?faces-redirect=true";

    }

    public boolean checkPasswords(){
        if(this.password.equals( this.repassword )){
            System.out.println( "Passwords okay" );
            return true;
        }
            return false;
    }

    public static void main(String args[]){
LogInController lg=new LogInController( "jorgen","1234","jkonini@gmail.com","Student","epoka","jorgenKonin","12 January,1999" );
System.out.println( lg.checkCredentials() );
System.out.println( lg.currentUser.getEmail() );
System.out.println( lg.currentStudent.getEmail());
lg.setEmail( "jorgen@gmail.com" );
lg.updateStudent();
System.out.println(lg.currentStudent.getEmail());
System.out.println( lg.currentUser.getEmail() );

    }
}



