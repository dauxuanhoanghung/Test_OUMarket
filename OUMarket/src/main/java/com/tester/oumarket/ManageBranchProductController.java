/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.BranchMarket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class ManageBranchProductController extends AbstractManageController {
    
    private BranchMarket branch;
    private String previous;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }    

    /**
     * @param branch the branch to set
     */
    public void setBranch(BranchMarket branch) {
        this.branch = branch;
    }

    /**
     * @param previous the previous to set
     */
    public void setPrevious(String previous) {
        this.previous = previous;
    }
    
}
