package main.com.pluralsight.controllers;

import main.com.pluralsight.DAO.intefaces.services.AnswerServices;
import main.com.pluralsight.DAO.intefaces.services.QuestionAnswerServices;
import main.com.pluralsight.DAO.intefaces.services.QuestionServices;
import main.com.pluralsight.model.Answer;
import main.com.pluralsight.model.Question;
import main.com.pluralsight.model.QuestionAnswer;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
@RequestScoped
public class QuestionController implements Serializable {
    int pageId=0;int id=0 ,pagenr=0;
    public static List<Boolean> isCorrect=new ArrayList<>( 10 );
    public  String givenAnswer;
    static List<Answer> givenAnswers=new AnswerServices().findAll();
    static List<Answer> correctAnswers=new ArrayList<>(  );

    static TestController ts=new TestController(  );




    public QuestionController(){}


    public String getGivenAnswer() {

        return givenAnswer;
    }

    public  void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public List<Answer> getGivenAnswers() {
        return givenAnswers;
    }

    public void setGivenAnswers(ArrayList<Answer> givenAnswers) {
        this.givenAnswers = givenAnswers;
    }

    public int getPageId() {
        return pageId++;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public static QuestionAnswer getAlternativesForEachQuestion(int id){
        QuestionAnswerServices qsa=new QuestionAnswerServices();
       QuestionAnswer questionAlternatives;
        questionAlternatives=qsa.findByQuestionId( id ).get( 0 );
        return questionAlternatives;
    }
    public String getCurrentGivenAnswer(int qId){
        QuestionAnswerServices qas=new QuestionAnswerServices();
        QuestionAnswer qa=qas.findByQuestionId( qId ).get( 0 );
        int answId=qa.getId();
        return givenAnswers.get( answId-1 ).getAnswer();
    }
    int id1=0;
    public String checkAnswers(int qID){
        if(getCurrentCorrectAnswer( qID ).equals( getCurrentGivenAnswer( qID ))){
            isCorrect.add( true );
            return "True";
        }else {
            isCorrect.add( false );
        return "False";
        }
    }
    int gatheredPoints=0;
    public  String getScores(){
        List<Question> questionList=new TestController(  ).getQuestionList();
        int pointsQuestion=100/(questionList.size());
        for(int i=0;i<isCorrect.size();i++){
            if(isCorrect.get( i )){
                gatheredPoints+=pointsQuestion;
            }
        }
        return gatheredPoints+"";
    }

    public static String passed(){
        if(60>Integer.parseInt( new QuestionController().getScores())){
            return "failed";
        }
        return "passed";
    }
    public String getCurrentCorrectAnswer(int Qid){
        QuestionAnswerServices qas=new QuestionAnswerServices();
        QuestionAnswer qa=qas.findByQuestionId( Qid ).get( 0 );
        int answId=qa.getAnswer_id();
        AnswerServices as=new AnswerServices();
        Answer a=as.findById( answId );
        correctAnswers.add( a );
        return a.getAnswer();
    }



    public static void main(String args[]) {
    }

}
