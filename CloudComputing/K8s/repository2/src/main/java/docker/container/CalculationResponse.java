package docker.container;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CalculationResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String file;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sum;

    public CalculationResponse(String file, String sum, String error) {
        this.file = file;
        this.sum = sum;
        this.error = error;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}