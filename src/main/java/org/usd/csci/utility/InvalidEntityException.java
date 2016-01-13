package org.usd.csci.utility;

public class InvalidEntityException extends Exception {
    
    //Default Constructor
    public InvalidEntityException(){
        
    }
    
    //Specific Constructor
    public InvalidEntityException(String message){
        
        super(message); //Calls the superclass constructor
    
    }
}
