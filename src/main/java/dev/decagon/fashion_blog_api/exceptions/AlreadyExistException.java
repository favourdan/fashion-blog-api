package dev.decagon.fashion_blog_api.exceptions;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message){
        super(message);
    }

}
