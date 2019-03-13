package main.com.pluralsight.controllers;

import main.com.pluralsight.model.QuestionAnswer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped

public class AnswerController implements Serializable {
     List<QuestionAnswer> alternatives ;
             //new QuestionController().getAlternativesForEachQuestion( );

    public AnswerController(){}

    public  List<QuestionAnswer> getAlternatives() {
        return alternatives;
    }

    public  void setAlternatives(List<QuestionAnswer> alternatives) {
        this.alternatives = alternatives;
    }

    public static void main(String args[]){

    }
}
