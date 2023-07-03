package docker.container;

import com.fasterxml.jackson.annotation.JsonInclude;

public class FileResponse {

    private String file;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    

    public FileResponse(String file, String error, Integer sum, String message) {
        this.file = file;
        this.error = error;
        this.sum = sum;
        this.message = message;
    }

    public String getFile() {
        return file;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}