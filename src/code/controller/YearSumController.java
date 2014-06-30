package src.code.controller;

import javafx.event.ActionEvent;

public class YearSumController extends AbstractController {

    @Override
    protected void submit(ActionEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void goBack(ActionEvent event) {
        transition(event, "seeSum");
    }

    @Override
    protected void populate(ActionEvent event) {
        // TODO Auto-generated method stub

    }

}
