package src.code.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class SummaryController extends AbstractController {


    @Override
    protected void submit(ActionEvent event) {
        String nextPage = ((Button)event.getSource()).getId();
        transition(event, nextPage);
    }

    @Override
    protected void goBack(ActionEvent event) {
        transition(event, "menu");
    }

    @Override
    protected void populate(ActionEvent event) {
       // not needed
    }

}
