package docker.container;

import com.fasterxml.jackson.annotation.JsonInclude;

public class FileResponse {

    private String file;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sum;

    public FileResponse(String file, String error) {
        this.file = file;
        this.error = error;
    }

    public String getFile() {
        return file;
    }

    public String getError() {
        return error;
    }
}