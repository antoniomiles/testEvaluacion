package com.pichincha.automationtest.tasks.demo.retrieveusername.enums;

public enum VarMsGraph {

    /**
     * Variables
     */
    MSGRAPH_TOKEN,

    /**
     * Variables Remember
     */
    SEND_DATE,
    DATA_FROM_MAIL
    ;

    private final String propertyName;

    VarMsGraph() {
        this.propertyName = name().replace("_", ".").toLowerCase();
    }


    public String getVarName() {
        return propertyName;
    }

}
