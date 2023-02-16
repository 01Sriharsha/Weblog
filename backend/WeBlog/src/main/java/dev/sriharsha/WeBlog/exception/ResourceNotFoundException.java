package dev.sriharsha.WeBlog.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private long filedValue;
    private String filedValue1;

    private String message;

    public ResourceNotFoundException(String message){
        super(message);
        this.message = message;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, long filedValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, filedValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.filedValue = filedValue;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String filedValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, filedValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.filedValue1 = filedValue;
    }


}
