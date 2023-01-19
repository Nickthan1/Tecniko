package nikos.tecniko.dto;

public class RestApiResult<T> {

    private T data;
    private int errorCode;
    private String description;

    public RestApiResult(T data, int errorCode, String description) {
        this.data = data;
        this.errorCode = errorCode;
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}