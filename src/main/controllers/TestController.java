package main.com.pluralsight.controllers;

import main.com.pluralsight.DAO.intefaces.TestQuestionsDao;
import main.com.pluralsight.DAO.intefaces.services.*;
import main.com.pluralsight.TestServices;
import main.com.pluralsight.model.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class TestController implements Serializable {
    public int id;
    public String name;
    public int pass;

     TestTableServices ts=new TestTableServices();
    public  List<Test> testList=new TestTableServices().findAll();
     Test currentTest=new TestTableServices().findById( 1 );
     List<Test> updatedTestList=new TestTableServices().findAll();
     List<Question> questionList1=getQuestionList();
    public int testNavigationId;



    public TestController(){

    }
    public TestController(int id, String name, int pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;

    }

    public List<Test> getUpdatedTestList() {
        return updatedTestList;
    }

    public void setUpdatedTestList(List<Test> updatedTestList) {
        this.updatedTestList = updatedTestList;
    }

    public int getTestNavigationId() {
        return testNavigationId;
    }

    public void setTestNavigationId(int testNavigationId) {
        this.testNavigationId = testNavigationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public List<Test> getTestList() {
        return testList;
    }

    public  void setTestList(List<Test> testList) {
        testList = testList;
    }

    public Test getCurrentTest() {
        return currentTest;
    }

    public void setCurrentTest(Test currentTest) {
        this.currentTest = currentTest;
    }

    public List<Question> getQuestionList1() {
        return questionList1;
    }

    public void setQuestionList1(List<Question> questionList1) {
        this.questionList1 = questionList1;
    }

    public  ArrayList<Question> getTestQuestions(int testId){
        //getting the Id-s of the questions of that testId
        TestQuestionsServices tqs=new TestQuestionsServices();
        System.out.println( "okay" );
        List<TestQuestions> testQuestions=tqs.findByTestId( testId );
        System.out.println( testQuestions );
        ArrayList<Integer> questionIds=new ArrayList<>( testQuestions.size()+1 );

        for(int i=0;i<testQuestions.size();i++){
            questionIds.add( testQuestions.get( i ).getQuestion_id());
            System.out.println( questionIds.get( i ) );
        }

        //getting access to the specific questions that we found

        QuestionServices qs=new QuestionServices();
        ArrayList<Question> questions=new ArrayList<>( questionIds.size() );
        for(int i=0;i<questionIds.size();i++){
            questions.add( qs.findById( questionIds.get( i ) ) );
            System.out.println( questions.get( i ) );
        }

        return questions;
    }
public  List<Test> getTests(){
        TestTableServices ts=new TestTableServices();
        return ts.findAll();
}



    public  String updateTest(){
        ts.update( currentTest );
        updatedTestList=ts.findAll();
        System.out.println( "test updated" );
        return "/showAdminTests1.xhtml?faces-redirect=true";
    }

    public String takeTest(Integer id){
        setCurrentTest(  ts.findById( id ));
        return "/editTest.xhtml?faces-redirect=true";
    }
    public String createTest(){
        int testId;
        if( ts.findAll().size()==0){
            testId=0;
        }else
            testId =ts.findAll().get( ts.findAll().size()-1 ).getId();
        testId++;
       Test newTest=new Test(testId,this.name,this.pass);
        ts.persist( newTest );
        updatedTestList=ts.findAll();
        return "/showAdminTests1.xhtml?faces-redirect=true";
    }

    public String showTest(Integer id){
        setCurrentTest( ts.findById( id ) );
        System.out.println( currentTest.getId() );
        return "/showQuestions.xhtml?faces-redirect=true";
    }
    public String showTest1(Integer id){
        setCurrentTest( ts.findById( id ) );
        System.out.println( currentTest.getId() );
        return "/showAdminQuestions.xhtml?faces-redirect=true";
    }
    public List<Question> getQuestionList(){
        return getTestQuestions( currentTest.getId() );
    }


    public static void main (String args[]){
TestController ts=new TestController(  );
ts.showTest( 2 );
System.out.println( ts.currentTest.getId() );
System.out.println( ts.getQuestionList());

    }


}
